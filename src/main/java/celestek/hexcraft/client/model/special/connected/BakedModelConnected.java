package celestek.hexcraft.client.model.special.connected;

import java.util.List;
import java.util.Optional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
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
 * A baked model of a cube which uses 47 textures. It receives the information about each face from {@link HexBlockConnected} before rendering and renders the correct textures on the translucent layer.
 * Supports quad caching
 */
@SideOnly(Side.CLIENT)
public class BakedModelConnected implements IBakedModel //FIXME Allow setting tint index from model file
{
	/**
	 * A cache key used to store different quads of the model
	 */
	private class CacheKey
	{
		protected BakedModelConnected model;
		protected IBlockState state;
		protected EnumFacing face;
		protected int faceState;

		public CacheKey(BakedModelConnected model, IBlockState state, EnumFacing face, int faceState)
		{
			this.model = model;
			this.state = state;
			this.face = face;
			this.faceState = faceState;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey key = (CacheKey) o;
			return this.face == key.face && this.state == key.state && this.faceState == key.faceState; // Careful with this comparison in case block states are changed and are no longer compared like this
		}

		@Override
		public int hashCode()
		{
			int hash = this.state == null ? 0 : this.state.hashCode();
			hash = 31 * hash + (this.face == null ? 0 : this.face.hashCode());
			hash = 31 * hash + this.faceState;
			return hash;
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			return HexShapes.Cube.create(Lists.newArrayList(), key.model.format, Optional.empty(), key.face, -1, true, key.model.sprites.get(key.faceState));
		}
	});

	/**
	 * The vertex format in which all of the model's quads should be drawn in
	 */
	protected final VertexFormat format;

	protected final ImmutableList<TextureAtlasSprite> sprites;

	protected boolean enableCache = true;

	public BakedModelConnected(VertexFormat format, ImmutableList<TextureAtlasSprite> sprites)
	{
		this.format = format;
		this.sprites = sprites;
	}

	public BakedModelConnected setCache(boolean flag)
	{
		this.enableCache = flag;
		return this;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing face, long rand)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		// Don't draw anything if on the wrong render layer, if no extra data was passed or if no faces should be culled
		if(MinecraftForgeClient.getRenderLayer() != BlockRenderLayer.TRANSLUCENT || face == null || !(state instanceof IExtendedBlockState)) return quads;
		IExtendedBlockState extended = (IExtendedBlockState) state;
		// Get the information about each face from the extended blockstate returned by HexBlockConnected
		int faceState = extended.getValue(HexBlockConnected.FACE_STATES).get(face);
		// Draw the quads
		if(!this.enableCache) return HexShapes.Cube.create(quads, this.format, Optional.empty(), face, -1, true, this.sprites.get(faceState));
		else return CACHE.getUnchecked(new CacheKey(this, extended.getClean(), face, faceState));
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return false;
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
		return this.sprites.get(0);
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}
}