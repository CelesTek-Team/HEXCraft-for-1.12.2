package celestek.hexcraft.common.block;

import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A block that can rotate in all 6 directions
 */
public class HexBlockPillar extends HexBlock
{
	public static final PropertyDirection FACING = BlockDirectional.FACING;

	public HexBlockPillar(String name, CreativeTabs tab, Material material, EHexColor color)
	{
		super(name, tab, material, color);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos position, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase entity, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		// bits 2, 3 and 4 are facing
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 0b0111));
	}
}