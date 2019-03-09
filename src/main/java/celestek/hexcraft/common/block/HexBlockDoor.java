package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.client.model.HexStateMapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.BlockDoor.EnumHingePosition;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HexBlockDoor extends HexBlockReinforceable
{
	public static final PropertyEnum<EnumDoorHalf> HALF = BlockDoor.HALF; // Stored on both blocks
	public static final PropertyDirection FACING = BlockHorizontal.FACING; // Stored on lower block
	public static final PropertyBool OPEN = BlockDoor.OPEN; // Stored on lower block
	public static final PropertyEnum<EnumHingePosition> HINGE = BlockDoor.HINGE; // Stored on upper block
	public static final PropertyBool POWERED = BlockDoor.POWERED; // Stored on upper block
	public static final PropertyBool REINFORCED = HexBlockReinforceable.REINFORCED; // Stored on upper block

	protected static final AxisAlignedBB BOUNDS_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB BOUNDS_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
	protected static final AxisAlignedBB BOUNDS_WEST = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB BOUNDS_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);	

	public HexBlockDoor(String name, Optional<HexStateMapper> mapper, CreativeTabs tab, Material material, int color, String... textures)
	{
		super(name, mapper, tab, material, color, textures);
		this.setDefaultState(this.getDefaultState().withProperty(HALF, EnumDoorHalf.LOWER).withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, false).withProperty(HINGE, EnumHingePosition.LEFT).withProperty(POWERED, false));
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos position, EnumFacing side)
	{
		return side == EnumFacing.UP && this.canPlaceBlockAt(world, position);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos position) //FIXME Can player edit and isTopSolid || solid face shape like in BlockDoor?
	{
		BlockPos upPosition = position.up();
		BlockPos downPosition = position.down();
		return position.getY() < world.getHeight() - 1 && world.getBlockState(downPosition).isSideSolid(world, downPosition, EnumFacing.UP) && super.canPlaceBlockAt(world, position) && super.canPlaceBlockAt(world, upPosition); 
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos position, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		EnumFacing facing = EnumFacing.fromAngle((double) placer.rotationYaw);
		boolean rightHinge = false;
		BlockPos rightPosition = position.offset(facing.rotateY());
		BlockPos leftPosition = position.offset(facing.rotateYCCW());
		if(world.getBlockState(rightPosition).getBlock() == this && world.getBlockState(rightPosition.up()).getBlock() == this) rightHinge = false;
		else if(world.getBlockState(leftPosition).getBlock() == this && world.getBlockState(leftPosition.up()).getBlock() == this) rightHinge = true;
		else
		{
			int x = facing.getFrontOffsetX();
			int y = facing.getFrontOffsetZ();
			rightHinge = x < 0 && hitZ < 0.5F || x > 0 && hitZ > 0.5F || y < 0 && hitX > 0.5F || y > 0 && hitX < 0.5F;
		}
		boolean powered = world.isBlockPowered(position) || world.isBlockPowered(position.up());
		return this.getDefaultState().withProperty(HALF, EnumDoorHalf.LOWER).withProperty(FACING, facing).withProperty(HINGE, rightHinge ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty(OPEN, powered).withProperty(POWERED, powered);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(position.up(), state.withProperty(HALF, EnumDoorHalf.UPPER));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos position)
	{
		//state = state.getActualState(source, position);
		boolean closed = !state.getValue(OPEN);
		boolean right = state.getValue(HINGE) == EnumHingePosition.RIGHT;
		switch (state.getValue(FACING))
		{
		case NORTH: return closed ? BOUNDS_NORTH : (right ? BOUNDS_WEST : BOUNDS_EAST);
		case SOUTH: return closed ? BOUNDS_SOUTH : (right ? BOUNDS_EAST : BOUNDS_WEST);
		case WEST: return closed ? BOUNDS_WEST : (right ? BOUNDS_SOUTH : BOUNDS_NORTH);
		case EAST: return closed ? BOUNDS_EAST : (right ? BOUNDS_NORTH : BOUNDS_SOUTH);
		default: return FULL_BLOCK_AABB;
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		/*
		BlockPos offsetPosition = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? position : position.down();
		IBlockState offsetState = position.equals(offsetPosition) ? state : world.getBlockState(offsetPosition);
		if (offsetState.getBlock() != this) return false;
		else
		{
			state = offsetState.cycleProperty(OPEN);
			world.setBlockState(offsetPosition, state, 10);
			world.markBlockRangeForRenderUpdate(offsetPosition, position);
			return true;
		}
		*/
		world.setBlockState(position, state.cycleProperty(OPEN));
		world.playEvent(player, state.getValue(OPEN) ? 1011 : 1005, position, 0);
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos position, Block block, BlockPos fromPosition)
	{
		BlockPos upPosition = position.up();
		BlockPos downPosition = position.down();
		if(state.getValue(HALF) == EnumDoorHalf.UPPER)
		{
			IBlockState downState = world.getBlockState(downPosition);
			if(downState.getBlock() != this) world.setBlockToAir(position);
			else world.setBlockState(position, downState.withProperty(HALF, EnumDoorHalf.UPPER));
		}
		else
		{
			IBlockState upState = world.getBlockState(upPosition);
			IBlockState downState = world.getBlockState(downPosition);
			if(upState.getBlock() != this || !downState.isSideSolid(world, downPosition, EnumFacing.UP)) world.setBlockToAir(position);
			else world.setBlockState(position, upState.withProperty(HALF, EnumDoorHalf.LOWER));
		}
		if ((world.isBlockPowered(position) || world.isBlockPowered(state.getValue(HALF) == EnumDoorHalf.LOWER ? upPosition : downPosition)) != state.getValue(POWERED))
		{
			world.setBlockState(position, state.cycleProperty(POWERED));
			if(state.getValue(POWERED) == state.getValue(OPEN))
			{
				world.setBlockState(position, state.cycleProperty(OPEN));
				world.playEvent(null, state.getValue(OPEN) ? 1011 : 1005, position, 0);
			}
		}
		/*
		if (state.getValue(HALF) == EnumDoorHalf.UPPER)
		{
			BlockPos offsetPosition = position.down();
			IBlockState offsetState = world.getBlockState(offsetPosition);
			if (offsetState.getBlock() != this) world.setBlockToAir(position);
			else if (block != this) offsetState.neighborChanged(world, offsetPosition, block, fromPosition);
		}
		else
		{
			BlockPos upPosition = position.up();
			IBlockState upState = world.getBlockState(upPosition);
			if (upState.getBlock() != this)
			{
				world.setBlockToAir(position);
				if (!world.isRemote) this.dropBlockAsItem(world, position, state, 0);
				return;
			}
			BlockPos downPosition = position.down();
			if (!world.getBlockState(downPosition).isSideSolid(world, downPosition, EnumFacing.UP))
			{
				world.setBlockToAir(position);
				if (!world.isRemote) this.dropBlockAsItem(world, position, state, 0);
				if (upState.getBlock() == this) world.setBlockToAir(upPosition);
				return;
			}
			boolean powered = world.isBlockPowered(position) || world.isBlockPowered(upPosition);
			if (block != this && (powered || block.getDefaultState().canProvidePower()) && powered != upState.getValue(POWERED))
			{
				world.setBlockState(upPosition, upState.withProperty(POWERED, powered), 2);
				if (powered != state.getValue(OPEN))
				{
					world.setBlockState(position, state.withProperty(OPEN, powered), 2);
					world.markBlockRangeForRenderUpdate(position, position);
					world.playEvent(null, powered ? 1005 : 1011, position, 0);
				}
			}
		}
		*/
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position)
	{
		if(state.getValue(HALF) == EnumDoorHalf.LOWER)
		{
			IBlockState offsetState = world.getBlockState(position.up());
			if (offsetState.getBlock() == this) state = state.withProperty(HINGE, offsetState.getValue(HINGE)).withProperty(POWERED, offsetState.getValue(POWERED)).withProperty(REINFORCED, offsetState.getValue(REINFORCED));
		}
		else
		{
			IBlockState offsetState = world.getBlockState(position.down());
			if (offsetState.getBlock() == this) state = state.withProperty(FACING, offsetState.getValue(FACING)).withProperty(OPEN, offsetState.getValue(OPEN));
		}
		return state;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {HALF, FACING, OPEN, HINGE, POWERED, REINFORCED});
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;
		if(state.getValue(HALF) == EnumDoorHalf.LOWER) // 1 bit is half, 2 is open and 3-4 are facing
		{
			if(state.getValue(OPEN)) meta |= 4;
			meta |= state.getValue(FACING).getHorizontalIndex();
		}
		else // 1 bit is half, 2 is hinge, 3 is powered and 4 is reinforced
		{
			meta |= 8;
			if(state.getValue(HINGE) == EnumHingePosition.RIGHT) meta |= 4;
			if(state.getValue(POWERED)) meta |= 2;
			if(state.getValue(REINFORCED)) meta |= 1;
		}
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = this.getDefaultState();
		if((meta & 8) == 0)
		{
			state = state.withProperty(HALF, EnumDoorHalf.LOWER);
			state = state.withProperty(OPEN, (meta & 4) != 0);
			state = state.withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
		}
		else
		{
			state = state.withProperty(HALF, EnumDoorHalf.UPPER);
			state = state.withProperty(HINGE, (meta & 4) != 0 ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT);
			state = state.withProperty(POWERED, (meta & 2) != 0);
			state = state.withProperty(REINFORCED, (meta & 1) != 0);
		}
		return state;
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos position)
	{
		return world.getBlockState(position).getValue(OPEN);
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.DESTROY;
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
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos position, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}