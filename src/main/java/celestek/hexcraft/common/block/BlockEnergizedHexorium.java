package celestek.hexcraft.common.block;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockEnergizedHexorium extends HexBlock {
	public BlockEnergizedHexorium(EHexColors color) {
		super("energized_hexorium_" + color.name, HexCreativeTabs.tabDecorative, Material.GLASS, color.color, color.texture);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}
}