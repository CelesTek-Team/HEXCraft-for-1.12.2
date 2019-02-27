package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.item.HexItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HexCreativeTab extends CreativeTabs
{
	public HexCreativeTab()
	{
		super(HexCraft.ID);
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(HexItems.energized_hexorium_rainbow);
	}
}