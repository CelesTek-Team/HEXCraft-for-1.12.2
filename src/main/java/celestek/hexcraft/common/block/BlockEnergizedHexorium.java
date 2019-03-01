package celestek.hexcraft.common.block;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockEnergizedHexorium extends HexBlock {
	public BlockEnergizedHexorium(String name, int color, String model) {
		super(name, HexCreativeTabs.tabDecorative, Material.GLASS, color, model);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}
}