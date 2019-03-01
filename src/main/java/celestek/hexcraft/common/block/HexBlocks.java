package celestek.hexcraft.common.block;

import java.util.ArrayList;
import java.util.List;

import celestek.hexcraft.common.item.HexItemBlock;
import celestek.hexcraft.common.item.HexItems;
import celestek.hexcraft.utility.EHexColors;
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
	hexorium_crystal_block_red = add(new BlockHexoriumCrystalBlock("red")),
	hexorium_crystal_block_green = add(new BlockHexoriumCrystalBlock("green")),
	hexorium_crystal_block_blue = add(new BlockHexoriumCrystalBlock("blue")),
	hexorium_crystal_block_white = add(new BlockHexoriumCrystalBlock("white")),
	hexorium_crystal_block_black = add(new BlockHexoriumCrystalBlock("black")),

	// Energized Hexorium
	energized_hexorium_red = add(new BlockEnergizedHexorium(EHexColors.RED)),
	energized_hexorium_orange = add(new BlockEnergizedHexorium(EHexColors.ORANGE)),
	energized_hexorium_yellow = add(new BlockEnergizedHexorium(EHexColors.YELLOW)),
	energized_hexorium_lime = add(new BlockEnergizedHexorium(EHexColors.LIME)),
	energized_hexorium_green = add(new BlockEnergizedHexorium(EHexColors.GREEN)),
	energized_hexorium_turquoise = add(new BlockEnergizedHexorium(EHexColors.TURQUOISE)),
	energized_hexorium_cyan = add(new BlockEnergizedHexorium(EHexColors.CYAN)),
	energized_hexorium_skyblue = add(new BlockEnergizedHexorium(EHexColors.SKY_BLUE)),
	energized_hexorium_blue = add(new BlockEnergizedHexorium(EHexColors.BLUE)),
	energized_hexorium_purple = add(new BlockEnergizedHexorium(EHexColors.PURPLE)),
	energized_hexorium_magenta = add(new BlockEnergizedHexorium(EHexColors.MAGENTA)),
	energized_hexorium_pink = add(new BlockEnergizedHexorium(EHexColors.PINK)),
	energized_hexorium_white = add(new BlockEnergizedHexorium(EHexColors.WHITE)),
	energized_hexorium_lightgray = add(new BlockEnergizedHexorium(EHexColors.LIGHT_GRAY)),
	energized_hexorium_gray = add(new BlockEnergizedHexorium(EHexColors.GRAY)),
	energized_hexorium_darkgray = add(new BlockEnergizedHexorium(EHexColors.DARK_GRAY)),
	energized_hexorium_black = add(new BlockEnergizedHexorium(EHexColors.BLACK)),
	energized_hexorium_rainbow = add(new BlockEnergizedHexorium(EHexColors.RAINBOW)),

	// Engineered Hexorium Block
	engineered_hexorium_block_red = add(new BlockEngineeredHexoriumBlock(EHexColors.RED)),
	engineered_hexorium_block_orange = add(new BlockEngineeredHexoriumBlock(EHexColors.ORANGE)),
	engineered_hexorium_block_yellow = add(new BlockEngineeredHexoriumBlock(EHexColors.YELLOW)),
	engineered_hexorium_block_lime = add(new BlockEngineeredHexoriumBlock(EHexColors.LIME)),
	engineered_hexorium_block_green = add(new BlockEngineeredHexoriumBlock(EHexColors.GREEN)),
	engineered_hexorium_block_turquoise = add(new BlockEngineeredHexoriumBlock(EHexColors.TURQUOISE)),
	engineered_hexorium_block_cyan = add(new BlockEngineeredHexoriumBlock(EHexColors.CYAN)),
	engineered_hexorium_block_skyblue = add(new BlockEngineeredHexoriumBlock(EHexColors.SKY_BLUE)),
	engineered_hexorium_block_blue = add(new BlockEngineeredHexoriumBlock(EHexColors.BLUE)),
	engineered_hexorium_block_purple = add(new BlockEngineeredHexoriumBlock(EHexColors.PURPLE)),
	engineered_hexorium_block_magenta = add(new BlockEngineeredHexoriumBlock(EHexColors.MAGENTA)),
	engineered_hexorium_block_pink = add(new BlockEngineeredHexoriumBlock(EHexColors.PINK)),
	engineered_hexorium_block_white = add(new BlockEngineeredHexoriumBlock(EHexColors.WHITE)),
	engineered_hexorium_block_lightgray = add(new BlockEngineeredHexoriumBlock(EHexColors.LIGHT_GRAY)),
	engineered_hexorium_block_gray = add(new BlockEngineeredHexoriumBlock(EHexColors.GRAY)),
	engineered_hexorium_block_darkgray = add(new BlockEngineeredHexoriumBlock(EHexColors.DARK_GRAY)),
	engineered_hexorium_block_black = add(new BlockEngineeredHexoriumBlock(EHexColors.BLACK)),
	engineered_hexorium_block_rainbow = add(new BlockEngineeredHexoriumBlock(EHexColors.RAINBOW)),

	// Framed Hexorium Block
	framed_hexorium_block_red = add(new BlockFramedHexoriumBlock(EHexColors.RED)),
	framed_hexorium_block_orange = add(new BlockFramedHexoriumBlock(EHexColors.ORANGE)),
	framed_hexorium_block_yellow = add(new BlockFramedHexoriumBlock(EHexColors.YELLOW)),
	framed_hexorium_block_lime = add(new BlockFramedHexoriumBlock(EHexColors.LIME)),
	framed_hexorium_block_green = add(new BlockFramedHexoriumBlock(EHexColors.GREEN)),
	framed_hexorium_block_turquoise = add(new BlockFramedHexoriumBlock(EHexColors.TURQUOISE)),
	framed_hexorium_block_cyan = add(new BlockFramedHexoriumBlock(EHexColors.CYAN)),
	framed_hexorium_block_skyblue = add(new BlockFramedHexoriumBlock(EHexColors.SKY_BLUE)),
	framed_hexorium_block_blue = add(new BlockFramedHexoriumBlock(EHexColors.BLUE)),
	framed_hexorium_block_purple = add(new BlockFramedHexoriumBlock(EHexColors.PURPLE)),
	framed_hexorium_block_magenta = add(new BlockFramedHexoriumBlock(EHexColors.MAGENTA)),
	framed_hexorium_block_pink = add(new BlockFramedHexoriumBlock(EHexColors.PINK)),
	framed_hexorium_block_white = add(new BlockFramedHexoriumBlock(EHexColors.WHITE)),
	framed_hexorium_block_lightgray = add(new BlockFramedHexoriumBlock(EHexColors.LIGHT_GRAY)),
	framed_hexorium_block_gray = add(new BlockFramedHexoriumBlock(EHexColors.GRAY)),
	framed_hexorium_block_darkgray = add(new BlockFramedHexoriumBlock(EHexColors.DARK_GRAY)),
	framed_hexorium_block_black = add(new BlockFramedHexoriumBlock(EHexColors.BLACK)),
	framed_hexorium_block_rainbow = add(new BlockFramedHexoriumBlock(EHexColors.RAINBOW));

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