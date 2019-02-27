package celestek.hexcraft.common.block;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class HexBlock extends Block
{
	public HexBlock(String name, Material material)
	{
		super(material);
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(HexCreativeTabs.tab);
	}
}