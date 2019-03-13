package celestek.hexcraft.client.model.connected;

import java.util.List;

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

//FIXME Figure out and implement isAmbientOcclusion, isGui3d, isBuiltInRenderer
//FIXME Allow setting tint index from model file
@SideOnly(Side.CLIENT)
public class BakedModelConnected implements IBakedModel
{
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
			return HexShapes.Cube.create(Lists.newArrayList(), key.model.format, key.face, -1, true, key.model.sprites.get(key.faceState));
		}
	});

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
		if(MinecraftForgeClient.getRenderLayer() != BlockRenderLayer.TRANSLUCENT || face == null || !(state instanceof IExtendedBlockState)) return quads;
		IExtendedBlockState extended = (IExtendedBlockState) state;
		int faceState = extended.getValue(HexBlockConnected.FACE_STATES).get(face);
		if(!this.enableCache) return HexShapes.Cube.create(quads, this.format, face, -1, true, this.sprites.get(faceState));
		else return CACHE.getUnchecked(new CacheKey(this, extended.getClean(), face, faceState));
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
		return this.sprites.get(0);
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}
}