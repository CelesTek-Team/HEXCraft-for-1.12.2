package celestek.hexcraft.common.item;

import java.util.ArrayList;
import java.util.List;

import celestek.hexcraft.common.block.HexBlock;
import celestek.hexcraft.common.creativetab.HexCreativeTabs;
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

public class HexItems {
	private static List<HexItem> items = new ArrayList<>();
	private static List<HexItemBlock> itemBlocks = new ArrayList<>();

	public static final Item

	hexorium_crystal_red = add(new HexItem("hexorium_crystal_red", HexCreativeTabs.tabComponents, "hexorium_crystal_red")),
	hexorium_crystal_green = add(new HexItem("hexorium_crystal_green", HexCreativeTabs.tabComponents, "hexorium_crystal_green")),
	hexorium_crystal_blue = add(new HexItem("hexorium_crystal_blue", HexCreativeTabs.tabComponents, "hexorium_crystal_blue")),
	hexorium_crystal_white = add(new HexItem("hexorium_crystal_white", HexCreativeTabs.tabComponents, "hexorium_crystal_white")),
	hexorium_crystal_black = add(new HexItem("hexorium_crystal_black", HexCreativeTabs.tabComponents, "hexorium_crystal_black")),

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
			if (!item.textures.isEmpty())
				HexUtilities.addFullbright(item.textures, item.enableCache(), item);
		}
		for (HexItemBlock item : itemBlocks)
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors(ColorHandlerEvent.Item event)
	{
		ItemColors registry = event.getItemColors();
		IItemColor color = (stack, index) -> ((HexBlock) ((ItemBlock) stack.getItem()).getBlock()).color;
		for (HexItemBlock item : itemBlocks)
			if(((HexBlock) item.getBlock()).color >= 0)
				registry.registerItemColorHandler(color, item);
	}

	public static HexItem add(HexItem item) {
		items.add(item);
		return item;
	}

	public static HexItemBlock add(HexItemBlock item) {
		itemBlocks.add(item);
		return item;
	}
}