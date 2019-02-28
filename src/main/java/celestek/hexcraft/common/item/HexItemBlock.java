package celestek.hexcraft.common.item;

import celestek.hexcraft.common.block.HexBlock;
import net.minecraft.item.ItemBlock;

public class HexItemBlock extends ItemBlock
{
	public HexItemBlock(HexBlock block)
	{
		super(block);
		this.setRegistryName(block.getRegistryName());
	}
}