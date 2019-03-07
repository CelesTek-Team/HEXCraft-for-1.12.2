package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.common.init.HexItems;
import net.minecraft.item.ItemStack;

public class TabComponents extends AHexCreativeTab
{
	public TabComponents()
	{
		super("components");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(HexItems.hexorium_crystal_red);
	}
}