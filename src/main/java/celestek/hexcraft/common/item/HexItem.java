package celestek.hexcraft.common.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.init.HexItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * HEXCraft's base item that can store a set of textures for the fullbright model This feature is handled automatically in {@link HexItems}
 */
public class HexItem extends Item {
	/**
	 * The item's textures which will have a fullbright effect applied. Can be left empty to not register a fullbright model
	 */
	public final ImmutableSet<String> textures;

	public HexItem(String name, CreativeTabs tab, String... textures) {
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(tab);

		Builder<String> builder = ImmutableSet.builder();
		for(String texture : textures) builder.add(HexCraft.ID + ":" + "items/" + texture);
		this.textures = builder.build();
	}

	/**
	 * Determines whether the attached fullbright model will have cache enabled
	 */
	public boolean enableCache() // FIXME Different approach?
	{
		return true;
	}
}