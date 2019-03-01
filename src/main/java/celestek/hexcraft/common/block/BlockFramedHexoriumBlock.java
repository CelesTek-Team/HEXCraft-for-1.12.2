package celestek.hexcraft.common.block;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFramedHexoriumBlock extends HexBlockReinforceable
{
	public BlockFramedHexoriumBlock(EHexColors color)
	{
		super("framed_hexorium_block_" + color.name, HexCreativeTabs.tabDecorative, Material.IRON, color.color, color.texture);
		this.setHardness(1.5F);
		this.setHarvestLevel("pickaxe", 2);
		this.setResistance(6F);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}