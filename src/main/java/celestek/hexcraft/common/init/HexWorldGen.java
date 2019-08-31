package celestek.hexcraft.common.init;

import java.util.ArrayList;
import java.util.Random;

import celestek.hexcraft.common.init.HexConfigs.MonolithConfig;
import celestek.hexcraft.common.init.HexConfigs.OreConfig;
import celestek.hexcraft.common.world.gen.GenMonolith;
import celestek.hexcraft.common.world.gen.GenOre;
import celestek.hexcraft.utility.HexUtilities;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

//FIXME Don't need so many instances. Just use one instance of ore gen and monolith gen and pass in the right params?
public class HexWorldGen implements IWorldGenerator
{
	public final Int2ObjectMap<ArrayList<WorldGenerator>> dimGens = new Int2ObjectOpenHashMap<ArrayList<WorldGenerator>>();
	public static HexWorldGen worldgen;

	public static void register()
	{
		worldgen = new HexWorldGen();
		GameRegistry.registerWorldGenerator(worldgen, 0);
	}

	// FIXME Don't hard-code
	public HexWorldGen()
	{
		HexConfigs.dimConfigs.forEach((id, config) ->
		{
			ArrayList<WorldGenerator> generators = new ArrayList<WorldGenerator>();
			boolean nether = id == -1;
			generators.add(fromMonolithConfig(nether ? HexBlocks.hexorium_nether_monolith_red.getDefaultState() : HexBlocks.hexorium_monolith_red.getDefaultState(), config.monolithRed));
			generators.add(fromMonolithConfig(nether ? HexBlocks.hexorium_nether_monolith_green.getDefaultState() : HexBlocks.hexorium_monolith_green.getDefaultState(), config.monolithGreen));
			generators.add(fromMonolithConfig(nether ? HexBlocks.hexorium_nether_monolith_blue.getDefaultState() : HexBlocks.hexorium_monolith_blue.getDefaultState(), config.monolithBlue));
			generators.add(fromMonolithConfig(nether ? HexBlocks.hexorium_nether_monolith_white.getDefaultState() : HexBlocks.hexorium_monolith_white.getDefaultState(), config.monolithWhite));
			generators.add(fromMonolithConfig(nether ? HexBlocks.hexorium_nether_monolith_black.getDefaultState() : HexBlocks.hexorium_monolith_black.getDefaultState(), config.monolithBlack));
			generators.add(fromOreConfig(nether ? HexBlocks.hexorium_nether_ore_red.getDefaultState() : HexBlocks.hexorium_ore_red.getDefaultState(), config.oreRed));
			generators.add(fromOreConfig(nether ? HexBlocks.hexorium_nether_ore_green.getDefaultState() : HexBlocks.hexorium_ore_green.getDefaultState(), config.oreGreen));
			generators.add(fromOreConfig(nether ? HexBlocks.hexorium_nether_ore_blue.getDefaultState() : HexBlocks.hexorium_ore_blue.getDefaultState(), config.oreBlue));
			generators.add(fromOreConfig(nether ? HexBlocks.hexorium_nether_ore_white.getDefaultState() : HexBlocks.hexorium_ore_white.getDefaultState(), config.oreWhite));
			generators.add(fromOreConfig(nether ? HexBlocks.hexorium_nether_ore_black.getDefaultState() : HexBlocks.hexorium_ore_black.getDefaultState(), config.oreBlack));
			dimGens.put(id, generators);
		});
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		ArrayList<WorldGenerator> generators = dimGens.get(world.provider.getDimension());
		if(generators == null) return;
		for(WorldGenerator generator : generators) generator.generate(world, random, new BlockPos(chunkX, 0, chunkZ));
	}

	// FIXME Bad
	public static GenMonolith fromMonolithConfig(IBlockState state, MonolithConfig config)
	{
		return new GenMonolith(config.enabled, state, config.chance, config.amount, config.heightMin, config.heightMax, HexUtilities.createBlockFilter(config.generateOn));
	}

	// FIXME Ditto
	public static GenOre fromOreConfig(IBlockState state, OreConfig config)
	{
		return new GenOre(config.enabled, state, config.veins, config.sizeMin, config.sizeMax, config.heightMin, config.heightMax, HexUtilities.createBlockFilter(config.generateIn));
	}
}