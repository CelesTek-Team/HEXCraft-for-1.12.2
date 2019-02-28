package celestek.hexcraft.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHexoriumCrystal extends HexBlockPillar
{
	public BlockHexoriumCrystal(String name, int color, String... textures)
	{
		super(name, Material.GLASS, color, textures);
		this.setSoundType(SoundType.GLASS);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(5F);
		this.setResistance(10F);
	}
}