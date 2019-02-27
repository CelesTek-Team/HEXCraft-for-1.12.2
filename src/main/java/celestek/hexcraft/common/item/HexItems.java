package celestek.hexcraft.common.item;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.block.HexBlockColored;
import celestek.hexcraft.common.block.HexBlocks;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HexItems
{
	public static final Item
	hexorium_crystal_red = new HexItem("hexorium_crystal_red"),
	hexorium_crystal_green = new HexItem("hexorium_crystal_green"),
	hexorium_crystal_blue = new HexItem("hexorium_crystal_blue"),
	hexorium_crystal_white = new HexItem("hexorium_crystal_white"),
	hexorium_crystal_black = new HexItem("hexorium_crystal_black"),

	// Blocks
	energized_hexorium_red = new HexItemBlock(HexBlocks.energized_hexorium_red),
	energized_hexorium_orange = new HexItemBlock(HexBlocks.energized_hexorium_orange),
	energized_hexorium_yellow = new HexItemBlock(HexBlocks.energized_hexorium_yellow),
	energized_hexorium_lime = new HexItemBlock(HexBlocks.energized_hexorium_lime),
	energized_hexorium_green = new HexItemBlock(HexBlocks.energized_hexorium_green),
	energized_hexorium_turquoise = new HexItemBlock(HexBlocks.energized_hexorium_turquoise),
	energized_hexorium_cyan = new HexItemBlock(HexBlocks.energized_hexorium_cyan),
	energized_hexorium_skyblue = new HexItemBlock(HexBlocks.energized_hexorium_skyblue),
	energized_hexorium_blue = new HexItemBlock(HexBlocks.energized_hexorium_blue),
	energized_hexorium_purple = new HexItemBlock(HexBlocks.energized_hexorium_purple),
	energized_hexorium_magenta = new HexItemBlock(HexBlocks.energized_hexorium_magenta),
	energized_hexorium_pink = new HexItemBlock(HexBlocks.energized_hexorium_pink),
	energized_hexorium_white = new HexItemBlock(HexBlocks.energized_hexorium_white),
	energized_hexorium_lightgray = new HexItemBlock(HexBlocks.energized_hexorium_lightgray),
	energized_hexorium_gray = new HexItemBlock(HexBlocks.energized_hexorium_gray),
	energized_hexorium_darkgray = new HexItemBlock(HexBlocks.energized_hexorium_darkgray),
	energized_hexorium_black = new HexItemBlock(HexBlocks.energized_hexorium_black),
	energized_hexorium_rainbow = new HexItemBlock(HexBlocks.energized_hexorium_rainbow);

	private HexItems() {}

	public static void register(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				hexorium_crystal_red,
				hexorium_crystal_green,
				hexorium_crystal_blue,
				hexorium_crystal_white,
				hexorium_crystal_black,

				energized_hexorium_red,
				energized_hexorium_orange,
				energized_hexorium_yellow,
				energized_hexorium_lime,
				energized_hexorium_green,
				energized_hexorium_turquoise,
				energized_hexorium_cyan,
				energized_hexorium_skyblue,
				energized_hexorium_blue,
				energized_hexorium_purple,
				energized_hexorium_magenta,
				energized_hexorium_pink,
				energized_hexorium_white,
				energized_hexorium_lightgray,
				energized_hexorium_gray,
				energized_hexorium_darkgray,
				energized_hexorium_black,
				energized_hexorium_rainbow);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModels(
				hexorium_crystal_red,
				hexorium_crystal_green,
				hexorium_crystal_blue,
				hexorium_crystal_white,
				hexorium_crystal_black,

				energized_hexorium_red,
				energized_hexorium_orange,
				energized_hexorium_yellow,
				energized_hexorium_lime,
				energized_hexorium_green,
				energized_hexorium_turquoise,
				energized_hexorium_cyan,
				energized_hexorium_skyblue,
				energized_hexorium_blue,
				energized_hexorium_purple,
				energized_hexorium_magenta,
				energized_hexorium_pink,
				energized_hexorium_white,
				energized_hexorium_lightgray,
				energized_hexorium_gray,
				energized_hexorium_darkgray,
				energized_hexorium_black,
				energized_hexorium_rainbow);

		HexUtilities.addFullbright(HexCraft.ID + ":" + "items/hexorium_crystal_red", hexorium_crystal_red);
		HexUtilities.addFullbright(HexCraft.ID + ":" + "items/hexorium_crystal_green", hexorium_crystal_green);
		HexUtilities.addFullbright(HexCraft.ID + ":" + "items/hexorium_crystal_blue", hexorium_crystal_blue);
		HexUtilities.addFullbright(HexCraft.ID + ":" + "items/hexorium_crystal_white", hexorium_crystal_white);
		HexUtilities.addFullbright(HexCraft.ID + ":" + "items/hexorium_crystal_black", hexorium_crystal_black);
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors(ColorHandlerEvent.Item event)
	{
		event.getItemColors().registerItemColorHandler((stack, index) -> ((HexBlockColored) ((ItemBlock) stack.getItem()).getBlock()).color,
				energized_hexorium_red,
				energized_hexorium_orange,
				energized_hexorium_yellow,
				energized_hexorium_lime,
				energized_hexorium_green,
				energized_hexorium_turquoise,
				energized_hexorium_cyan,
				energized_hexorium_skyblue,
				energized_hexorium_blue,
				energized_hexorium_purple,
				energized_hexorium_magenta,
				energized_hexorium_pink,
				energized_hexorium_white,
				energized_hexorium_lightgray,
				energized_hexorium_gray,
				energized_hexorium_darkgray,
				energized_hexorium_black);
	}

	@SideOnly(Side.CLIENT)
	private static void registerModels(Item... items)
	{
		for(Item item : items) ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}