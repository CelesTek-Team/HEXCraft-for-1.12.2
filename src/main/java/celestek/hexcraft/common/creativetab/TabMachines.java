package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.common.init.HexItems;
import net.minecraft.item.ItemStack;

public class TabMachines extends AHexCreativeTab
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