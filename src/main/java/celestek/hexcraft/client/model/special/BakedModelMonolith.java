package celestek.hexcraft.client.model.special;

import java.util.List;
import java.util.Optional;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

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

/**
 * A baked model of a special hexagonal prism which uses a single texture. Supports quad caching, matrix and item perspective transforms
 */
@SideOnly(Side.CLIENT)
public class BakedModelMonolith implements IBakedModel
{
	/**
	 * A cache key used to store different quads of the model
	 */
	/*
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
			return HexShapes.Monolith.create(Lists.newArrayList(), key.model.format, key.model.transform, null, 0, false, key.model.sprite);
		}
	});
	*/

	/**
	 * The item perspective transforms defined under the "transform" tag in the blockstate
	 */
	protected final IModelState state;
	/**
	 * The vertex format in which all of the model's quads should be drawn in
	 */
	protected final VertexFormat format;
	/**
	 * The matrix transforms generated by {@link BakedModelMonolith}
	 */
	protected final Optional<TRSRTransformation> transform;

	protected final TextureAtlasSprite sprite;

	public BakedModelMonolith(IModelState state, VertexFormat format, Optional<TRSRTransformation> transform, TextureAtlasSprite sprite)
	{
		this.state = state;
		this.format = format;
		this.transform = transform;
		this.sprite = sprite;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) // Culling?
	{
		List<BakedQuad> quads = Lists.newArrayList();
		// Don't draw anything if faces should be culled
		if(side != null) return quads;
		// Draw the quads
		return HexShapes.Monolith.create(quads, this.format, this.transform, null, 0, false, this.sprite);
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return true;
	}

	@Override
	public boolean isGui3d()
	{
		return true;
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