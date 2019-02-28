package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.block.HexBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HexCreativeTab extends CreativeTabs {
	public HexCreativeTab() {
		super(HexCraft.ID);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(HexBlocks.energized_hexorium_rainbow));
	}
}