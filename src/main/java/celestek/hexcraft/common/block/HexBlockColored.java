package celestek.hexcraft.common.block;

import net.minecraft.block.material.Material;

public class HexBlockColored extends HexBlock {
	public final int color;

	public HexBlockColored(String name, Material material, int color, String model)	{
		super(name, material, model);
		this.color = color;
	}
}