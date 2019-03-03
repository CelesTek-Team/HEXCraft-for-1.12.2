package celestek.hexcraft.common.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import celestek.hexcraft.HexCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class HexItem extends Item {
	public final ImmutableSet<String> textures;

	public HexItem(String name, CreativeTabs tab, String... textures) {
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(tab);
		Builder<String> builder = ImmutableSet.builder();
		for(String texture : textures) builder.add(HexCraft.ID + ":" + "items/" + texture);
		this.textures = builder.build();
	}

	// FIXME Different approach?
	public boolean enableCache()
	{
		return true;
	}
}