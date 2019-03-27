package celestek.hexcraft.common.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.client.event.HexClientEvents;
import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.block.HexBlock;
import celestek.hexcraft.common.item.HexItem;
import celestek.hexcraft.common.item.HexItemBlock;
import celestek.hexcraft.common.item.ItemHexoriumCrystal;
import celestek.hexcraft.common.item.ItemHexoriumReinforcer;
import celestek.hexcraft.utility.EHexColor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains all of HEXCraft's items. Automatically registers {@link HexItem}'s custom model override. Also registers their {@link HexBlock}'s state mapper and color handler for item blocks
 */
public final class HexItems {
	private static List<HexItem> items = new ArrayList<>();
	private static List<HexItemBlock> itemBlocks = new ArrayList<>();

	public static final Item

	hexorium_crystal_red = add(new ItemHexoriumCrystal(EHexColor.RED)),
	hexorium_crystal_green = add(new ItemHexoriumCrystal(EHexColor.GREEN)),
	hexorium_crystal_blue = add(new ItemHexoriumCrystal(EHexColor.BLUE)),
	hexorium_crystal_white = add(new ItemHexoriumCrystal(EHexColor.WHITE)),
	hexorium_crystal_black = add(new ItemHexoriumCrystal(EHexColor.BLACK)),

	hexorium_reinforcer = add(new ItemHexoriumReinforcer());

	private HexItems() {}

	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry registry = event.getRegistry();
		for (HexItem item : items)
			registry.register(item);
		for (HexItemBlock item : itemBlocks)
			registry.register(item);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		for (HexItem item : items) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			// Add a custom model override if the item provides one
			Optional<Function<IBakedModel, IBakedModel>> override = item.addModelOverride();
			if (override.isPresent())
				HexClientEvents.addModelOverride(item.getRegistryName(), override.get());
		}
		for (HexItemBlock item : itemBlocks)
		{
			Optional<HexStateMapper> mapper = ((HexBlock) item.getBlock()).addStateMapper();
			// Register a state mapper if the the item's block has one
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(mapper.isPresent() ? mapper.get().getPathForItem(item) : item.getRegistryName(), "inventory"));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors(ColorHandlerEvent.Item event)
	{
		ItemColors registry = event.getItemColors();
		for (HexItemBlock item : itemBlocks)
		{
			// Register a color handler if the item's block has one
			Optional<IItemColor> color = ((HexBlock) item.getBlock()).addItemColor();
			if(color.isPresent())
				registry.registerItemColorHandler(color.get(), item);
		}
	}

	/**
	 * Add the given item for automatic registry
	 */
	public static HexItem add(HexItem item) {
		items.add(item);
		return item;
	}

	/**
	 * Add the given item block for automatic registry
	 */
	public static HexItemBlock add(HexItemBlock item) {
		itemBlocks.add(item);
		return item;
	}
}