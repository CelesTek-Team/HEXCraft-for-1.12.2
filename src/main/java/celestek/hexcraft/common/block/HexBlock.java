package celestek.hexcraft.common.block;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import celestek.hexcraft.HexCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class HexBlock extends Block {
	public final int color;
	public final ImmutableSet<String> textures;

	public HexBlock(String name, CreativeTabs tab, Material material, int color, String... textures) {
		super(material);
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(tab);
		this.color = color;
		Builder<String> builder = ImmutableSet.builder();
		for(String texture : textures) builder.add(HexCraft.ID + ":" + "blocks/" + texture);
		this.textures = builder.build();
	}

	// FIXME Different approach?
	public boolean enableCache()
	{
		return true;
	}
}