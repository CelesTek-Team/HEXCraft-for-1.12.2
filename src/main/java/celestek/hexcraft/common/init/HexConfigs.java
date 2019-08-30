package celestek.hexcraft.common.init;

import java.io.File;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraftforge.common.config.Configuration;

// FIXME I don't like how messy I've made this. CLEAN UP
public class HexConfigs
{
	public static File mainDir, dimDir;
	public static Int2ObjectMap<DimensionConfig> dimConfigs = new Int2ObjectOpenHashMap<DimensionConfig>();

	public static void load()
	{
		if(!dimDir.exists()) dimDir.mkdirs();
		File[] files = dimDir.listFiles();
		for(File file : files)
		{
			Configuration config = new Configuration(file);
			config.load();
			DimensionConfig dimConfig = new DimensionConfig(config,
					new MonolithConfig().load(config, CATEGORY_MONOLITH_RED, true, 100, 1, 0, 60, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_GREEN, true, 100, 1, 0, 60, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_BLUE, true, 100, 1, 0, 60, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_WHITE, true, 10, 1, 0, 20, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_BLACK, true, 10, 1, 0, 20, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_RED, true, 10, 2, 6, 0, 64, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_GREEN, true, 10, 2, 6, 0, 64, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_BLUE, true, 10, 2, 6, 0, 64, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_WHITE, true, 5, 1, 3, 0, 20, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_BLACK, true, 5, 1, 3, 0, 20, "minecraft:stone"));
			if(config.hasChanged()) config.save();
			String name = file.getName();
			dimConfigs.put(Integer.parseInt(name.substring(0, name.length() - 4)), dimConfig);
		}
		if(!dimConfigs.containsKey(0))
		{
			File file = new File(dimDir, "0.cfg");
			Configuration config = new Configuration(file);
			config.load();
			DimensionConfig dimConfig = new DimensionConfig(config,
					new MonolithConfig().load(config, CATEGORY_MONOLITH_RED, true, 100, 1, 0, 60, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_GREEN, true, 100, 1, 0, 60, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_BLUE, true, 100, 1, 0, 60, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_WHITE, true, 10, 1, 0, 20, "minecraft:stone"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_BLACK, true, 10, 1, 0, 20, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_RED, true, 10, 2, 6, 0, 64, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_GREEN, true, 10, 2, 6, 0, 64, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_BLUE, true, 10, 2, 6, 0, 64, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_WHITE, true, 5, 1, 3, 0, 20, "minecraft:stone"),
					new OreConfig().load(config, CATEGORY_ORE_BLACK, true, 5, 1, 3, 0, 20, "minecraft:stone"));
			if(config.hasChanged()) config.save();
			dimConfigs.put(0, dimConfig);
		}
		if(!dimConfigs.containsKey(-1))
		{
			File file = new File(dimDir, "-1.cfg");
			Configuration config = new Configuration(file);
			config.load();
			DimensionConfig dimConfig = new DimensionConfig(config,
					new MonolithConfig().load(config, CATEGORY_MONOLITH_RED, true, 10, 1, 0, 127, "minecraft:netherrack"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_GREEN, true, 10, 1, 0, 127, "minecraft:netherrack"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_BLUE, true, 10, 1, 0, 127, "minecraft:netherrack"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_WHITE, true, 50, 1, 0, 127, "minecraft:netherrack"),
					new MonolithConfig().load(config, CATEGORY_MONOLITH_BLACK, true, 50, 1, 0, 127, "minecraft:netherrack"),
					new OreConfig().load(config, CATEGORY_ORE_RED, true, 5, 1, 3, 0, 127, "minecraft:netherrack"),
					new OreConfig().load(config, CATEGORY_ORE_GREEN, true, 5, 1, 3, 0, 127, "minecraft:netherrack"),
					new OreConfig().load(config, CATEGORY_ORE_BLUE, true, 5, 1, 3, 0, 127, "minecraft:netherrack"),
					new OreConfig().load(config, CATEGORY_ORE_WHITE, true, 10, 2, 6, 0, 127, "minecraft:netherrack"),
					new OreConfig().load(config, CATEGORY_ORE_BLACK, true, 10, 2, 6, 0, 127, "minecraft:netherrack"));
			if(config.hasChanged()) config.save();
			dimConfigs.put(-1, dimConfig);
		}
	}

	public static final String
	CATEGORY_MONOLITH_RED = "Red monolith generation",
	CATEGORY_MONOLITH_GREEN = "Green monolith generation",
	CATEGORY_MONOLITH_BLUE = "Blue monolith generation",
	CATEGORY_MONOLITH_WHITE = "White monolith generation",
	CATEGORY_MONOLITH_BLACK = "Black monolith generation",
	CATEGORY_ORE_RED = "Red ore generation",
	CATEGORY_ORE_GREEN = "Green ore generation",
	CATEGORY_ORE_BLUE = "Blue ore generation",
	CATEGORY_ORE_WHITE = "White ore generation",
	CATEGORY_ORE_BLACK = "Black ore generation";

	public static final class DimensionConfig
	{
		public final Configuration config;
		public final MonolithConfig monolithRed, monolithGreen, monolithBlue, monolithWhite, monolithBlack;
		public final OreConfig oreRed, oreGreen, oreBlue, oreWhite, oreBlack;

		public DimensionConfig(Configuration config, MonolithConfig monolithRed, MonolithConfig monolithGreen, MonolithConfig monolithBlue, MonolithConfig monolithWhite, MonolithConfig monolithBlack, OreConfig oreRed, OreConfig oreGreen, OreConfig oreBlue, OreConfig oreWhite, OreConfig oreBlack)
		{
			this.config = config;
			this.monolithRed = monolithRed;
			this.monolithGreen = monolithGreen;
			this.monolithBlue = monolithBlue;
			this.monolithWhite = monolithWhite;
			this.monolithBlack = monolithBlack;
			this.oreRed = oreRed;
			this.oreGreen = oreGreen;
			this.oreBlue = oreBlue;
			this.oreWhite = oreWhite;
			this.oreBlack = oreBlack;
		}
	}

	public static final class MonolithConfig
	{
		public boolean enabled;
		public int chance, amount, heightMin, heightMax;
		public String[] generateOn;

		public MonolithConfig load(Configuration config, String category, boolean generate, int chance, int amount, int heightMin, int heightMax, String... generateOn)
		{
			this.enabled = config.getBoolean("Enabled", category, generate, "");
			this.chance = config.getInt("Chance of generating", category, chance, 1, 100, "");
			this.amount = config.getInt("Monoliths per chunk", category, amount, 1, 64, "");
			this.heightMin = config.getInt("Minimum height", category, heightMin, 0, 255, "");
			this.heightMax = config.getInt("Maximum height", category, heightMax, 0, 255, "");
			this.generateOn = config.getStringList("Generate on", category, generateOn, "");
			return this;
		}
	}

	public static final class OreConfig
	{
		public boolean enabled;
		public int veins, sizeMin, sizeMax, heightMin, heightMax;
		public String[] generateIn;

		public OreConfig load(Configuration config, String category, boolean generate, int veins, int sizeMin, int sizeMax, int heightMin, int heightMax, String... generateIn)
		{
			this.enabled = config.getBoolean("Enabled", category, generate, "");
			this.veins = config.getInt("Veins per chunk", category, veins, 1, 64, "");
			this.sizeMin = config.getInt("Minimum vein size", category, sizeMin, 1, 64, "");
			this.sizeMax = config.getInt("Maximum vein size", category, sizeMax, 1, 64, "");
			this.heightMin = config.getInt("Minimum height", category, heightMin, 0, 255, "");
			this.heightMax = config.getInt("Maximum height", category, heightMax, 0, 255, "");
			this.generateIn = config.getStringList("Generate in", category, generateIn, "");
			return this;
		}
	}
}