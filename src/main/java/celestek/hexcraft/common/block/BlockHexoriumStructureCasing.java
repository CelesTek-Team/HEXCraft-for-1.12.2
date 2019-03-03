package celestek.hexcraft.common.block;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHexoriumStructureCasing extends HexBlockConnectedReinforceable
{
	public BlockHexoriumStructureCasing(EHexColors color)
	{
		super("hexorium_structure_casing_" + color.name, HexCreativeTabs.tabDecorative, Material.IRON, color.color, color.texture);
		this.setHardness(1.5F);
		this.setHarvestLevel("pickaxe", 2);
		this.setResistance(6F);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}