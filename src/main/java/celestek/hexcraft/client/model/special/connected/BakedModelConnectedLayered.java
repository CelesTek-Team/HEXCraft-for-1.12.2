package celestek.hexcraft.client.model.special.connected;

import java.util.List;
import java.util.Optional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import celestek.hexcraft.common.block.HexBlockConnected;
import celestek.hexcraft.utility.HexShapes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A baked model of a cube which uses so called {@link Layer}s parsed by {@link ModelConnectedLayered} containing all the parameters used for rendering as well as a particle texture.
 * It receives the information about each face from {@link HexBlockConnected} before rendering. Supports quad caching and ambient occlusion
 */
@SideOnly(Side.CLIENT)
public class BakedModelConnectedLayered implements IBakedModel
{
	/**
	 * A wrapper which contains a list of sprites for each possible connection, a tint layer, the render layers in which it should render and configurable shading
	 */
	public static class Layer
	{
		/**
		 * A list of 47 sprites for each possible connection. If there aren't enough textures, the first one is used by default
		 */
		public final ImmutableList<TextureAtlasSprite> sprites;
		public final int tint;
		public final ImmutableSet<BlockRenderLayer> renderLayers;
		/**
		 * Effectively determines if this layer should render in the null render layer. This is required because guava's immutable API does not support null values
		 */
		public final boolean renderCracks;
		public final boolean shade;

		public Layer(ImmutableList<TextureAtlasSprite> sprites, int tint, ImmutableSet<BlockRenderLayer> renderLayers, boolean renderCracks, boolean shade)
		{
			this.sprites = sprites;
			this.tint = tint;
			this.renderLayers = renderLayers;
			this.renderCracks = renderCracks;
			this.shade = shade;
		}

		/**
		 * Adds new quads representing the given side of a cube with the layer's parameters depending on the connection in the given vertex format to the given list
		 */
		public List<BakedQuad> bake(List<BakedQuad> quads, VertexFormat format, EnumFacing face, int faceState) // Make class non-static and get format and shape from model?
		{
			return HexShapes.Cube.create(quads, format, Optional.empty(), face, this.tint, this.shade, this.sprites.size() > faceState ? this.sprites.get(faceState) : this.sprites.get(0));
		}

		/**
		 * Determines whether the layer can render in the given render layer
		 */
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
			return this.tint == layer.tint && this.renderCracks == layer.renderCracks && this.shade == layer.shade && this.renderLayers.equals(layer.renderLayers) && this.sprites.equals(layer.sprites);
		}

		@Override
		public int hashCode()
		{
			int hash = this.sprites == null ? 0 : this.sprites.hashCode();
			hash = 31 * hash + this.tint;
			hash = 31 * hash + (this.renderLayers == null ? 0 : this.renderLayers.hashCode());
			hash = 31 * hash + (this.renderCracks ? 1 : 0);
			hash = 31 * hash + (this.shade ? 1 : 0);
			return hash;
		}
	}

	/**
	 * A cache key used to store different quads of the model
	 */
	private class CacheKey
	{
		protected BakedModelConnectedLayered model;
		protected IBlockState state;
		protected EnumFacing face;
		protected Layer layer;
		protected int faceState;

		public CacheKey(BakedModelConnectedLayered model, IBlockState state, EnumFacing face, Layer layer, int faceState)
		{
			this.model = model;
			this.state = state;
			this.face = face;
			this.layer = layer;
			this.faceState = faceState;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey key = (CacheKey) o;
			return this.face == key.face && this.state == key.state && this.faceState == key.faceState && this.layer.equals(key.layer); // Careful with this comparison in case block states are changed and are no longer compared like this
		}

		@Override
		public int hashCode()
		{
			int hash = this.state == null ? 0 : this.state.hashCode();
			hash = 31 * hash + (this.face == null ? 0 : this.face.hashCode());
			hash = 31 * hash + (this.layer == null ? 0 : this.layer.hashCode());
			hash = 31 * hash + this.faceState;
			return hash;
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			return key.layer.bake(Lists.newArrayList(), key.model.format, key.face, key.faceState);
		}
	});

	/**
	 * The vertex format in which all of the model's quads should be drawn in
	 */
	protected final VertexFormat format;
	protected boolean ambientOcclusion;

	protected final ImmutableList<Layer> layers;
	protected final TextureAtlasSprite particle;

	protected boolean enableCache = true;

	public BakedModelConnectedLayered(VertexFormat format, boolean ambientOcclusion, ImmutableList<Layer> layers, TextureAtlasSprite particle)
	{
		this.format = format;
		this.ambientOcclusion = ambientOcclusion;
		this.layers = layers;
		this.particle = particle;
	}

	public BakedModelConnectedLayered setCache(boolean flag)
	{ 
		this.enableCache = flag;
		return this;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		// Don't draw anything if a no faces should be culled or no extra data was passed
		if(side == null || !(state instanceof IExtendedBlockState)) return quads;
		BlockRenderLayer renderLayer = MinecraftForgeClient.getRenderLayer();
		IExtendedBlockState extended = (IExtendedBlockState) state;
		// Get the information about each face from the extended blockstate returned by HexBlockConnected
		int faceState = extended.getValue(HexBlockConnected.FACE_STATES).get(side);
		// Draw each layer if in the right render layer
		for(Layer layer : this.layers) if(layer.canRender(renderLayer))
		{
			if(this.enableCache) quads.addAll(CACHE.getUnchecked(new CacheKey(this, state, side, layer, faceState)));
			else layer.bake(quads, this.format, side, faceState);
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
}