package celestek.hexcraft.utility;

import com.google.common.collect.ImmutableSet;

import celestek.hexcraft.client.event.HexClientEvents;
import celestek.hexcraft.client.model.BakedModelBrightness;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HexUtilities
{
	private HexUtilities() {}

	public static boolean isLightMapDisabled()
	{
		return FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled;
	}

	private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

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

	public static void addFullbright(ImmutableSet<String> textures, boolean cache, IForgeRegistryEntry... entries)
	{
		for(IForgeRegistryEntry entry : entries) HexClientEvents.addBakedModelOverride(entry.getRegistryName(), base -> new BakedModelBrightness(base, textures).setCache(cache));
	}

	public static BakedQuad createCube(VertexFormat format, EnumFacing face, int tint, TextureAtlasSprite sprite)
	{
		if(face == null) return null;
		// The vertex order MATTERS apparently
		switch(face)
		{
		case DOWN: return createQuad(format, new Vec3d(0d, 0d, 1d), new Vec3d(0d, 0d, 0d), new Vec3d(1d, 0d, 0d), new Vec3d(1d, 0d, 1d), tint, sprite);
		case UP: return createQuad(format, new Vec3d(0d, 1d, 0d), new Vec3d(0d, 1d, 1d), new Vec3d(1d, 1d, 1d), new Vec3d(1d, 1d, 0d), tint, sprite);
		case NORTH: return createQuad(format, new Vec3d(1d, 1d, 0d), new Vec3d(1d, 0d, 0d), new Vec3d(0d, 0d, 0d), new Vec3d(0d, 1d, 0d), tint, sprite);
		case SOUTH: return createQuad(format, new Vec3d(0d, 1d, 1d), new Vec3d(0d, 0d, 1d), new Vec3d(1d, 0d, 1d), new Vec3d(1d, 1d, 1d), tint, sprite);
		case WEST: return createQuad(format, new Vec3d(0d, 1d, 0d), new Vec3d(0d, 0d, 0d), new Vec3d(0d, 0d, 1d), new Vec3d(0d, 1d, 1d), tint, sprite);
		case EAST: return createQuad(format, new Vec3d(1d, 1d, 1d), new Vec3d(1d, 0d, 1d), new Vec3d(1d, 0d, 0d), new Vec3d(1d, 1d, 0d), tint, sprite);
		default: return null;
		}
	}

	public static BakedQuad createQuad(VertexFormat format, Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, int tint, TextureAtlasSprite sprite)
	{
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		builder.setQuadTint(tint);
		putVertex(builder, format, normal, v1.x, v1.y, v1.z, 0, 0, sprite);
		putVertex(builder, format, normal, v2.x, v2.y, v2.z, 0, 16, sprite);
		putVertex(builder, format, normal, v3.x, v3.y, v3.z, 16, 16, sprite);
		putVertex(builder, format, normal, v4.x, v4.y, v4.z, 16, 0, sprite);
		return builder.build();
	}

	public static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format,  Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite)
	{
		for (int e = 0; e < format.getElementCount(); e++)
		{
			switch (format.getElement(e).getUsage())
			{
			case POSITION:
				builder.put(e, (float) x, (float) y, (float) z, 1f);
				break;
			case COLOR:
				builder.put(e, 1f, 1f, 1f, 1f);
				break;
			case UV:
				if (format.getElement(e).getIndex() == 0)
				{
					u = sprite.getInterpolatedU(u);
					v = sprite.getInterpolatedV(v);
					builder.put(e, u, v, 0f, 1f);
					break;
				}
			case NORMAL:
				builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
				break;
			default:
				builder.put(e);
				break;
			}
		}
	}
}