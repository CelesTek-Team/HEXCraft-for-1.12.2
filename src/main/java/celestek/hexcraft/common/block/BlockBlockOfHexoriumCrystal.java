package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import celestek.hexcraft.utility.HexFilters;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBlockOfHexoriumCrystal extends HexBlockPillar
{
	public BlockBlockOfHexoriumCrystal(EHexColors color)
	{
		super("block_of_hexorium_crystal_" + color.name, Optional.empty(), HexCreativeTabs.tabDecorative, Material.GLASS, EHexColors.DIMMED.color, HexFilters.ALWAYS_TRUE);
		this.setHardness(5F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public boolean enableCache()
	{
		return true;
	}
}