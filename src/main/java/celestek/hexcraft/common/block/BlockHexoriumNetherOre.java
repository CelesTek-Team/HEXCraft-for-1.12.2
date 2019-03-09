package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.utility.Drop;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHexoriumNetherOre extends HexBlockOre
{
	public BlockHexoriumNetherOre(EHexColors color, Drop drop)
	{
		super("hexorium_nether_ore_" + color.name, Optional.empty(), drop, "hexorium_ore/hexorium_ore_" + color.name + "_gems");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}