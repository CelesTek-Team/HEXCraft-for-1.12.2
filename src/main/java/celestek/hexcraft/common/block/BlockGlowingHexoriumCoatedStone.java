package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class BlockGlowingHexoriumCoatedStone extends HexBlockConnectedReinforceable
{
	public BlockGlowingHexoriumCoatedStone(EHexColors color)
	{
		super("glowing_hexorium_coated_stone_" + color.name, color == EHexColors.RAINBOW ? Optional.empty() : Optional.of(new HexStateMapper("glowing_hexorium_coated_stone")), HexCreativeTabs.tabDecorative, Material.IRON, color.color, Optional.of((string) -> string.endsWith("glow") || string.endsWith("rainbow")));
		this.setHardness(1.5F);
		this.setResistance(6F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
	}
}