package celestek.hexcraft.common.item;

import java.util.Optional;
import java.util.function.Predicate;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.init.HexItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * HEXCraft's base item that stores a texture filter for the fullbright model. This feature is handled automatically in {@link HexItems}
 */
public class HexItem extends Item {
	/**
	 * The item's texture filter which determines the textures to apply the fullbright effect. Can be left empty to not register a fullbright model
	 */
	public final Optional<Predicate<String>> filter;

	public HexItem(String name, CreativeTabs tab, Optional<Predicate<String>> filter) {
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(tab);

		this.filter = filter;
	}

	/**
	 * Determines whether the attached fullbright model will have cache enabled
	 */
	public boolean enableCache() // FIXME Different approach?
	{
		return true;
	}
}