package celestek.hexcraft.common.block;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class HexBlockConnected extends HexBlock
{
	public static final IUnlistedProperty<ImmutableMap<EnumFacing, Integer>> FACE_STATES = new PropertyStates("face_states");

	public static class PropertyStates implements IUnlistedProperty<ImmutableMap<EnumFacing, Integer>>
	{
		private final String name;

		public PropertyStates(String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		// FIXME Check the values from 0-46
		@Override
		public boolean isValid(ImmutableMap<EnumFacing, Integer> value)
		{
			return true;
		}

		@Override
		public Class getType()
		{
			return ImmutableMap.class;
		}

		@Override
		public String valueToString(ImmutableMap<EnumFacing, Integer> value)
		{
			return value.toString();
		}
	}

	public HexBlockConnected(String name, CreativeTabs tab, Material material, int color, String... textures)
	{
		super(name, tab, material, color, textures);
	}

	@Override
	public boolean enableCache()
	{
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] {FACE_STATES});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos position)
	{
		ImmutableMap.Builder<EnumFacing, Integer> builder = ImmutableMap.builder();
		for(EnumFacing face : EnumFacing.values()) builder.put(face, this.getState(world, position, face));
		return ((IExtendedBlockState) state).withProperty(FACE_STATES, builder.build());
	}

	private static final int[] ids =
	{
		 0,  0,  6,  6,  0,  0,  6,  6,  3,  3, 19, 15,  3,  3, 19, 15,
		 1,  1, 18, 18,  1,  1, 13, 13,  2,  2, 23, 31,  2,  2, 27, 14,
		 0,  0,  6,  6,  0,  0,  6,  6,  3,  3, 19, 15,  3,  3, 19, 15,
		 1,  1, 18, 18,  1,  1, 13, 13,  2,  2, 23, 31,  2,  2, 27, 14,
		 4,  4,  5,  5,  4,  4,  5,  5, 17, 17, 22, 26, 17, 17, 22, 26,
		16, 16, 20, 20, 16, 16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38,
		 4,  4,  5,  5,  4,  4,  5,  5,  9,  9, 30, 12,  9,  9, 30, 12,
		16, 16, 20, 20, 16, 16, 28, 28, 25, 25, 45, 37, 25, 25, 40, 32,
		 0,  0,  6,  6,  0,  0,  6,  6,  3,  3, 19, 15,  3,  3, 19, 15,
		 1,  1, 18, 18,  1,  1, 13, 13,  2,  2, 23, 31,  2,  2, 27, 14,
		 0,  0,  6,  6,  0,  0,  6,  6,  3,  3, 19, 15,  3,  3, 19, 15,
		 1,  1, 18, 18,  1,  1, 13, 13,  2,  2, 23, 31,  2,  2, 27, 14,
		 4,  4,  5,  5,  4,  4,  5,  5, 17, 17, 22, 26, 17, 17, 22, 26,
		 7,  7, 24, 24,  7,  7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33,
		 4,  4,  5,  5,  4,  4,  5,  5,  9,  9, 30, 12,  9,  9, 30, 12,
		 7,  7, 24, 24,  7,  7, 10, 10,  8,  8, 36, 35,  8,  8, 34, 11
	};

	protected int getState(IBlockAccess world, BlockPos position, EnumFacing face)
	{
		boolean[] connections = new boolean[8];
		switch(face)
		{
		// Had to mirror traversal for down
		case DOWN:
			connections[0] = this.canConnect(world, position, position.add(-1, 0, 1));
			connections[1] = this.canConnect(world, position, position.add(0, 0, 1));
			connections[2] = this.canConnect(world, position, position.add(1, 0, 1));
			connections[3] = this.canConnect(world, position, position.add(-1, 0, 0));
			connections[4] = this.canConnect(world, position, position.add(1, 0, 0));
			connections[5] = this.canConnect(world, position, position.add(-1, 0, -1));
			connections[6] = this.canConnect(world, position, position.add(0, 0,  -1));
			connections[7] = this.canConnect(world, position, position.add(1, 0, -1));
			break;
		case UP:
			connections[0] = this.canConnect(world, position, position.add(-1, 0, -1));
			connections[1] = this.canConnect(world, position, position.add(0, 0, -1));
			connections[2] = this.canConnect(world, position, position.add(1, 0, -1));
			connections[3] = this.canConnect(world, position, position.add(-1, 0, 0));
			connections[4] = this.canConnect(world, position, position.add(1, 0, 0));
			connections[5] = this.canConnect(world, position, position.add(-1, 0, 1));
			connections[6] = this.canConnect(world, position, position.add(0, 0,  1));
			connections[7] = this.canConnect(world, position, position.add(1, 0, 1));
			break;
		case NORTH:
			connections[0] = this.canConnect(world, position, position.add(1, 1, 0));
			connections[1] = this.canConnect(world, position, position.add(0, 1, 0));
			connections[2] = this.canConnect(world, position, position.add(-1, 1, 0));
			connections[3] = this.canConnect(world, position, position.add(1, 0, 0));
			connections[4] = this.canConnect(world, position, position.add(-1, 0, 0));
			connections[5] = this.canConnect(world, position, position.add(1, -1, 0));
			connections[6] = this.canConnect(world, position, position.add(0, -1, 0));
			connections[7] = this.canConnect(world, position, position.add(-1, -1, 0));
			break;
		case SOUTH:
			connections[0] = this.canConnect(world, position, position.add(-1, 1, 0));
			connections[1] = this.canConnect(world, position, position.add(0, 1, 0));
			connections[2] = this.canConnect(world, position, position.add(1, 1, 0));
			connections[3] = this.canConnect(world, position, position.add(-1, 0, 0));
			connections[4] = this.canConnect(world, position, position.add(1, 0, 0));
			connections[5] = this.canConnect(world, position, position.add(-1, -1, 0));
			connections[6] = this.canConnect(world, position, position.add(0, -1, 0));
			connections[7] = this.canConnect(world, position, position.add(1, -1, 0));
			break;
		case WEST:
			connections[0] = this.canConnect(world, position, position.add(0, 1, -1));
			connections[1] = this.canConnect(world, position, position.add(0, 1, 0));
			connections[2] = this.canConnect(world, position, position.add(0, 1, 1));
			connections[3] = this.canConnect(world, position, position.add(0, 0, -1));
			connections[4] = this.canConnect(world, position, position.add(0, 0, 1));
			connections[5] = this.canConnect(world, position, position.add(0, -1, -1));
			connections[6] = this.canConnect(world, position, position.add(0, -1, 0));
			connections[7] = this.canConnect(world, position, position.add(0, -1, 1));
			break;
		case EAST:
			connections[0] = this.canConnect(world, position, position.add(0, 1, 1));
			connections[1] = this.canConnect(world, position, position.add(0, 1, 0));
			connections[2] = this.canConnect(world, position, position.add(0, 1, -1));
			connections[3] = this.canConnect(world, position, position.add(0, 0, 1));
			connections[4] = this.canConnect(world, position, position.add(0, 0, -1));
			connections[5] = this.canConnect(world, position, position.add(0, -1, 1));
			connections[6] = this.canConnect(world, position, position.add(0, -1, 0));
			connections[7] = this.canConnect(world, position, position.add(0, -1, -1));
			break;
		}
		int id = 0;
		for (int i = 0; i < 8; i++) id = id + (connections[i] ? (i == 0 ? 1 : (i == 1 ? 2 : (i == 2 ? 4 : (i == 3 ? 8 : (i == 4 ? 16 : (i == 5 ? 32 : (i == 6 ? 64 : 128))))))) : 0);
		return ids[id];
	}

	protected boolean canConnect(IBlockAccess world, BlockPos position, BlockPos toPosition)
	{
		return world.getBlockState(toPosition).getBlock() == this;
	}
}