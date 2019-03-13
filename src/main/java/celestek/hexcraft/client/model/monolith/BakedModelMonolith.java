package celestek.hexcraft.client.model.monolith;

import java.util.List;
import java.util.Optional;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import celestek.hexcraft.utility.HexShapes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedModelMonolith implements IBakedModel
{
	protected final IModelState state;
	protected final VertexFormat format;
	protected final Optional<TRSRTransformation> transform;
	protected final TextureAtlasSprite sprite;
	private boolean enableCache = true;

	public BakedModelMonolith(IModelState state, VertexFormat format, Optional<TRSRTransformation> transform, TextureAtlasSprite sprite)
	{
		this.state = state;
		this.format = format;
		this.transform = transform;
		this.sprite = sprite;
	}

	public BakedModelMonolith setCache(boolean flag)
	{
		this.enableCache = flag;
		return this;
	}

	private class CacheKey
	{
		protected BakedModelMonolith model;
		protected IBlockState state;

		public CacheKey(BakedModelMonolith model, IBlockState state)
		{
			this.model = model;
			this.state = state;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey key = (CacheKey) o;
			return this.state == key.state; // Careful with this comparison in case block states are changed and are no longer compared like this
		}

		@Override
		public int hashCode()
		{
			return this.state != null ? this.state.hashCode() : 0;
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			return HexShapes.Monolith.create(Lists.newArrayList(), key.model.format, key.model.transform, 0, false, key.model.sprite);
		}
	});

	// FIXME Culling?
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		if(side != null) return quads;
		if(!this.enableCache) return HexShapes.Monolith.create(quads, this.format, this.transform, 0, false, this.sprite);
		else return CACHE.getUnchecked(new CacheKey(this, state));
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return true;
	}

	@Override
	public boolean isGui3d()
	{
		return false;
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return this.sprite;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType transform)
	{
		return PerspectiveMapWrapper.handlePerspective(this, this.state, transform);
	}
}