package celestek.hexcraft.common.block;

import java.util.ArrayList;
import java.util.List;

import celestek.hexcraft.common.item.HexItemBlock;
import celestek.hexcraft.common.item.HexItems;
import celestek.hexcraft.utility.HexColors;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class HexBlocks {
	private static List<HexBlock> blocks = new ArrayList<>();

	public static final Block

	// Hexorium Crystal Block
	hexorium_crystal_block_red = add(new BlockHexoriumCrystalBlock("hexorium_crystal_block_red", HexColors.GRAY, "crystal/hexorium_crystal_block_top_red", "crystal/hexorium_crystal_block_side_red", "crystal/hexorium_crystal_block_bottom_red")),
	hexorium_crystal_block_green = add(new BlockHexoriumCrystalBlock("hexorium_crystal_block_green", HexColors.GRAY, "crystal/hexorium_crystal_block_top_green", "crystal/hexorium_crystal_block_side_green", "crystal/hexorium_crystal_block_bottom_green")),
	hexorium_crystal_block_blue = add(new BlockHexoriumCrystalBlock("hexorium_crystal_block_blue", HexColors.GRAY, "crystal/hexorium_crystal_block_top_blue", "crystal/hexorium_crystal_block_side_blue", "crystal/hexorium_crystal_block_bottom_blue")),
	hexorium_crystal_block_white = add(new BlockHexoriumCrystalBlock("hexorium_crystal_block_white", HexColors.GRAY, "crystal/hexorium_crystal_block_top_white", "crystal/hexorium_crystal_block_side_white", "crystal/hexorium_crystal_block_bottom_white")),
	hexorium_crystal_block_black = add(new BlockHexoriumCrystalBlock("hexorium_crystal_block_black", HexColors.GRAY, "crystal/hexorium_crystal_block_top_black", "crystal/hexorium_crystal_block_side_black", "crystal/hexorium_crystal_block_bottom_black")),

	// Energized Hexorium
	energized_hexorium_red = add(new BlockEnergizedHexorium("energized_hexorium_red", HexColors.RED, "glow")),
	energized_hexorium_orange = add(new BlockEnergizedHexorium("energized_hexorium_orange", HexColors.ORANGE, "glow")),
	energized_hexorium_yellow = add(new BlockEnergizedHexorium("energized_hexorium_yellow", HexColors.YELLOW, "glow")),
	energized_hexorium_lime = add(new BlockEnergizedHexorium("energized_hexorium_lime", HexColors.LIME, "glow")),
	energized_hexorium_green = add(new BlockEnergizedHexorium("energized_hexorium_green", HexColors.GREEN, "glow")),
	energized_hexorium_turquoise = add(new BlockEnergizedHexorium("energized_hexorium_turquoise", HexColors.TURQUOISE, "glow")),
	energized_hexorium_cyan = add(new BlockEnergizedHexorium("energized_hexorium_cyan", HexColors.CYAN, "glow")),
	energized_hexorium_skyblue = add(new BlockEnergizedHexorium("energized_hexorium_skyblue", HexColors.SKY_BLUE, "glow")),
	energized_hexorium_blue = add(new BlockEnergizedHexorium("energized_hexorium_blue", HexColors.BLUE, "glow")),
	energized_hexorium_purple = add(new BlockEnergizedHexorium("energized_hexorium_purple", HexColors.PURPLE, "glow")),
	energized_hexorium_magenta = add(new BlockEnergizedHexorium("energized_hexorium_magenta", HexColors.MAGENTA, "glow")),
	energized_hexorium_pink = add(new BlockEnergizedHexorium("energized_hexorium_pink", HexColors.PINK, "glow")),
	energized_hexorium_white = add(new BlockEnergizedHexorium("energized_hexorium_white", HexColors.WHITE, "glow")),
	energized_hexorium_lightgray = add(new BlockEnergizedHexorium("energized_hexorium_lightgray", HexColors.LIGHT_GRAY, "glow")),
	energized_hexorium_gray = add(new BlockEnergizedHexorium("energized_hexorium_gray", HexColors.GRAY, "glow")),
	energized_hexorium_darkgray = add(new BlockEnergizedHexorium("energized_hexorium_darkgray", HexColors.DARK_GRAY, "glow")),
	energized_hexorium_black = add(new BlockEnergizedHexorium("energized_hexorium_black", HexColors.BLACK, "glow")),
	energized_hexorium_rainbow = add(new BlockEnergizedHexorium("energized_hexorium_rainbow", HexColors.RAINBOW, "glow_rainbow")),

	// Engineered Hexorium Block
	engineered_hexorium_block_red = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_red", HexColors.RED, "glow")),
	engineered_hexorium_block_orange = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_orange", HexColors.ORANGE, "glow")),
	engineered_hexorium_block_yellow = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_yellow", HexColors.YELLOW, "glow")),
	engineered_hexorium_block_lime = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_lime", HexColors.LIME, "glow")),
	engineered_hexorium_block_green = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_green", HexColors.GREEN, "glow")),
	engineered_hexorium_block_turquoise = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_turquoise", HexColors.TURQUOISE, "glow")),
	engineered_hexorium_block_cyan = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_cyan", HexColors.CYAN, "glow")),
	engineered_hexorium_block_skyblue = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_skyblue", HexColors.SKY_BLUE, "glow")),
	engineered_hexorium_block_blue = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_blue", HexColors.BLUE, "glow")),
	engineered_hexorium_block_purple = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_purple", HexColors.PURPLE, "glow")),
	engineered_hexorium_block_magenta = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_magenta", HexColors.MAGENTA, "glow")),
	engineered_hexorium_block_pink = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_pink", HexColors.PINK, "glow")),
	engineered_hexorium_block_white = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_white", HexColors.WHITE, "glow")),
	engineered_hexorium_block_lightgray = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_lightgray", HexColors.LIGHT_GRAY, "glow")),
	engineered_hexorium_block_gray = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_gray", HexColors.GRAY, "glow")),
	engineered_hexorium_block_darkgray = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_darkgray", HexColors.DARK_GRAY, "glow")),
	engineered_hexorium_block_black = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_black", HexColors.BLACK, "glow")),
	engineered_hexorium_block_rainbow = add(new BlockEngineeredHexoriumBlock("engineered_hexorium_block_rainbow", HexColors.RAINBOW, "glow_rainbow"));

	private HexBlocks() {}

	public static void register(RegistryEvent.Register<Block> event) {
		IForgeRegistry registry = event.getRegistry();
		for(HexBlock block : blocks)
			registry.register(block);
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors(ColorHandlerEvent.Block event) {
		BlockColors registry = event.getBlockColors();
		IBlockColor color = (state, world, position, tint) -> ((HexBlock) state.getBlock()).color;
		for (HexBlock block : blocks)
			if(block.color >= 0)
				registry.registerBlockColorHandler(color, block);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels() {
		for(HexBlock block : blocks)
			if(!block.textures.isEmpty())
				HexUtilities.addFullbright(block.textures, block);
	}

	public static HexBlock add(HexBlock block)
	{
		blocks.add(block);
		HexItems.add(new HexItemBlock(block));
		return block;
	}
}