package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.utility.Drop;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class BlockHexoriumOre extends HexBlockOre
{
	public BlockHexoriumOre(EHexColors color, Drop drop)
	{
		super("hexorium_ore_" + color.name, Optional.empty(), drop, "hexorium_ore/hexorium_ore_" + color.name + "_gems");
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean enableCache()
	{
		return false;
	}
}