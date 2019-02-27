package celestek.hexcraft.client.event;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.block.HexBlocks;
import celestek.hexcraft.common.item.HexItems;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = HexCraft.ID, value = Side.CLIENT)
public class HexClientEvents
{
	private HexClientEvents() {}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		HexBlocks.registerModels();
		HexItems.registerModels();
	}

	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event)
	{
		
	}
}