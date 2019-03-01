package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.block.HexBlocks;
import celestek.hexcraft.common.item.HexItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HexCreativeTab extends CreativeTabs {

	public HexCreativeTab(String name) {
		super(HexCraft.ID + "." + name);
	}

	@Override
	public ItemStack getTabIconItem() {
		return null;
	}
}