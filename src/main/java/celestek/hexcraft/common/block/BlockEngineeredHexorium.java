package celestek.hexcraft.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEngineeredHexorium extends HexBlockReinforceable
{
	public BlockEngineeredHexorium(String name, int color, String... textures)
	{
		super(name, Material.IRON, color, textures);
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