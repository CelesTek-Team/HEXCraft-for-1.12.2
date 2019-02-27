package celestek.hexcraft.common.item;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.item.Item;

public class HexItem extends Item
{
	public HexItem(String name)
	{
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(HexCreativeTabs.tab);
	}
}