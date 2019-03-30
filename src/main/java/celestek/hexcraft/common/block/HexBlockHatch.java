package celestek.hexcraft.common.block;

import celestek.hexcraft.utility.EHexColor;
import celestek.hexcraft.utility.HexShapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HexBlockHatch extends HexBlockReinforceable
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool OPEN = BlockTrapDoor.OPEN;

	protected static final AxisAlignedBB EAST_OPEN_AABB = new AxisAlignedBB(0d, 0d, 0d, HexShapes.Door.dThck, 1d, 1d);
	protected static final AxisAlignedBB WEST_OPEN_AABB = new AxisAlignedBB(1d - HexShapes.Door.dThck, 0d, 0d, 1d, 1d, 1d);
	protected static final AxisAlignedBB SOUTH_OPEN_AABB = new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, HexShapes.Door.dThck);
	protected static final AxisAlignedBB NORTH_OPEN_AABB = new AxisAlignedBB(0d, 0d, 1d - HexShapes.Door.dThck, 1d, 1d, 1d);
	protected static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0d, 0d, 0d, 1d, HexShapes.Door.dThck, 1d);
	protected static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0d, 1d - HexShapes.Door.dThck, 0d, 1d, 1d, 1d);

	public HexBlockHatch(String name, CreativeTabs tab, Material material, EHexColor color)
	{
		super(name, tab, material, color);
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, false));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if(!state.getValue(OPEN)) return BOTTOM_AABB;
		switch (state.getValue(FACING))
		{
		case NORTH:  return NORTH_OPEN_AABB;
		case SOUTH: return SOUTH_OPEN_AABB;
		case WEST: return WEST_OPEN_AABB;
		case EAST: return EAST_OPEN_AABB;
		default: return FULL_BLOCK_AABB;
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos position, EnumFacing side)
	{
		return true;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos position, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = this.getDefaultState();
		if (facing.getAxis().isHorizontal()) state = state.withProperty(FACING, facing);
		else state = state.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		state = state.withProperty(OPEN, world.isBlockPowered(position));
		return state;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		world.setBlockState(position, state.cycleProperty(OPEN), 2);
		world.playEvent(player, state.getValue(OPEN) ? 1036 : 1037, position, 0);
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos position, Block block, BlockPos fromPos)
	{
		if(world.isRemote) return;
		boolean powered = world.isBlockPowered(position);
		if (powered || block.getDefaultState().canProvidePower())
		{
			if (state.getValue(OPEN) != powered)
			{
				world.setBlockState(position, state.withProperty(OPEN, Boolean.valueOf(powered)), 2);
				world.playEvent(null, state.getValue(OPEN) ? 1036 : 1037, position, 0);
			}
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING, OPEN, REINFORCED});
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos position, EntityLivingBase entity)
	{
		if (!state.getValue(OPEN)) return false;
		IBlockState downState = world.getBlockState(position.down());
		if (downState.getBlock() != Blocks.LADDER) return false;
		return downState.getValue(BlockLadder.FACING) == state.getValue(FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		// bits 1 is open, 2 is reinforced and 3-4 are facing
		int meta = 0;
		if(state.getValue(OPEN)) meta |= 0b1000;
		if(state.getValue(REINFORCED)) meta |= 0b0100;
		meta |= state.getValue(FACING).getHorizontalIndex();
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = this.getDefaultState();
		state = state.withProperty(OPEN, (meta & 0b1000) != 0);
		state = state.withProperty(REINFORCED, (meta & 0b0100) != 0);
		state = state.withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011));
		return state;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return face == EnumFacing.DOWN && !state.getValue(OPEN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos).getValue(OPEN);
	}
}