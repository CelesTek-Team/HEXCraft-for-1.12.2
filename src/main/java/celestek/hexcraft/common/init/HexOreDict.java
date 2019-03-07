package celestek.hexcraft.common.init;

import net.minecraftforge.oredict.OreDictionary;

public final class HexOreDict
{
	private HexOreDict() {}

	public static void register()
	{
		OreDictionary.registerOre("gemHexoriumRed", HexItems.hexorium_crystal_red);
		OreDictionary.registerOre("gemHexoriumGreen", HexItems.hexorium_crystal_green);
		OreDictionary.registerOre("gemHexoriumBlue", HexItems.hexorium_crystal_blue);
		OreDictionary.registerOre("gemHexoriumWhite", HexItems.hexorium_crystal_white);
		OreDictionary.registerOre("gemHexoriumBlack", HexItems.hexorium_crystal_black);
	}
}