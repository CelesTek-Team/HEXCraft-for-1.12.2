package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEngineeredHexoriumBlock extends HexBlockReinforceable
{
	public BlockEngineeredHexoriumBlock(EHexColors color)
	{
		super("engineered_hexorium_block_" + color.name, color == EHexColors.RAINBOW ? Optional.empty() : Optional.of(new HexStateMapper("engineered_hexorium_block")), HexCreativeTabs.tabDecorative, Material.IRON, color.color, color == EHexColors.RAINBOW ? "glow_rainbow" : "glow");
		this.setHardness(1.5F);
		this.setResistance(6F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}