package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import celestek.hexcraft.utility.HexShapes;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnergizedHexoriumMonolith extends HexBlockPillar
{
	protected static final AxisAlignedBB
	BOUNDS_DOWN = new AxisAlignedBB(HexShapes.Monolith.xA, 1d - HexShapes.Monolith.yMax, HexShapes.Monolith.zF, HexShapes.Monolith.xD, 1d - HexShapes.Monolith.yMin, HexShapes.Monolith.zB),
	BOUNDS_UP = new AxisAlignedBB(HexShapes.Monolith.xA, HexShapes.Monolith.yMin, HexShapes.Monolith.zF, HexShapes.Monolith.xD, HexShapes.Monolith.yMax, HexShapes.Monolith.zB),
	BOUNDS_NORTH = new AxisAlignedBB(HexShapes.Monolith.zF, HexShapes.Monolith.xA, 1d - HexShapes.Monolith.yMax, HexShapes.Monolith.zB, HexShapes.Monolith.xD, 1d - HexShapes.Monolith.yMin),
	BOUNDS_SOUTH = new AxisAlignedBB(HexShapes.Monolith.zF, HexShapes.Monolith.xA, HexShapes.Monolith.yMin, HexShapes.Monolith.zB, HexShapes.Monolith.xD, HexShapes.Monolith.yMax),
	BOUNDS_WEST = new AxisAlignedBB(1d - HexShapes.Monolith.yMax, HexShapes.Monolith.xA, HexShapes.Monolith.zF, 1d - HexShapes.Monolith.yMin, HexShapes.Monolith.xD, HexShapes.Monolith.zB),
	BOUNDS_EAST = new AxisAlignedBB(HexShapes.Monolith.yMin, HexShapes.Monolith.xA, HexShapes.Monolith.zF, HexShapes.Monolith.yMax, HexShapes.Monolith.xD, HexShapes.Monolith.zB);

	public BlockEnergizedHexoriumMonolith(EHexColors color)
	{
		super("energized_hexorium_monolith_" + color.name, color == EHexColors.RAINBOW ? Optional.empty() : Optional.of(new HexStateMapper("energized_hexorium_monolith")), HexCreativeTabs.tabDecorative, Material.GLASS, color.color, color == EHexColors.RAINBOW ? "energized_hexorium_monolith_rainbow" : "energized_hexorium_monolith");
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
		this.setLightOpacity(0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch (state.getValue(FACING))
		{
		case DOWN: return BOUNDS_DOWN;
		case UP: return BOUNDS_UP;
		case NORTH: return BOUNDS_NORTH;
		case SOUTH: return BOUNDS_SOUTH;
		case WEST: return BOUNDS_WEST;
		case EAST: return BOUNDS_EAST;
		default: return FULL_BLOCK_AABB;
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos position, EnumFacing side)
	{
		return canAttach(world, position.offset(side.getOpposite()), side);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos position)
	{
		for (EnumFacing side : EnumFacing.values()) if(canAttach(world, position, side)) return true;
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos position, Block block, BlockPos neighbor)
	{
		EnumFacing side = state.getValue(FACING);
		if(!canAttach(world, position.offset(side.getOpposite()), side)) world.destroyBlock(position, true);
	}

	public static boolean canAttach(World world, BlockPos position, EnumFacing side)
	{
		IBlockState state = world.getBlockState(position);
		return state.getBlockFaceShape(world, position, side) == BlockFaceShape.SOLID; // Seems to be better than IBlockState#isSideSolid
	}

	@Override
	public boolean canSilkHarvest()
	{
		return false;
	}

	// FIXME manipulator compat
	// Store the EHexColor instead?
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos position, IBlockState state, int fortune)
	{
		EHexColors.fromColor(this.color).addDrops(drops);
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}