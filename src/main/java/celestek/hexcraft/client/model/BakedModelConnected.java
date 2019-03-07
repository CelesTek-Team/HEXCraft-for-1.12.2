package celestek.hexcraft.client.model;

import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import celestek.hexcraft.common.block.HexBlockConnected;
import celestek.hexcraft.utility.HexShapes;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//FIXME Figure out and implement isAmbientOcclusion, isGui3d, isBuiltInRenderer
//FIXME Allow setting tint index from model file
@SideOnly(Side.CLIENT)
public class BakedModelConnected implements IBakedModel
{
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
			if (this.face != key.face) return false;
			if (this.state != key.state) return false; // Careful with this comparison in case block states are changed and are no longer compared like this
			if(this.faceState != key.faceState) return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			return this.state != null ? this.state.hashCode() : 0 + (31 * (this.face != null ? this.face.hashCode() : 0)) + 37 * faceState; // Is this proper?
		}
	}

	private static final LoadingCache<CacheKey, BakedQuad> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, BakedQuad>()
	{
		@Override
		public BakedQuad load(CacheKey key)
		{
			return HexShapes.Cube.create(key.model.format, key.face, -1, key.model.sprites.get(key.faceState));
		}
	});

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing face, long rand)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		if(!(state instanceof IExtendedBlockState) || face == null) return quads;
		IExtendedBlockState extended = (IExtendedBlockState) state;
		int faceState = extended.getValue(HexBlockConnected.FACE_STATES).get(face);
		if(!this.enableCache) quads.add(HexShapes.Cube.create(this.format, face, -1, this.sprites.get(faceState)));
		else quads.add(CACHE.getUnchecked(new CacheKey(this, extended.getClean(), face, faceState)));
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return false;
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