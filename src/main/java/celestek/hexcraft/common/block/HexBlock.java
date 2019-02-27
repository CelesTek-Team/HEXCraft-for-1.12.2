package celestek.hexcraft.common.block;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import celestek.hexcraft.common.item.HexItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class HexBlock extends Block {
	private String model;

	public HexBlock(String name, Material material, String model) {
		super(material);
		this.model = model;
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(HexCreativeTabs.tab);
		HexBlocks.addBlock(this);
	}

	public String getModel() {
		return this.model;
	}
}