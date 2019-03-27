package celestek.hexcraft.utility;

import java.util.HashMap;
import java.util.Map;

import celestek.hexcraft.common.init.HexItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * All the different colors used by HEXCraft. Each color consists of a hex color code, name and a set of crystal drops of respective colors
 */
public enum EHexColor {
	RED (0xff0000, "red"),
	ORANGE (0xff8000, "orange"),
	YELLOW (0xffff00, "yellow"),
	LIME (0x80ff00, "lime"),
	GREEN (0x00ff00, "green"),
	TURQUOISE (0x00ff80, "turquoise"),
	CYAN (0x00ffff, "cyan"),
	SKY_BLUE (0x0080ff, "skyblue"),
	BLUE (0x0000ff, "blue"),
	PURPLE (0x8000ff, "purple"),
	MAGENTA (0xff00ff, "magenta"),
	PINK (0xff0080, "pink"),
	WHITE (0xffffff, "white"),
	LIGHT_GRAY (0xc0c0c0, "lightgray"),
	GRAY (0x808080, "gray"),
	DARK_GRAY (0x404040, "darkgray"),
	BLACK (0x181818, "black"),
	RAINBOW (-2, "rainbow"),
	DIMMED (0x808080, "dimmed"),
	NONE (-1, "none");

	public static final float DARKEN_MULTIPLIER = 0.15f;

	private static final Map<Integer, EHexColor> COLOR_LOOKUP = new HashMap<Integer, EHexColor>();
	private static final Map<String, EHexColor> NAME_LOOKUP = new HashMap<String, EHexColor>();
	static
	{
		for(EHexColor color : EHexColor.values())
		{
			COLOR_LOOKUP.put(color.color, color);
			NAME_LOOKUP.put(color.name, color);
		}
	}

	public final int color;
	public final String name;

	EHexColor(int color, String name) {
		this.color = color;
		this.name = name;
	}

	/**
	 * Returns this color multiplied by the given multiplier
	 */
	public int darken(float multiplier)
	{
		// Unpack the RGB values
		int red = (this.color >> 16) & 255;
		int green = (this.color >> 8) & 255;
		int blue = this.color & 255;
		// Multiply the values and pack them
		red = (int) ((float) red * multiplier) << 16;
		green = (int) ((float) green * multiplier) << 8;
		blue = (int) ((float) blue * multiplier);
		// Combine the values
		return red | green | blue;
	}

	/**
	 * Determines whether this color has a normal color value or not
	 */
	public boolean isSpecial()
	{
		return this.color < 0;
	}

	/**
	 * Adds the crystal drops of this color to the given list
	 */
	public NonNullList<ItemStack> addDrops(NonNullList<ItemStack> drops) // FIXME Use drop class
	{
		// This is needed because referencing items in an enum constructor causes an NPE for some reason (it didn't before wtf)
		switch(this)
		{
		case RED: drops.add(new ItemStack(HexItems.hexorium_crystal_red, 8)); return drops;
		case ORANGE: drops.add(new ItemStack(HexItems.hexorium_crystal_red, 6)); drops.add(new ItemStack(HexItems.hexorium_crystal_green, 2)); return drops;
		case YELLOW: drops.add(new ItemStack(HexItems.hexorium_crystal_red, 4)); drops.add(new ItemStack(HexItems.hexorium_crystal_green, 4)); return drops;
		case LIME: drops.add(new ItemStack(HexItems.hexorium_crystal_red, 2)); drops.add(new ItemStack(HexItems.hexorium_crystal_green, 6)); return drops;
		case GREEN: drops.add(new ItemStack(HexItems.hexorium_crystal_green, 8)); return drops;
		case TURQUOISE: drops.add(new ItemStack(HexItems.hexorium_crystal_green, 6)); drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 2)); return drops;
		case CYAN: drops.add(new ItemStack(HexItems.hexorium_crystal_green, 4)); drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 4)); return drops;
		case SKY_BLUE: drops.add(new ItemStack(HexItems.hexorium_crystal_green, 2)); drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 6)); return drops;
		case BLUE: drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 8)); return drops;
		case PURPLE: drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 6)); drops.add(new ItemStack(HexItems.hexorium_crystal_red, 2)); return drops;
		case MAGENTA: drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 4)); drops.add(new ItemStack(HexItems.hexorium_crystal_red, 4)); return drops;
		case PINK: drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 2)); drops.add(new ItemStack(HexItems.hexorium_crystal_red, 6)); return drops;
		case WHITE: drops.add(new ItemStack(HexItems.hexorium_crystal_white, 8)); return drops;
		case LIGHT_GRAY: drops.add(new ItemStack(HexItems.hexorium_crystal_white, 6)); drops.add(new ItemStack(HexItems.hexorium_crystal_black, 2)); return drops;
		case GRAY: drops.add(new ItemStack(HexItems.hexorium_crystal_white, 4)); drops.add(new ItemStack(HexItems.hexorium_crystal_black, 4)); return drops;
		case DARK_GRAY: drops.add(new ItemStack(HexItems.hexorium_crystal_white, 2)); drops.add(new ItemStack(HexItems.hexorium_crystal_black, 6)); return drops;
		case BLACK: drops.add(new ItemStack(HexItems.hexorium_crystal_black, 8)); return drops;
		case RAINBOW: drops.add(new ItemStack(HexItems.hexorium_crystal_red, 2)); drops.add(new ItemStack(HexItems.hexorium_crystal_green, 2)); drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 2)); drops.add(new ItemStack(HexItems.hexorium_crystal_white, 2)); return drops;
		default: return drops;
		}
	}

	/**
	 * Gets the corresponding color by its hex color code
	 */
	public static EHexColor fromColor(int color)
	{
		return COLOR_LOOKUP.get(color);
	}

	/**
	 * Gets the corresponding color by its name
	 */
	public static EHexColor fromName(String name)
	{
		return NAME_LOOKUP.get(name);
	}
}