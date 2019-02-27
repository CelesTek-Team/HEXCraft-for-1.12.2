package celestek.hexcraft.common.item;

import celestek.hexcraft.common.block.HexBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HexItems
{
	public static final Item
	energized_hexorium_red = new HexItemBlock(HexBlocks.energized_hexorium_red);

	private HexItems() {}

	public static void register(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(energized_hexorium_red);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModels(energized_hexorium_red);
	}

	@SideOnly(Side.CLIENT)
	private static void registerModels(Item... items)
	{
		for(Item item : items) ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}