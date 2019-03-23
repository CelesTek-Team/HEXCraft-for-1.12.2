package celestek.hexcraft.utility;

import java.util.Optional;

import javax.vecmath.Vector4f;

import com.google.common.collect.ImmutableSet;

import celestek.hexcraft.client.event.HexClientEvents;
import celestek.hexcraft.client.model.BakedModelBrightness;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.client.FMLClientHandler;

/**
 * Contains many useful utility functions
 */
public final class HexUtilities
{
	private HexUtilities() {}

	/**
	 * Adds a fullbright model override to all the given model paths for the given textures
	 */
	public static void addFullbright(ImmutableSet<String> textures, boolean cache, ResourceLocation... paths) // Move?
	{
		for(ResourceLocation path : paths) HexClientEvents.addModelOverride(path, base -> new BakedModelBrightness(base, textures).setCache(cache));
	}

	public static boolean isLightMapDisabled()
	{
		return FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled;
	}

	private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

	/**
	 * Returns a vertex format with a lightmap based on the given format
	 */
	public static VertexFormat getFormatWithLightMap(VertexFormat format)
	{
		if (isLightMapDisabled()) return format;
		if (format == DefaultVertexFormats.BLOCK) return DefaultVertexFormats.BLOCK;
		else if (format == DefaultVertexFormats.ITEM) return ITEM_FORMAT_WITH_LIGHTMAP;
		else if (!format.hasUvOffset(1))
		{
			VertexFormat result = new VertexFormat(format);
			result.addElement(DefaultVertexFormats.TEX_2S);
			return result;
		}
		return format;
	}

	/**
	 * Creates a quad with the given vertex format, matrix transform, vertex coordinates, UVs, alpha transparency, shading and sprite
	 */
	public static BakedQuad createQuad(VertexFormat format, Optional<TRSRTransformation> transform, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float u1, float v1, float u2, float v2, float u3, float v3, float u4, float v4, float alpha, int tint, boolean shade, TextureAtlasSprite sprite)
	{
		// Calculate the normal for this quad
		// Use vector classes for this?
		float d1x = x3 - x2, d1y = y3 - y2, d1z = z3 - z2, d2x = x1 - x2, d2y = y1 - y2, d2z = z1 - z2;
		float nx = d1y * d2z - d1z * d2y, ny = d1z * d2x - d1x * d2z, nz = d1x * d2y - d1y * d2x;
		float l = MathHelper.sqrt(nx * nx + ny * ny + nz * nz);
		nx /= l; ny /= l; nz /= l;

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		builder.setQuadTint(tint);
		builder.setApplyDiffuseLighting(shade);
		putVertex(builder, format, transform, nx, ny, nz, x1, y1, z1, u1, v1, alpha, sprite);
		putVertex(builder, format, transform, nx, ny, nz, x2, y2, z2, u2, v2, alpha, sprite);
		putVertex(builder, format, transform, nx, ny, nz, x3, y3, z3, u3, v3, alpha, sprite);
		putVertex(builder, format, transform, nx, ny, nz, x4, y4, z4, u4, v4, alpha, sprite);
		return builder.build();
	}

	/**
	 * Adds the given vertex coordinates, UVs and alpha transparency via the given vertex format, matrix transformation and sprite to the given quad builder
	 */
	public static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format, Optional<TRSRTransformation> transform, float nx, float ny, float nz, float x, float y, float z, float u, float v, float alpha, TextureAtlasSprite sprite)
	{
		Vector4f vector = new Vector4f(x, y, z, 1f);
		for (int e = 0; e < format.getElementCount(); e++)
		{
			switch (format.getElement(e).getUsage())
			{
			case POSITION:
				if(transform.isPresent() && !transform.get().isIdentity()) transform.get().getMatrix().transform(vector);
				builder.put(e, vector.x, vector.y, vector.z, vector.w);
				break;
			case COLOR:
				builder.put(e, 1f, 1f, 1f, alpha);
				break;
			case UV:
				if (format.getElement(e).getIndex() == 0)
				{
					builder.put(e, sprite.getInterpolatedU(u), sprite.getInterpolatedV(v), 0f, 1f);
					break;
				}
			case NORMAL:
				builder.put(e, nx, ny, nz, 0f);
				break;
			default:
				builder.put(e);
				break;
			}
		}
	}
}