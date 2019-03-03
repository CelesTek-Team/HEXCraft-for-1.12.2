package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.init.HexBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class HexCreativeTab extends CreativeTabs {
	public HexCreativeTab(String name) {
		super(HexCraft.ID + "." + name);
	}
}