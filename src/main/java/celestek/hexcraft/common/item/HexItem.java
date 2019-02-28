package celestek.hexcraft.common.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.item.Item;

public class HexItem extends Item {
	public final ImmutableSet<String> textures;

	public HexItem(String name, String... textures) {
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(HexCreativeTabs.tab);
		Builder<String> builder = ImmutableSet.builder();
		for(String texture : textures) builder.add(HexCraft.ID + ":" + "items/" + texture);
		this.textures = builder.build();
	}
}