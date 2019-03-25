package celestek.hexcraft.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A special baked model wrapper which changes the vertex format, applies maximum brightness and disables diffuse lighting for all the quads that match the supplied texture filter.
 * Supports quad caching
 */
@SideOnly(Side.CLIENT)
public class BakedModelBrightness extends BakedModelWrapper
{
	/**
	 * A cache key used to store different quads of the model
	 */
	private class CacheKey
	{
		protected BakedModelBrightness model;
		protected IBlockState state;
		protected EnumFacing side;

		public CacheKey(BakedModelBrightness model, IBlockState state, EnumFacing side)
		{
			this.model = model;
			this.state = state;
			this.side = side;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey key = (CacheKey) o;
			return this.side == key.side && this.state == key.state; // Careful with this comparison in case block states are changed and are no longer compared like this
		}

		@Override
		public int hashCode()
		{
			int hash = this.state == null ? 0 : this.state.hashCode();
			hash = 31 * hash + (this.side == null ? 0 : this.side.hashCode());
			return hash;
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			return transformQuads(key.model.originalModel.getQuads(key.state, key.side, 0), key.model.filter);
		}
	});

	/**
	 * The texture filter which is used to determine the quads to modify
	 */
	protected final Predicate<String> filter;

	protected boolean enableCache = true;

	public BakedModelBrightness(IBakedModel base, Predicate<String> filter)
	{
		super(base);
		this.filter = filter;
	}

	public BakedModelBrightness setCache(boolean flag)
	{
		this.enableCache = flag;
		return this;
	}

	// FIXME Caching for items with animated textures
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
	{
		// Transform and draw the quads
		if (!this.enableCache || state == null) return transformQuads(this.originalModel.getQuads(state, side, 0), this.filter);
		return CACHE.getUnchecked(new CacheKey(this, state instanceof IExtendedBlockState ? ((IExtendedBlockState) state).getClean() : state, side));
	}

	/**
	 * Returns a new list of quads from the given list where all the quads matching the supplied texture filter are replaced with transformed ones
	 */
	protected static List<BakedQuad> transformQuads(List<BakedQuad> oldQuads, Predicate<String> filter) // FIXME make non-static?
	{
		List<BakedQuad> quads = new ArrayList<>(oldQuads);
		for (int i = 0; i < quads.size(); ++i)
		{
			BakedQuad quad = quads.get(i);
			// Applies maximum brightness (15)
			if(filter.test(quad.getSprite().getIconName())) quads.set(i, transformQuad(quad, 15 << 20 | 15 << 4));
		}
		return quads;
	}

	/**
	 * Returns a new quad based on the given one with the format changed if required, maximum brightness applied and diffuse lighting disabled
	 */
	protected static BakedQuad transformQuad(BakedQuad quad, int brightness)
	{
		if (HexUtilities.isLightMapDisabled()) return quad;
		VertexFormat newFormat = HexUtilities.getFormatWithLightMap(quad.getFormat());
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(newFormat);
		VertexLighterFlat trans = new VertexLighterFlat(Minecraft.getMinecraft().getBlockColors())
		{
			@Override
			protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z)
			{
				lightmap[0] = ((float) ((brightness >> 0x04) & 0xF) * 0x20) / 0xFFFF;
				lightmap[1] = ((float) ((brightness >> 0x14) & 0xF) * 0x20) / 0xFFFF;
			}

			@Override
			public void setQuadTint(int tint)
			{
				// NO OP
			}
		};
		trans.setParent(builder);
		quad.pipe(trans);
		builder.setQuadTint(quad.getTintIndex());
		builder.setQuadOrientation(quad.getFace());
		builder.setTexture(quad.getSprite());
		builder.setApplyDiffuseLighting(false); // FIXME Why does this only partially disable shading or something?
		return builder.build();
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType type)
	{
		Pair<? extends IBakedModel, Matrix4f> base = super.handlePerspective(type);
		// Return the unchanged base model if in a GUI
		if(type == TransformType.GUI) return base;
		// Wrap the model if it's not already
		return base.getLeft() instanceof BakedModelBrightness ? base : Pair.of(new BakedModelBrightness(base.getLeft(), this.filter), base.getRight());
	}
}