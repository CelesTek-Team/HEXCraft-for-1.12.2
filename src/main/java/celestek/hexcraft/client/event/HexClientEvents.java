package celestek.hexcraft.client.event;

import java.util.HashMap;
import java.util.function.Function;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.block.HexBlocks;
import celestek.hexcraft.common.item.HexItems;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ColorHandlerEvent;
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
	public static void registerBlockColors(ColorHandlerEvent.Block event)
	{
		HexBlocks.registerColors(event);
	}

	@SubscribeEvent
	public static void registerItemColors(ColorHandlerEvent.Item event)
	{
		HexItems.registerColors(event);
	}

	private static HashMap<ResourceLocation, Function<IBakedModel, IBakedModel>> bakedModelOverrides = new HashMap<>(); // Move somewhere else?

	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event)
	{
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		for (ModelResourceLocation resource : registry.getKeys())
		{
			ResourceLocation key = new ResourceLocation(resource.getResourceDomain(), resource.getResourcePath());
			if(bakedModelOverrides.containsKey(key)) registry.putObject(resource, bakedModelOverrides.get(key).apply(registry.getObject(resource)));
		}
	}

	public static void addBakedModelOverride(ResourceLocation resource, Function<IBakedModel, IBakedModel> override)
	{
		bakedModelOverrides.put(resource, override);
	}
}