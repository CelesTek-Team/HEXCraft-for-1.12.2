package celestek.hexcraft.client.event;

import java.util.HashMap;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.client.model.BakedModelBrightness;
import celestek.hexcraft.common.init.HexBlocks;
import celestek.hexcraft.common.init.HexItems;
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
public final class HexClientEvents
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

	/**
	 * A map of model paths and their respective model wrapper functions.
	 * During the model bake event all of the models which correspond the the contained paths are replaced with the ones generated by the mapped function.
	 */
	private static HashMap<ResourceLocation, Function<IBakedModel, IBakedModel>> overrides = new HashMap<>();

	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event)
	{
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		for (ModelResourceLocation resource : registry.getKeys())
		{
			ResourceLocation key = new ResourceLocation(resource.getResourceDomain(), resource.getResourcePath());
			// For every supplied model path we wrap its existing model via the corresponding function
			if(overrides.containsKey(key)) registry.putObject(resource, overrides.get(key).apply(registry.getObject(resource)));
		}
	}

	public static void addModelOverride(ResourceLocation resource, Function<IBakedModel, IBakedModel> override)
	{
		overrides.put(resource, override);
	}
}