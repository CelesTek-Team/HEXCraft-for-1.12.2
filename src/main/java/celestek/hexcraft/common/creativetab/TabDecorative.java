package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.common.init.HexBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabDecorative extends HexCreativeTab
{
	public TabDecorative()
	{
		super("decorative");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(Item.getItemFromBlock(HexBlocks.energized_hexorium_rainbow));
	}
}