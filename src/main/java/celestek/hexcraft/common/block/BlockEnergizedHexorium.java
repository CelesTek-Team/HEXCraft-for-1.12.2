package celestek.hexcraft.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockEnergizedHexorium extends HexBlockColored
{
	public BlockEnergizedHexorium(String name, int color)
	{
		super(name, Material.GLASS, color);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}
}