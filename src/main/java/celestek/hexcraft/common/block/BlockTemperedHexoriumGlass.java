package celestek.hexcraft.common.block;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTemperedHexoriumGlass extends HexBlockConnected
{
	public BlockTemperedHexoriumGlass()
	{
		super("tempered_hexorium_glass", HexCreativeTabs.decorative, Material.GLASS, EHexColor.NONE);
		this.setHardness(1.5f);
		this.setResistance(10f);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.GLASS);
		this.setLightOpacity(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos position, EnumFacing side)
	{
		return world.getBlockState(position.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(state, world, position, side);
	}
}