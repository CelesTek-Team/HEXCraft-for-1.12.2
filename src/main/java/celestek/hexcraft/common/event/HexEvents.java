package celestek.hexcraft.common.event;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.init.HexBlocks;
import celestek.hexcraft.common.init.HexItems;
import celestek.hexcraft.common.init.HexOreDict;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HexCraft.ID)
public class HexEvents
{
	private HexEvents() {}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		HexBlocks.register(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		HexItems.register(event);
		HexOreDict.register();
	}
}