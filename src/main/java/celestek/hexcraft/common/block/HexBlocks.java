package celestek.hexcraft.common.block;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.utility.HexColors;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.Block;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class HexBlocks {
	private static List<HexBlock> hexBlocks = new ArrayList<>();

	public static final Block
	energized_hexorium_red = new BlockEnergizedHexorium("energized_hexorium_red", HexColors.RED, "glow"),
	energized_hexorium_orange = new BlockEnergizedHexorium("energized_hexorium_orange", HexColors.ORANGE, "glow"),
	energized_hexorium_yellow = new BlockEnergizedHexorium("energized_hexorium_yellow", HexColors.YELLOW, "glow"),
	energized_hexorium_lime = new BlockEnergizedHexorium("energized_hexorium_lime", HexColors.LIME, "glow"),
	energized_hexorium_green = new BlockEnergizedHexorium("energized_hexorium_green", HexColors.GREEN, "glow"),
	energized_hexorium_turquoise = new BlockEnergizedHexorium("energized_hexorium_turquoise", HexColors.TURQUOISE, "glow"),
	energized_hexorium_cyan = new BlockEnergizedHexorium("energized_hexorium_cyan", HexColors.CYAN, "glow"),
	energized_hexorium_skyblue = new BlockEnergizedHexorium("energized_hexorium_skyblue", HexColors.SKY_BLUE, "glow"),
	energized_hexorium_blue = new BlockEnergizedHexorium("energized_hexorium_blue", HexColors.BLUE, "glow"),
	energized_hexorium_purple = new BlockEnergizedHexorium("energized_hexorium_purple", HexColors.PURPLE, "glow"),
	energized_hexorium_magenta = new BlockEnergizedHexorium("energized_hexorium_magenta", HexColors.MAGENTA, "glow"),
	energized_hexorium_pink = new BlockEnergizedHexorium("energized_hexorium_pink", HexColors.PINK, "glow"),
	energized_hexorium_white = new BlockEnergizedHexorium("energized_hexorium_white", HexColors.WHITE, "glow"),
	energized_hexorium_lightgray = new BlockEnergizedHexorium("energized_hexorium_lightgray", HexColors.LIGHT_GRAY, "glow"),
	energized_hexorium_gray = new BlockEnergizedHexorium("energized_hexorium_gray", HexColors.GRAY, "glow"),
	energized_hexorium_darkgray = new BlockEnergizedHexorium("energized_hexorium_darkgray", HexColors.DARK_GRAY, "glow"),
	energized_hexorium_black = new BlockEnergizedHexorium("energized_hexorium_black", HexColors.BLACK, "glow"),
	energized_hexorium_rainbow = new BlockEnergizedHexorium("energized_hexorium_rainbow", HexColors.RAINBOW, "glow_rainbow");

	private HexBlocks() {}

	public static void register(RegistryEvent.Register<Block> event) {
		for (HexBlock block : hexBlocks)
			event.getRegistry().register(block);
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors(ColorHandlerEvent.Block event) {
		for (HexBlock block : hexBlocks)
			event.getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> ((HexBlockColored) state.getBlock()).color, block);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()	{
		for (HexBlock block : hexBlocks)
			if (!block.getModel().equals(""))
				HexUtilities.addFullbright(HexCraft.ID + ":blocks/" + block.getModel(), block);
	}

	public static void addBlock(HexBlock block) {
		HexBlocks.hexBlocks.add(block);
	}
}