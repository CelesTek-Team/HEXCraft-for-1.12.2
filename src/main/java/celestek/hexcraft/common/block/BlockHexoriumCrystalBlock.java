package celestek.hexcraft.common.block;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockHexoriumCrystalBlock extends HexBlockPillar
{
	public BlockHexoriumCrystalBlock(String color, String... textures)
	{
		super("hexorium_crystal_block_" + color, HexCreativeTabs.tabDecorative, Material.GLASS, EHexColors.DIMMED.color,
				"hexorium_crystal_block/hexorium_crystal_block_top_" + color, "hexorium_crystal_block/hexorium_crystal_block_side_" + color, "hexorium_crystal_block/hexorium_crystal_block_bottom_" + color);
		this.setSoundType(SoundType.GLASS);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(5F);
		this.setResistance(10F);
	}
}