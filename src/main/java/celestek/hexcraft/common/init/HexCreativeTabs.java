package celestek.hexcraft.common.init;

import celestek.hexcraft.common.creativetab.TabComponents;
import celestek.hexcraft.common.creativetab.TabDecorative;
import celestek.hexcraft.common.creativetab.TabMachines;
import net.minecraft.creativetab.CreativeTabs;

public final class HexCreativeTabs
{
	public static final CreativeTabs

	tabDecorative = new TabDecorative(),
	tabMachines = new TabMachines(),
	tabComponents = new TabComponents();

	private HexCreativeTabs() {}
}