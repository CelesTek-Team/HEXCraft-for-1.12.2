package celestek.hexcraft.common.block;

import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HexBlockLamp extends HexBlock
{
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public HexBlockLamp(String name, CreativeTabs tab, Material material, EHexColor color)
	{
		super(name, tab, material, color);
		this.setDefaultState(this.getDefaultState().withProperty(POWERED, false));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos position, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(POWERED, world.isBlockPowered(position));
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos position, Block block, BlockPos fromPos)
	{
		if(!world.isRemote && (state.getValue(POWERED) != world.isBlockPowered(position))) world.setBlockState(position, state.cycleProperty(POWERED), 2);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos position)
	{
		return state.getValue(POWERED) ? 15 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {POWERED});
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		// bit 1 is reinforced
		return state.getValue(POWERED) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(POWERED, meta == 1);
	}
}