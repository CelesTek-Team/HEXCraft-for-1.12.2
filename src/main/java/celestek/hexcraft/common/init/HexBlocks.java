package celestek.hexcraft.common.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.client.event.HexClientEvents;
import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.block.BlockBlockOfHexoriumCrystal;
import celestek.hexcraft.common.block.BlockConcentricHexoriumBlock;
import celestek.hexcraft.common.block.BlockEnergizedHexorium;
import celestek.hexcraft.common.block.BlockEnergizedHexoriumMonolith;
import celestek.hexcraft.common.block.BlockEngineeredHexoriumBlock;
import celestek.hexcraft.common.block.BlockFramedHexoriumBlock;
import celestek.hexcraft.common.block.BlockGlowingHexoriumCoatedStone;
import celestek.hexcraft.common.block.BlockGlowingHexoriumGlass;
import celestek.hexcraft.common.block.BlockHexoriumCoatedStone;
import celestek.hexcraft.common.block.BlockHexoriumDoor;
import celestek.hexcraft.common.block.BlockHexoriumLamp;
import celestek.hexcraft.common.block.BlockHexoriumNetherOre;
import celestek.hexcraft.common.block.BlockHexoriumOre;
import celestek.hexcraft.common.block.BlockHexoriumStructureCasing;
import celestek.hexcraft.common.block.BlockInvertedHexoriumLamp;
import celestek.hexcraft.common.block.BlockPlatedHexoriumBlock;
import celestek.hexcraft.common.block.BlockTemperedHexoriumGlass;
import celestek.hexcraft.common.block.HexBlock;
import celestek.hexcraft.common.item.HexItemBlock;
import celestek.hexcraft.utility.Drop;
import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains all of HEXCraft's blocks. Automatically registers the {@link HexBlock} state mapper, color handler and its model override
 */
public final class HexBlocks {
	private static List<HexBlock> blocks = new ArrayList<>();

	public static final Block

	// Block of Hexorium Crystal
	block_of_hexorium_crystal_red = add(new BlockBlockOfHexoriumCrystal(EHexColor.RED)),
	block_of_hexorium_crystal_green = add(new BlockBlockOfHexoriumCrystal(EHexColor.GREEN)),
	block_of_hexorium_crystal_blue = add(new BlockBlockOfHexoriumCrystal(EHexColor.BLUE)),
	block_of_hexorium_crystal_white = add(new BlockBlockOfHexoriumCrystal(EHexColor.WHITE)),
	block_of_hexorium_crystal_black = add(new BlockBlockOfHexoriumCrystal(EHexColor.BLACK)),

	// Energized Hexorium
	energized_hexorium_red = add(new BlockEnergizedHexorium(EHexColor.RED)),
	energized_hexorium_orange = add(new BlockEnergizedHexorium(EHexColor.ORANGE)),
	energized_hexorium_yellow = add(new BlockEnergizedHexorium(EHexColor.YELLOW)),
	energized_hexorium_lime = add(new BlockEnergizedHexorium(EHexColor.LIME)),
	energized_hexorium_green = add(new BlockEnergizedHexorium(EHexColor.GREEN)),
	energized_hexorium_turquoise = add(new BlockEnergizedHexorium(EHexColor.TURQUOISE)),
	energized_hexorium_cyan = add(new BlockEnergizedHexorium(EHexColor.CYAN)),
	energized_hexorium_skyblue = add(new BlockEnergizedHexorium(EHexColor.SKY_BLUE)),
	energized_hexorium_blue = add(new BlockEnergizedHexorium(EHexColor.BLUE)),
	energized_hexorium_purple = add(new BlockEnergizedHexorium(EHexColor.PURPLE)),
	energized_hexorium_magenta = add(new BlockEnergizedHexorium(EHexColor.MAGENTA)),
	energized_hexorium_pink = add(new BlockEnergizedHexorium(EHexColor.PINK)),
	energized_hexorium_white = add(new BlockEnergizedHexorium(EHexColor.WHITE)),
	energized_hexorium_lightgray = add(new BlockEnergizedHexorium(EHexColor.LIGHT_GRAY)),
	energized_hexorium_gray = add(new BlockEnergizedHexorium(EHexColor.GRAY)),
	energized_hexorium_darkgray = add(new BlockEnergizedHexorium(EHexColor.DARK_GRAY)),
	energized_hexorium_black = add(new BlockEnergizedHexorium(EHexColor.BLACK)),
	energized_hexorium_rainbow = add(new BlockEnergizedHexorium(EHexColor.RAINBOW)),

	// Engineered Hexorium Block
	engineered_hexorium_block_red = add(new BlockEngineeredHexoriumBlock(EHexColor.RED)),
	engineered_hexorium_block_orange = add(new BlockEngineeredHexoriumBlock(EHexColor.ORANGE)),
	engineered_hexorium_block_yellow = add(new BlockEngineeredHexoriumBlock(EHexColor.YELLOW)),
	engineered_hexorium_block_lime = add(new BlockEngineeredHexoriumBlock(EHexColor.LIME)),
	engineered_hexorium_block_green = add(new BlockEngineeredHexoriumBlock(EHexColor.GREEN)),
	engineered_hexorium_block_turquoise = add(new BlockEngineeredHexoriumBlock(EHexColor.TURQUOISE)),
	engineered_hexorium_block_cyan = add(new BlockEngineeredHexoriumBlock(EHexColor.CYAN)),
	engineered_hexorium_block_skyblue = add(new BlockEngineeredHexoriumBlock(EHexColor.SKY_BLUE)),
	engineered_hexorium_block_blue = add(new BlockEngineeredHexoriumBlock(EHexColor.BLUE)),
	engineered_hexorium_block_purple = add(new BlockEngineeredHexoriumBlock(EHexColor.PURPLE)),
	engineered_hexorium_block_magenta = add(new BlockEngineeredHexoriumBlock(EHexColor.MAGENTA)),
	engineered_hexorium_block_pink = add(new BlockEngineeredHexoriumBlock(EHexColor.PINK)),
	engineered_hexorium_block_white = add(new BlockEngineeredHexoriumBlock(EHexColor.WHITE)),
	engineered_hexorium_block_lightgray = add(new BlockEngineeredHexoriumBlock(EHexColor.LIGHT_GRAY)),
	engineered_hexorium_block_gray = add(new BlockEngineeredHexoriumBlock(EHexColor.GRAY)),
	engineered_hexorium_block_darkgray = add(new BlockEngineeredHexoriumBlock(EHexColor.DARK_GRAY)),
	engineered_hexorium_block_black = add(new BlockEngineeredHexoriumBlock(EHexColor.BLACK)),
	engineered_hexorium_block_rainbow = add(new BlockEngineeredHexoriumBlock(EHexColor.RAINBOW)),

	// Framed Hexorium Block
	framed_hexorium_block_red = add(new BlockFramedHexoriumBlock(EHexColor.RED)),
	framed_hexorium_block_orange = add(new BlockFramedHexoriumBlock(EHexColor.ORANGE)),
	framed_hexorium_block_yellow = add(new BlockFramedHexoriumBlock(EHexColor.YELLOW)),
	framed_hexorium_block_lime = add(new BlockFramedHexoriumBlock(EHexColor.LIME)),
	framed_hexorium_block_green = add(new BlockFramedHexoriumBlock(EHexColor.GREEN)),
	framed_hexorium_block_turquoise = add(new BlockFramedHexoriumBlock(EHexColor.TURQUOISE)),
	framed_hexorium_block_cyan = add(new BlockFramedHexoriumBlock(EHexColor.CYAN)),
	framed_hexorium_block_skyblue = add(new BlockFramedHexoriumBlock(EHexColor.SKY_BLUE)),
	framed_hexorium_block_blue = add(new BlockFramedHexoriumBlock(EHexColor.BLUE)),
	framed_hexorium_block_purple = add(new BlockFramedHexoriumBlock(EHexColor.PURPLE)),
	framed_hexorium_block_magenta = add(new BlockFramedHexoriumBlock(EHexColor.MAGENTA)),
	framed_hexorium_block_pink = add(new BlockFramedHexoriumBlock(EHexColor.PINK)),
	framed_hexorium_block_white = add(new BlockFramedHexoriumBlock(EHexColor.WHITE)),
	framed_hexorium_block_lightgray = add(new BlockFramedHexoriumBlock(EHexColor.LIGHT_GRAY)),
	framed_hexorium_block_gray = add(new BlockFramedHexoriumBlock(EHexColor.GRAY)),
	framed_hexorium_block_darkgray = add(new BlockFramedHexoriumBlock(EHexColor.DARK_GRAY)),
	framed_hexorium_block_black = add(new BlockFramedHexoriumBlock(EHexColor.BLACK)),
	framed_hexorium_block_rainbow = add(new BlockFramedHexoriumBlock(EHexColor.RAINBOW)),

	// Plated Hexorium Block
	plated_hexorium_block_red = add(new BlockPlatedHexoriumBlock(EHexColor.RED)),
	plated_hexorium_block_orange = add(new BlockPlatedHexoriumBlock(EHexColor.ORANGE)),
	plated_hexorium_block_yellow = add(new BlockPlatedHexoriumBlock(EHexColor.YELLOW)),
	plated_hexorium_block_lime = add(new BlockPlatedHexoriumBlock(EHexColor.LIME)),
	plated_hexorium_block_green = add(new BlockPlatedHexoriumBlock(EHexColor.GREEN)),
	plated_hexorium_block_turquoise = add(new BlockPlatedHexoriumBlock(EHexColor.TURQUOISE)),
	plated_hexorium_block_cyan = add(new BlockPlatedHexoriumBlock(EHexColor.CYAN)),
	plated_hexorium_block_skyblue = add(new BlockPlatedHexoriumBlock(EHexColor.SKY_BLUE)),
	plated_hexorium_block_blue = add(new BlockPlatedHexoriumBlock(EHexColor.BLUE)),
	plated_hexorium_block_purple = add(new BlockPlatedHexoriumBlock(EHexColor.PURPLE)),
	plated_hexorium_block_magenta = add(new BlockPlatedHexoriumBlock(EHexColor.MAGENTA)),
	plated_hexorium_block_pink = add(new BlockPlatedHexoriumBlock(EHexColor.PINK)),
	plated_hexorium_block_white = add(new BlockPlatedHexoriumBlock(EHexColor.WHITE)),
	plated_hexorium_block_lightgray = add(new BlockPlatedHexoriumBlock(EHexColor.LIGHT_GRAY)),
	plated_hexorium_block_gray = add(new BlockPlatedHexoriumBlock(EHexColor.GRAY)),
	plated_hexorium_block_darkgray = add(new BlockPlatedHexoriumBlock(EHexColor.DARK_GRAY)),
	plated_hexorium_block_black = add(new BlockPlatedHexoriumBlock(EHexColor.BLACK)),
	plated_hexorium_block_rainbow = add(new BlockPlatedHexoriumBlock(EHexColor.RAINBOW)),

	// Concentric Hexorium Block
	concentric_hexorium_block_red = add(new BlockConcentricHexoriumBlock(EHexColor.RED)),
	concentric_hexorium_block_orange = add(new BlockConcentricHexoriumBlock(EHexColor.ORANGE)),
	concentric_hexorium_block_yellow = add(new BlockConcentricHexoriumBlock(EHexColor.YELLOW)),
	concentric_hexorium_block_lime = add(new BlockConcentricHexoriumBlock(EHexColor.LIME)),
	concentric_hexorium_block_green = add(new BlockConcentricHexoriumBlock(EHexColor.GREEN)),
	concentric_hexorium_block_turquoise = add(new BlockConcentricHexoriumBlock(EHexColor.TURQUOISE)),
	concentric_hexorium_block_cyan = add(new BlockConcentricHexoriumBlock(EHexColor.CYAN)),
	concentric_hexorium_block_skyblue = add(new BlockConcentricHexoriumBlock(EHexColor.SKY_BLUE)),
	concentric_hexorium_block_blue = add(new BlockConcentricHexoriumBlock(EHexColor.BLUE)),
	concentric_hexorium_block_purple = add(new BlockConcentricHexoriumBlock(EHexColor.PURPLE)),
	concentric_hexorium_block_magenta = add(new BlockConcentricHexoriumBlock(EHexColor.MAGENTA)),
	concentric_hexorium_block_pink = add(new BlockConcentricHexoriumBlock(EHexColor.PINK)),
	concentric_hexorium_block_white = add(new BlockConcentricHexoriumBlock(EHexColor.WHITE)),
	concentric_hexorium_block_lightgray = add(new BlockConcentricHexoriumBlock(EHexColor.LIGHT_GRAY)),
	concentric_hexorium_block_gray = add(new BlockConcentricHexoriumBlock(EHexColor.GRAY)),
	concentric_hexorium_block_darkgray = add(new BlockConcentricHexoriumBlock(EHexColor.DARK_GRAY)),
	concentric_hexorium_block_black = add(new BlockConcentricHexoriumBlock(EHexColor.BLACK)),
	concentric_hexorium_block_rainbow = add(new BlockConcentricHexoriumBlock(EHexColor.RAINBOW)),

	// Hexorium Structure Casing
	hexorium_structure_casing_red = add(new BlockHexoriumStructureCasing(EHexColor.RED)),
	hexorium_structure_casing_orange = add(new BlockHexoriumStructureCasing(EHexColor.ORANGE)),
	hexorium_structure_casing_yellow = add(new BlockHexoriumStructureCasing(EHexColor.YELLOW)),
	hexorium_structure_casing_lime = add(new BlockHexoriumStructureCasing(EHexColor.LIME)),
	hexorium_structure_casing_green = add(new BlockHexoriumStructureCasing(EHexColor.GREEN)),
	hexorium_structure_casing_turquoise = add(new BlockHexoriumStructureCasing(EHexColor.TURQUOISE)),
	hexorium_structure_casing_cyan = add(new BlockHexoriumStructureCasing(EHexColor.CYAN)),
	hexorium_structure_casing_skyblue = add(new BlockHexoriumStructureCasing(EHexColor.SKY_BLUE)),
	hexorium_structure_casing_blue = add(new BlockHexoriumStructureCasing(EHexColor.BLUE)),
	hexorium_structure_casing_purple = add(new BlockHexoriumStructureCasing(EHexColor.PURPLE)),
	hexorium_structure_casing_magenta = add(new BlockHexoriumStructureCasing(EHexColor.MAGENTA)),
	hexorium_structure_casing_pink = add(new BlockHexoriumStructureCasing(EHexColor.PINK)),
	hexorium_structure_casing_white = add(new BlockHexoriumStructureCasing(EHexColor.WHITE)),
	hexorium_structure_casing_lightgray = add(new BlockHexoriumStructureCasing(EHexColor.LIGHT_GRAY)),
	hexorium_structure_casing_gray = add(new BlockHexoriumStructureCasing(EHexColor.GRAY)),
	hexorium_structure_casing_darkgray = add(new BlockHexoriumStructureCasing(EHexColor.DARK_GRAY)),
	hexorium_structure_casing_black = add(new BlockHexoriumStructureCasing(EHexColor.BLACK)),
	hexorium_structure_casing_rainbow = add(new BlockHexoriumStructureCasing(EHexColor.RAINBOW)),

	// Hexorium-Coated Stone
	hexorium_coated_stone = add(new BlockHexoriumCoatedStone()),

	// Glowing Hexorium-Coated Stone
	glowing_hexorium_coated_stone_red = add(new BlockGlowingHexoriumCoatedStone(EHexColor.RED)),
	glowing_hexorium_coated_stone_orange = add(new BlockGlowingHexoriumCoatedStone(EHexColor.ORANGE)),
	glowing_hexorium_coated_stone_yellow = add(new BlockGlowingHexoriumCoatedStone(EHexColor.YELLOW)),
	glowing_hexorium_coated_stone_lime = add(new BlockGlowingHexoriumCoatedStone(EHexColor.LIME)),
	glowing_hexorium_coated_stone_green = add(new BlockGlowingHexoriumCoatedStone(EHexColor.GREEN)),
	glowing_hexorium_coated_stone_turquoise = add(new BlockGlowingHexoriumCoatedStone(EHexColor.TURQUOISE)),
	glowing_hexorium_coated_stone_cyan = add(new BlockGlowingHexoriumCoatedStone(EHexColor.CYAN)),
	glowing_hexorium_coated_stone_skyblue = add(new BlockGlowingHexoriumCoatedStone(EHexColor.SKY_BLUE)),
	glowing_hexorium_coated_stone_blue = add(new BlockGlowingHexoriumCoatedStone(EHexColor.BLUE)),
	glowing_hexorium_coated_stone_purple = add(new BlockGlowingHexoriumCoatedStone(EHexColor.PURPLE)),
	glowing_hexorium_coated_stone_magenta = add(new BlockGlowingHexoriumCoatedStone(EHexColor.MAGENTA)),
	glowing_hexorium_coated_stone_pink = add(new BlockGlowingHexoriumCoatedStone(EHexColor.PINK)),
	glowing_hexorium_coated_stone_white = add(new BlockGlowingHexoriumCoatedStone(EHexColor.WHITE)),
	glowing_hexorium_coated_stone_lightgray = add(new BlockGlowingHexoriumCoatedStone(EHexColor.LIGHT_GRAY)),
	glowing_hexorium_coated_stone_gray = add(new BlockGlowingHexoriumCoatedStone(EHexColor.GRAY)),
	glowing_hexorium_coated_stone_darkgray = add(new BlockGlowingHexoriumCoatedStone(EHexColor.DARK_GRAY)),
	glowing_hexorium_coated_stone_black = add(new BlockGlowingHexoriumCoatedStone(EHexColor.BLACK)),
	glowing_hexorium_coated_stone_rainbow = add(new BlockGlowingHexoriumCoatedStone(EHexColor.RAINBOW)),

	// Energized Monolith
	energized_hexorium_monolith_red = add(new BlockEnergizedHexoriumMonolith(EHexColor.RED)),

	// Hexorium Ore
	hexorium_ore_red = add(new BlockHexoriumOre(EHexColor.RED, new Drop(HexItems.hexorium_crystal_red, 2, 4))),

	// Hexorium Nether Ore
	hexorium_nether_ore_red = add(new BlockHexoriumNetherOre(EHexColor.RED, new Drop(HexItems.hexorium_crystal_red, 1, 2))),

	// Hexorium Door
	hexorium_door_red = add(new BlockHexoriumDoor(EHexColor.RED)),

	// Glowing Hexorium Glass
	glowing_hexorium_glass_red = add(new BlockGlowingHexoriumGlass(EHexColor.RED)),
	glowing_hexorium_glass_orange = add(new BlockGlowingHexoriumGlass(EHexColor.ORANGE)),

	// Tempered Hexorium Glass
	tempered_hexorium_glass = add(new BlockTemperedHexoriumGlass()),

	// Hexorium Lamp
	hexorium_lamp_red = add(new BlockHexoriumLamp(EHexColor.RED)),

	// Inverted Hexorium Lamp
	inverted_hexorium_lamp_red = add(new BlockInvertedHexoriumLamp(EHexColor.RED));

	private HexBlocks() {}

	public static void register(RegistryEvent.Register<Block> event) {
		IForgeRegistry registry = event.getRegistry();
		for(HexBlock block : blocks)
			registry.register(block);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels() {
		for(HexBlock block : blocks)
		{
			// Register a state mapper and add a custom model override to all of its models if the block provides these
			Optional<HexStateMapper> mapper = block.addStateMapper();
			if(mapper.isPresent())
			{
				ModelLoader.setCustomStateMapper(block, mapper.get());
				for(ResourceLocation path : mapper.get().paths)
				{
					Optional<Function<IBakedModel, IBakedModel>> override = block.addModelOverride(path);
					if(override.isPresent()) HexClientEvents.addModelOverride(path, override.get());
				}
			}
			else
			{
				Optional<Function<IBakedModel, IBakedModel>> override = block.addModelOverride(block.getRegistryName());
				if(override.isPresent()) HexClientEvents.addModelOverride(block.getRegistryName(), override.get());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerColors(ColorHandlerEvent.Block event) {
		BlockColors registry = event.getBlockColors();
		for (HexBlock block : blocks)
		{
			// Register a color handler if the block provides one
			Optional<IBlockColor> color = block.addBlockColor();
			if(color.isPresent())
				registry.registerBlockColorHandler(color.get(), block);
		}
	}

	/**
	 * Add the given block for automatic registry
	 */
	public static HexBlock add(HexBlock block)
	{
		blocks.add(block);
		HexItems.add(new HexItemBlock(block));
		return block;
	}
}