package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.HexCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
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
		return new ItemStack(Items.APPLE); // FIXME
	}
}