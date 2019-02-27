package celestek.hexcraft.common.block;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.utility.HexColors;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.Block;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HexBlocks
{
	public static final Block
	energized_hexorium_red = new BlockEnergizedHexorium("energized_hexorium_red", HexColors.RED),
	energized_hexorium_orange = new BlockEnergizedHexorium("energized_hexorium_orange", HexColors.ORANGE),
	energized_hexorium_yellow = new BlockEnergizedHexorium("energized_hexorium_yellow", HexColors.YELLOW),
	energized_hexorium_lime = new BlockEnergizedHexorium("energized_hexorium_lime", HexColors.LIME),
	energized_hexorium_green = new BlockEnergizedHexorium("energized_hexorium_green", HexColors.GREEN),
	energized_hexorium_turquoise = new BlockEnergizedHexorium("energized_hexorium_turquoise", HexColors.TURQUOISE),
	energized_hexorium_cyan = new BlockEnergizedHexorium("energized_hexorium_cyan", HexColors.CYAN),
	energized_hexorium_skyblue = new BlockEnergizedHexorium("energized_hexorium_skyblue", HexColors.SKY_BLUE),
	energized_hexorium_blue = new BlockEnergizedHexorium("energized_hexorium_blue", HexColors.BLUE),
	energized_hexorium_purple = new BlockEnergizedHexorium("energized_hexorium_purple", HexColors.PURPLE),
	energized_hexorium_magenta = new BlockEnergizedHexorium("energized_hexorium_magenta", HexColors.MAGENTA),
	energized_hexorium_pink = new BlockEnergizedHexorium("energized_hexorium_pink", HexColors.PINK),
	energized_hexorium_white = new BlockEnergizedHexorium("energized_hexorium_white", HexColors.WHITE),
	energized_hexorium_lightgray = new BlockEnergizedHexorium("energized_hexorium_lightgray", HexColors.LIGHT_GRAY),
	energized_hexorium_gray = new BlockEnergizedHexorium("energized_hexorium_gray", HexColors.GRAY),
	energized_hexorium_darkgray = new BlockEnergizedHexorium("energized_hexorium_darkgray", HexColors.DARK_GRAY),
	energized_hexorium_black = new BlockEnergizedHexorium("energized_hexorium_black", HexColors.BLACK),
	energized_hexorium_rainbow = new BlockEnergizedHexorium("energized_hexorium_rainbow", HexColors.RAINBOW);

	private HexBlocks() {}

	public static void register(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
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
	public static void registerColors(ColorHandlerEvent.Block event)
	{
		event.getBlockColors().registerBlockColorHandler((state, access, position, index) -> ((HexBlockColored) state.getBlock()).color,
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
	public static void registerModels()
	{
		HexUtilities.addFullbright(HexCraft.ID + ":" + "blocks/glow",
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
		HexUtilities.addFullbright(HexCraft.ID + ":" + "blocks/glow_rainbow",
				energized_hexorium_rainbow);
	}
}