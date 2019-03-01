package celestek.hexcraft.common.creativetab;

import celestek.hexcraft.common.item.HexItems;
import net.minecraft.item.ItemStack;

public class TabComponents extends HexCreativeTab {

	public TabComponents() {
		super("components");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(HexItems.hexorium_crystal_red);
	}
}