package celestek.hexcraft.client.model.connected;

import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import celestek.hexcraft.utility.HexShapes;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//FIXME Allow setting tint index from model file
@SideOnly(Side.CLIENT)
public class BakedModelConnectedOverlay extends BakedModelConnected
{
	protected TextureAtlasSprite base;

	public BakedModelConnectedOverlay(VertexFormat format, ImmutableList<TextureAtlasSprite> sprites, TextureAtlasSprite base)
	{
		super(format, sprites);
		this.base = base;
	}

	private class CacheKey
	{
		protected BakedModelConnectedOverlay model;
		protected IBlockState state;
		protected EnumFacing face;

		public CacheKey(BakedModelConnectedOverlay model, IBlockState state, EnumFacing face)
		{
			this.model = model;
			this.state = state;
			this.face = face;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey key = (CacheKey) o;
			if (this.face != key.face) return false;
			if (this.state != key.state) return false; // Careful with this comparison in case block states are changed and are no longer compared like this
			return true;
		}

		@Override
		public int hashCode()
		{
			return this.state != null ? this.state.hashCode() : 0 + (31 * (this.face != null ? this.face.hashCode() : 0));
		}
	}

	private static final LoadingCache<CacheKey, BakedQuad> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, BakedQuad>()
	{
		@Override
		public BakedQuad load(CacheKey key)
		{
			return HexShapes.Cube.create(key.model.format, key.face, 0, key.model.base);
		}
	});

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing face, long rand)
	{
		List<BakedQuad> quads = super.getQuads(state, face, rand);
		if(!(state instanceof IExtendedBlockState) || face == null) return quads;
		if(!this.enableCache) quads.add(0, HexShapes.Cube.create(this.format, face, 0, this.base));
		else quads.add(0, CACHE.getUnchecked(new CacheKey(this, state instanceof IExtendedBlockState ? ((IExtendedBlockState) state).getClean() : state, face)));
		return quads;
	}
}