package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.utility.Drop;
import celestek.hexcraft.utility.EHexColor;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHexoriumNetherMonolith extends HexBlockNaturalMonolith
{
	public BlockHexoriumNetherMonolith(EHexColor color, Drop drop)
	{
		super("hexorium_nether_monolith_" + color.name, color, drop);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<HexStateMapper> addStateMapper()
	{
		return this.color.isSpecial() ? Optional.empty() : Optional.of(new HexStateMapper("hexorium_nether_monolith"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride(ResourceLocation path)
	{
		return Optional.of(HexUtilities.createFullbrightOverride(HexUtilities.FILTER_TRUE));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}