package celestek.hexcraft.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;

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

public class BakedModelBrightness extends BakedModelWrapper
{
	private class CacheKey
	{
		private IBakedModel base;
		private ImmutableSet<String> textures;
		private IBlockState state;
		private EnumFacing side;

		public CacheKey(IBakedModel base, ImmutableSet<String> textures, IBlockState state, EnumFacing side)
		{
			this.base = base;
			this.textures = textures;
			this.state = state;
			this.side = side;
		}

		// FIXME equals instead of ==? Although default state implementations are already compared that way
		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey cacheKey = (CacheKey) o;
			if (this.side != cacheKey.side) return false;
			if (this.state != cacheKey.state) return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			return state != null ? state.hashCode() : 0 + (31 * (side != null ? side.hashCode() : 0));
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			return transformQuads(key.base.getQuads(key.state, key.side, 0), key.textures);
		}
	});

	private ImmutableSet<String> textures;
	private boolean disableCache = false;

	public BakedModelBrightness(IBakedModel base, ImmutableSet<String> textures)
	{
		super(base);
		this.textures = textures;
	}

	public BakedModelBrightness disableCache()
	{
		this.disableCache = true;
		return this;
	}

	// FIXME Caching for items with animated textures
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
	{
		if (this.disableCache || state == null) return transformQuads(this.originalModel.getQuads(state, side, 0), textures);
		return CACHE.getUnchecked(new CacheKey(this.originalModel, textures, state instanceof IExtendedBlockState ? ((IExtendedBlockState) state).getClean() : state, side));
	}

	private static List<BakedQuad> transformQuads(List<BakedQuad> oldQuads, Set<String> textures)
	{
		List<BakedQuad> quads = new ArrayList<>(oldQuads);
		for (int i = 0; i < quads.size(); ++i)
		{
			BakedQuad quad = quads.get(i);
			if (textures.contains(quad.getSprite().getIconName())) quads.set(i, transformQuad(quad, 15 << 20 | 15 << 4)); // FULLBRIGHT
		}
		return quads;
	}

	private static BakedQuad transformQuad(BakedQuad quad, int brightness)
	{
		if (HexUtilities.isLightMapDisabled()) return quad;
		VertexFormat newFormat = HexUtilities.getFormatWithLightMap(quad.getFormat());
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(newFormat);
		VertexLighterFlat trans = new VertexLighterFlat(Minecraft.getMinecraft().getBlockColors())
		{
			@Override
			protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z)
			{
				lightmap[0] = ((float)((brightness >> 0x04) & 0xF) * 0x20) / 0xFFFF;
				lightmap[1] = ((float)((brightness >> 0x14) & 0xF) * 0x20) / 0xFFFF;
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
		builder.setApplyDiffuseLighting(false);
		return builder.build();
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType type)
	{
		Pair<? extends IBakedModel, Matrix4f> base = super.handlePerspective(type);
		return base.getLeft() instanceof BakedModelBrightness ? base : Pair.of(new BakedModelBrightness(base.getLeft(), this.textures), base.getRight());
	}
}