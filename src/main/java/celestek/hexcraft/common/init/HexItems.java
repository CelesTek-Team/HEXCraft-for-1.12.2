package celestek.hexcraft.common.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.block.HexBlock;
import celestek.hexcraft.common.item.HexItem;
import celestek.hexcraft.common.item.HexItemBlock;
import celestek.hexcraft.common.item.ItemHexoriumReinforcer;
import celestek.hexcraft.utility.HexFilters;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains all of HEXCraft's items. Automatically registers a fullbright model with {@link HexItem}'s texture filter. Also registers their {@link HexBlock}'s state mapper and color handler for item blocks
 */
public final class HexItems {
	private static List<HexItem> items = new ArrayList<>();
	private static List<HexItemBlock> itemBlocks = new ArrayList<>();

	public static final Item

	hexorium_crystal_red = add(new HexItem("hexorium_crystal_red", HexCreativeTabs.tabComponents, HexFilters.filterContains("hexorium_crystal_red"))),
	hexorium_crystal_green = add(new HexItem("hexorium_crystal_green", HexCreativeTabs.tabComponents, HexFilters.filterContains("hexorium_crystal_green"))),
	hexorium_crystal_blue = add(new HexItem("hexorium_crystal_blue", HexCreativeTabs.tabComponents, HexFilters.filterContains("hexorium_crystal_blue"))),
	hexorium_crystal_white = add(new HexItem("hexorium_crystal_white", HexCreativeTabs.tabComponents, HexFilters.filterContains("hexorium_crystal_white"))),
	hexorium_crystal_black = add(new HexItem("hexorium_crystal_black", HexCreativeTabs.tabComponents, HexFilters.filterContains("hexorium_crystal_black"))),

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
			// Add a fullbright model override if the item has a texture filter
			if (item.filter.isPresent())
				HexUtilities.addFullbright(item.filter.get(), item.enableCache(), item.getRegistryName());
		}
		for (HexItemBlock item : itemBlocks)
		{
			Optional<HexStateMapper> mapper = ((HexBlock) item.getBlock()).mapper;
			// Register a state mapper if the the item block's block has one
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(mapper.isPresent() ? mapper.get().getPathForItem(item) : item.getRegistryName(), "inventory"));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors(ColorHandlerEvent.Item event)
	{
		ItemColors registry = event.getItemColors();
		IItemColor color = (stack, index) -> ((HexBlock) ((ItemBlock) stack.getItem()).getBlock()).color;
		for (HexItemBlock item : itemBlocks)
			// Register a color handler if the item block's block color is not negative
			if(((HexBlock) item.getBlock()).color >= 0)
				registry.registerItemColorHandler(color, item);
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