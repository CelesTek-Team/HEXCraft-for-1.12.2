package celestek.hexcraft.common.item;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class HexItemBlock extends ItemBlock {
	public HexItemBlock(Block block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		HexItems.addItemBlock(this);
	}
}