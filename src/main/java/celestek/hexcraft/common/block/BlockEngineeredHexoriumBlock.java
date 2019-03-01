package celestek.hexcraft.common.block;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEngineeredHexoriumBlock extends HexBlockReinforceable
{
	public BlockEngineeredHexoriumBlock(String name, int color, String... textures)
	{
		super(name, HexCreativeTabs.tabDecorative, Material.IRON, color, textures);
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