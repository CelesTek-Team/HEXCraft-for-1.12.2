package celestek.hexcraft.common.block;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockHexoriumCrystalBlock extends HexBlockPillar
{
	public BlockHexoriumCrystalBlock(String name, int color, String... textures)
	{
		super(name, HexCreativeTabs.tabDecorative, Material.GLASS, color, textures);
		this.setSoundType(SoundType.GLASS);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(5F);
		this.setResistance(10F);
	}
}