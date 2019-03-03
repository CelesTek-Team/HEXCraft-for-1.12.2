package celestek.hexcraft.common.block;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBlockOfHexoriumCrystal extends HexBlockPillar
{
	public BlockBlockOfHexoriumCrystal(String color)
	{
		super("block_of_hexorium_crystal_" + color, HexCreativeTabs.tabDecorative, Material.GLASS, EHexColors.DIMMED.color, "block_of_hexorium_crystal/block_of_hexorium_crystal_top_" + color, "block_of_hexorium_crystal/block_of_hexorium_crystal_side_" + color, "block_of_hexorium_crystal/block_of_hexorium_crystal_bottom_" + color);
		this.setSoundType(SoundType.GLASS);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(5F);
		this.setResistance(10F);
	}
}