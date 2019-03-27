package celestek.hexcraft.common.block;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHexoriumCoatedStone extends HexBlockReinforceable
{
	public BlockHexoriumCoatedStone()
	{
		super("hexorium_coated_stone", HexCreativeTabs.tabDecorative, Material.IRON, EHexColor.NONE);
		this.setHardness(1.5F);
		this.setResistance(6F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.STONE);
	}
}