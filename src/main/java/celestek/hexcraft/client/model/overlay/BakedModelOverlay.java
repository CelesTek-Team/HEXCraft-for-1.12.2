package celestek.hexcraft.client.model.overlay;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import celestek.hexcraft.utility.HexShapes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedModelOverlay implements IBakedModel
{
	public static class Layer
	{
		public final TextureAtlasSprite sprite;
		public final int tint;
		public final ImmutableSet<BlockRenderLayer> renderLayers;
		public final boolean renderCracks;
		public final boolean shade;

		public Layer(TextureAtlasSprite sprite, int tint, ImmutableSet<BlockRenderLayer> renderLayers, boolean renderCracks, boolean shade)
		{
			this.sprite = sprite;
			this.tint =  tint;
			this.renderLayers = renderLayers;
			this.renderCracks = renderCracks;
			this.shade = shade;
		}

		public List<BakedQuad> bake(List<BakedQuad> quads, VertexFormat format, EnumFacing face)
		{
			return HexShapes.Cube.create(quads, format, face, this.tint, this.shade, this.sprite);
		}

		public boolean canRender(BlockRenderLayer layer)
		{
			return this.renderCracks && layer == null || this.renderLayers.contains(layer);
		}

		@Override
		public boolean equals(Object other)
		{
			if(this == other) return true;
			if(other == null || this.getClass() != other.getClass()) return false;
			Layer layer = (Layer) other;
			return this.sprite.equals(layer.sprite) && this.tint == layer.tint && this.renderCracks == layer.renderCracks && this.shade == layer.shade && this.renderLayers.equals(layer.renderLayers);
		}

		@Override
		public int hashCode()
		{
			int hash = this.sprite == null ? 0 : this.sprite.hashCode();
			hash = 31 * hash + this.tint;
			hash = 31 * hash + (this.renderLayers == null ? 0 : this.renderLayers.hashCode());
			hash = 31 * hash + (this.renderCracks ? 1 : 0);
			hash = 31 * hash + (this.shade ? 1 : 0);
			return hash;
		}
	}

	private class CacheKey
	{
		protected BakedModelOverlay model;
		protected IBlockState state;
		protected EnumFacing face;
		protected Layer layer;

		public CacheKey(BakedModelOverlay model, IBlockState state, EnumFacing face, Layer layer)
		{
			this.model = model;
			this.state = state;
			this.face = face;
			this.layer = layer;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey key = (CacheKey) o;
			return this.face == key.face && this.state == key.state && this.layer.equals(key.layer); // Careful with this comparison in case block states are changed and are no longer compared like this
		}

		@Override
		public int hashCode()
		{
			int hash = this.state == null ? 0 : this.state.hashCode();
			hash = 31 * hash + (this.face == null ? 0 : this.face.hashCode());
			hash = 31 * hash + (this.layer == null ? 0 : this.layer.hashCode());
			return hash;
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			return key.layer.bake(Lists.newArrayList(), key.model.format, key.face);
		}
	});

	protected final IModelState state;
	protected final VertexFormat format;
	protected boolean ambientOcclusion;

	protected final ImmutableList<Layer> layers;
	protected final TextureAtlasSprite particle;

	protected boolean enableCache = false;

	public BakedModelOverlay(IModelState state, VertexFormat format, boolean ambientOcclusion, ImmutableList<Layer> layers, TextureAtlasSprite particle)
	{
		this.state = state;
		this.format = format;
		this.ambientOcclusion = ambientOcclusion;
		this.layers = layers;
		this.particle = particle;
	}

	public BakedModelOverlay setCache(boolean flag)
	{
		this.enableCache = flag;
		return this;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing face, long rand)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		if(face == null) return quads;
		BlockRenderLayer renderLayer = MinecraftForgeClient.getRenderLayer();
		for(Layer layer : this.layers) if(layer.canRender(renderLayer))
		{
			if(this.enableCache) quads.addAll(CACHE.getUnchecked(new CacheKey(this, state, face, layer)));
			else layer.bake(quads, this.format, face);
		}
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return this.ambientOcclusion;
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
		return this.particle;
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