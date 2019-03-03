package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.common.item.HexItems;
import net.minecraft.item.ItemStack;

public class TabMachines extends HexCreativeTab
{
	public TabMachines()
	{
		super("machines");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(HexItems.hexorium_reinforcer);
	}
}