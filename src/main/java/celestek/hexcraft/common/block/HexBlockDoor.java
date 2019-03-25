package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Predicate;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.utility.HexShapes;
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

/**
 * A door block which can also be reinforced. This block behaves differently depending on its half property. See below for more details
 */
public class HexBlockDoor extends HexBlockReinforceable
{
	// Stored on lower block
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<EnumHingePosition> HINGE = BlockDoor.HINGE;
	// Stored on upper block
	public static final PropertyBool OPEN = BlockDoor.OPEN;
	// Stored on both blocks
	public static final PropertyBool REINFORCED = HexBlockReinforceable.REINFORCED;
	public static final PropertyEnum<EnumDoorHalf> HALF = BlockDoor.HALF;

	protected static final AxisAlignedBB BOUNDS_NORTH = new AxisAlignedBB(0d, 0d, 1d - HexShapes.Door.dThck, 1d, 1d, 1d);
	protected static final AxisAlignedBB BOUNDS_SOUTH = new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, HexShapes.Door.dThck);
	protected static final AxisAlignedBB BOUNDS_WEST = new AxisAlignedBB(1d - HexShapes.Door.dThck, 0d, 0d, 1d, 1d, 1d);
	protected static final AxisAlignedBB BOUNDS_EAST = new AxisAlignedBB(0d, 0d, 0d, HexShapes.Door.dThck, 1d, 1d);

	public HexBlockDoor(String name, Optional<HexStateMapper> mapper, CreativeTabs tab, Material material, int color, Optional<Predicate<String>> filter)
	{
		super(name, mapper, tab, material, color, filter);
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, false).withProperty(HINGE, EnumHingePosition.LEFT).withProperty(HALF, EnumDoorHalf.LOWER));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos position)
	{
		state = state.getActualState(source, position);
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
	public boolean canPlaceBlockOnSide(World world, BlockPos position, EnumFacing side)
	{
		return side == EnumFacing.UP && this.canPlaceBlockAt(world, position);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos position) //FIXME canPlayerEdit
	{
		BlockPos upPosition = position.up();
		BlockPos downPosition = position.down();
		return position.getY() < world.getHeight() - 1 && world.getBlockState(downPosition).getBlockFaceShape(world, downPosition, EnumFacing.UP) == BlockFaceShape.SOLID && super.canPlaceBlockAt(world, upPosition); 
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos position, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		// Set the facing from the player's rotation
		EnumFacing facing = EnumFacing.fromAngle((double) placer.rotationYaw);
		boolean rightHinge = false;
		BlockPos rightPosition = position.offset(facing.rotateY());
		BlockPos leftPosition = position.offset(facing.rotateYCCW());
		// Set the hinge position if placed next to another door
		if(world.getBlockState(rightPosition).getBlock() == this && world.getBlockState(rightPosition.up()).getBlock() == this) rightHinge = false;
		else if(world.getBlockState(leftPosition).getBlock() == this && world.getBlockState(leftPosition.up()).getBlock() == this) rightHinge = true;
		else
		{
			// Set the hinge position depending on the part of the clicked block
			int x = facing.getFrontOffsetX();
			int y = facing.getFrontOffsetZ();
			rightHinge = x < 0 && hitZ < 0.5F || x > 0 && hitZ > 0.5F || y < 0 && hitX > 0.5F || y > 0 && hitX < 0.5F;
		}
		// Also instantly open the door if placed on a powered block
		return this.getDefaultState().withProperty(HALF, EnumDoorHalf.LOWER).withProperty(FACING, facing).withProperty(HINGE, rightHinge ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty(OPEN, world.isBlockPowered(position) || world.isBlockPowered(position.up()));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(position.up(), state.withProperty(HALF, EnumDoorHalf.UPPER));
	}

	/**
	 * Returns the top half position of the door at the given position
	 */
	protected BlockPos getTop(World world, BlockPos position)
	{
		// Combine the properties of both door halves into one blockstate
		IBlockState state = world.getBlockState(position).getActualState(world, position);
		// FIXME if(state.getBlock() != this) return;
		// Return the correct position depending on the door half
		return state.getValue(HALF) == EnumDoorHalf.UPPER ? position : position.up();
	}

	/**
	 * Returns the position adjacent to the given door block depending on its hinge position
	 */
	protected BlockPos getAdjacent(World world, BlockPos position)
	{
		// Combine the properties of both door halves into one blockstate
		IBlockState state = world.getBlockState(position).getActualState(world, position);
		// FIXME if(state.getBlock() != this) return;
		// Returns the adjacent position depending on the door hinge position
		return position.offset(state.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT ? state.getValue(FACING).rotateYCCW() : state.getValue(FACING).rotateY());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		// Combine the properties of both door halves into one blockstate
		state = state.getActualState(world, position);
		// Get the top door half position
		BlockPos topPosition = this.getTop(world, position);
		// Combine the properties of both door halves into one blockstate
		IBlockState topState = world.getBlockState(topPosition).getActualState(world, topPosition);
		if(topState.getBlock() == this)
		{
			// If the top block is a door, toggle it and play a sound
			world.setBlockState(topPosition, topState.cycleProperty(OPEN), 10);
			world.playEvent(player, topState.getValue(OPEN) ? 1011 : 1005, position, 0);
			BlockPos adjacentPosition = this.getAdjacent(world, topPosition);
			// Combine the properties of both door halves into one blockstate
			IBlockState adjacentState = world.getBlockState(adjacentPosition).getActualState(world,adjacentPosition);
			// If both this and the adjacent door  are either open or closed and their hinge positions are mirrored, toggle the adjacent door too
			if(adjacentState.getBlock() == this && adjacentState.getValue(OPEN) == state.getValue(OPEN) && adjacentState.getValue(HINGE) != state.getValue(HINGE)) world.setBlockState(adjacentPosition, adjacentState.cycleProperty(OPEN), 10);
		}
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos position, Block block, BlockPos fromPosition) // FIXME DIAGONAL REDSTONE
	{
		// Combine the properties of both door halves into one blockstate
		state = state.getActualState(world, position);
		boolean top = state.getValue(HALF) == EnumDoorHalf.UPPER;
		BlockPos downPosition = position.down();
		// If this is the bottom half and the block below is no longer solid, break this half
		if(!top && world.getBlockState(downPosition).getBlockFaceShape(world, downPosition, EnumFacing.UP) != BlockFaceShape.SOLID)
		{
			world.setBlockToAir(position);
			return;
		}
		// Get the other door half position
		BlockPos halfPosition = top ? downPosition : position.up();
		// Combine the properties of both door halves into one blockstate
		IBlockState halfState = world.getBlockState(halfPosition).getActualState(world, halfPosition);
		// If the other door half is broken, break this half
		if(halfState.getBlock() != this)
		{
			world.setBlockToAir(position);
			return;
		}
		boolean powered = world.isBlockPowered(position) || world.isBlockPowered(halfPosition);
		// If the neighbor block can now provide power or no longer can and if the door is powered and not open or vice versa, toggle the door and play a sound
		if((block.canProvidePower(world.getBlockState(fromPosition)) != world.getBlockState(fromPosition).canProvidePower()) && (powered != state.getValue(OPEN)))
		{
			// Get the top door half position
			BlockPos topPosition = top ? position : halfPosition;
			// Combine the properties of both door halves into one blockstate
			IBlockState topState = world.getBlockState(topPosition).getActualState(world, topPosition);
			world.setBlockState(topPosition, topState.cycleProperty(OPEN), 2);
			world.playEvent(null, topState.getValue(OPEN) ? 1011 : 1005, position, 0);
		}
		else world.setBlockState(position, halfState.withProperty(HALF, top ? EnumDoorHalf.UPPER : EnumDoorHalf.LOWER), 2);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {HALF, FACING, OPEN, HINGE, REINFORCED});
	}

	/**
	 * Returns the door half's blockstate with the respective properties of its neighbor which aren't stored in the this half.
	 * VERY IMPORTANT: this means that in order to change a property of the door and have the other half update accordingly it is required to change it on the half the property is stored in.
	 * Otherwise the change will be overriden by the other half's value before it can update to this half's value
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position)
	{
		if(state.getValue(HALF) == EnumDoorHalf.LOWER)
		{
			IBlockState offsetState = world.getBlockState(position.up());
			if (offsetState.getBlock() == this) state = state.withProperty(OPEN, offsetState.getValue(OPEN)).withProperty(HINGE, offsetState.getValue(HINGE));
		}
		else
		{
			IBlockState offsetState = world.getBlockState(position.down());
			if (offsetState.getBlock() == this) state = state.withProperty(FACING, offsetState.getValue(FACING));
		}
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;
		if(state.getValue(HALF) == EnumDoorHalf.LOWER) // bits 1 is half, 2 is reinforced and 3-4 are facing
		{
			if(state.getValue(REINFORCED)) meta |= 0b0100;
			meta |= state.getValue(FACING).getHorizontalIndex();
		}
		else // bits 1 is half, 2 reinforced, 3 is open and 4 is hinge
		{
			meta |= 0b1000;
			if(state.getValue(REINFORCED)) meta |= 0b0100;
			if(state.getValue(OPEN)) meta |= 0b0010;
			if(state.getValue(HINGE) == EnumHingePosition.RIGHT) meta |= 0b0001;
		}
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = this.getDefaultState();
		if((meta & 0b1000) == 0)
		{
			state = state.withProperty(HALF, EnumDoorHalf.LOWER);
			state = state.withProperty(REINFORCED, (meta & 0b0100) != 0);
			state = state.withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011));
		}
		else
		{
			state = state.withProperty(HALF, EnumDoorHalf.UPPER);
			state = state.withProperty(REINFORCED, (meta & 0b0100) != 0);
			state = state.withProperty(OPEN, (meta & 0b0010) != 0);
			state = state.withProperty(HINGE, (meta & 0b0001) != 0 ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT);
		}
		return state;
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos position)
	{
		return world.getBlockState(position).getActualState(world, position).getValue(OPEN);
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