package celestek.hexcraft.client.model.connected;

import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import celestek.hexcraft.utility.HexShapes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//FIXME Allow setting tint index from model file
@SideOnly(Side.CLIENT)
public class BakedModelConnectedOverlay extends BakedModelConnected
{
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
			return this.face == key.face && this.state == key.state; // Careful with this comparison in case block states are changed and are no longer compared like this
		}

		@Override
		public int hashCode()
		{
			int hash = this.state == null ? 0 : this.state.hashCode();
			hash = 31 * hash + (this.face == null ? 0 : this.face.hashCode());
			return hash;
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			return HexShapes.Cube.create(Lists.newArrayList(), key.model.format, key.face, 0, false, key.model.base);
		}
	});

	protected TextureAtlasSprite base;

	public BakedModelConnectedOverlay(VertexFormat format, ImmutableList<TextureAtlasSprite> sprites, TextureAtlasSprite base)
	{
		super(format, sprites);
		this.base = base;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing face, long rand)
	{
		List<BakedQuad> quads = super.getQuads(state, face, rand);
		BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
		if(layer != BlockRenderLayer.SOLID && layer != null || face == null || !(state instanceof IExtendedBlockState)) return quads;
		if(!this.enableCache) quads.addAll(0, HexShapes.Cube.create(Lists.newArrayList(), this.format, face, 0, false, this.base));
		else quads.addAll(0, CACHE.getUnchecked(new CacheKey(this, state instanceof IExtendedBlockState ? ((IExtendedBlockState) state).getClean() : state, face)));
		return quads;
	}
}