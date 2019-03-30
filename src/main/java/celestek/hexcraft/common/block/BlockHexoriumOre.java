package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.utility.Drop;
import celestek.hexcraft.utility.EHexColor;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHexoriumOre extends HexBlockOre
{
	public BlockHexoriumOre(EHexColor color, Drop drop)
	{
		super("hexorium_ore_" + color.name, drop);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride(ResourceLocation path)
	{
		return Optional.of(HexUtilities.createFullbrightOverride(HexUtilities.createFilter("hexorium_ore_gems")));
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT;
	}
}