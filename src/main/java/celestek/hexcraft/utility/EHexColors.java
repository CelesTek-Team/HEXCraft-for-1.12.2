package celestek.hexcraft.utility;

import java.util.HashMap;
import java.util.Map;

import celestek.hexcraft.common.init.HexItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * All the different colors used by the mod. Each color consists of a hex color code, name and a set of crystal drops of respective colors
 */
public enum EHexColors {
	RED (0xff0000, "red", new Drop(HexItems.hexorium_crystal_red, 8)),
	ORANGE (0xff8000, "orange", new Drop(HexItems.hexorium_crystal_red, 6), new Drop(HexItems.hexorium_crystal_green, 2)),
	YELLOW (0xffff00, "yellow", new Drop(HexItems.hexorium_crystal_red, 4), new Drop(HexItems.hexorium_crystal_green, 4)),
	LIME (0x80ff00, "lime", new Drop(HexItems.hexorium_crystal_red, 2), new Drop(HexItems.hexorium_crystal_green, 6)),
	GREEN (0x00ff00, "green", new Drop(HexItems.hexorium_crystal_green, 8)),
	TURQUOISE (0x00ff80, "turquoise", new Drop(HexItems.hexorium_crystal_green, 6), new Drop(HexItems.hexorium_crystal_blue, 2)),
	CYAN (0x00ffff, "cyan", new Drop(HexItems.hexorium_crystal_green, 4), new Drop(HexItems.hexorium_crystal_blue, 4)),
	SKY_BLUE (0x0080ff, "skyblue", new Drop(HexItems.hexorium_crystal_green, 2), new Drop(HexItems.hexorium_crystal_blue, 6)),
	BLUE (0x0000ff, "blue", new Drop(HexItems.hexorium_crystal_blue, 8)),
	PURPLE (0x8000ff, "purple", new Drop(HexItems.hexorium_crystal_red, 2), new Drop(HexItems.hexorium_crystal_blue, 6)),
	MAGENTA (0xff00ff, "magenta", new Drop(HexItems.hexorium_crystal_red, 4), new Drop(HexItems.hexorium_crystal_blue, 4)),
	PINK (0xff0080, "pink", new Drop(HexItems.hexorium_crystal_red, 6), new Drop(HexItems.hexorium_crystal_blue, 2)),
	WHITE (0xffffff, "white", new Drop(HexItems.hexorium_crystal_white, 8)),
	LIGHT_GRAY (0xc0c0c0, "lightgray", new Drop(HexItems.hexorium_crystal_white, 6), new Drop(HexItems.hexorium_crystal_black, 2)),
	GRAY (0x808080, "gray", new Drop(HexItems.hexorium_crystal_white, 4), new Drop(HexItems.hexorium_crystal_black, 4)),
	DARK_GRAY (0x404040, "darkgray", new Drop(HexItems.hexorium_crystal_white, 2), new Drop(HexItems.hexorium_crystal_black, 6)),
	BLACK (0x181818, "black", new Drop(HexItems.hexorium_crystal_black, 8)),
	RAINBOW (-2, "rainbow", new Drop(HexItems.hexorium_crystal_red, 2), new Drop(HexItems.hexorium_crystal_green, 2), new Drop(HexItems.hexorium_crystal_blue, 2), new Drop(HexItems.hexorium_crystal_white, 2)),
	DIMMED (0x808080, "dimmed"),
	NONE (-1, null);

	private static final Map<Integer, EHexColors> COLOR_LOOKUP = new HashMap<Integer, EHexColors>();
	private static final Map<String, EHexColors> NAME_LOOKUP = new HashMap<String, EHexColors>();

	static
	{
		for(EHexColors color : EHexColors.values())
		{
			COLOR_LOOKUP.put(color.color, color);
			NAME_LOOKUP.put(color.name, color);
		}
	}

	public final int color;
	public final String name;
	protected final Drop[] drops;

	EHexColors(int color, String name, Drop... drops) {
		this.color = color;
		this.name = name;
		this.drops = drops;
	}

	/**
	 * Adds the crystal drops of this color to the given list
	 */
	public NonNullList<ItemStack> addDrops(NonNullList<ItemStack> drops)
	{
		for(Drop drop : this.drops) drops.add(drop.createStack());
		return drops;
	}

	/**
	 * Gets the corresponding color by its hex color code
	 */
	public static EHexColors fromColor(int color)
	{
		return COLOR_LOOKUP.get(color);
	}

	/**
	 * Gets the corresponding color by its name
	 */
	public static EHexColors fromName(String name)
	{
		return NAME_LOOKUP.get(name);
	}
}