package celestek.hexcraft.common.item;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.item.Item;

public class HexItem extends Item {
	private String model;

	public HexItem(String name, String model) {
		this.model = model;
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(HexCreativeTabs.tab);
		HexItems.addItem(this);
	}

	public String getModel() {
		return this.model;
	}
}