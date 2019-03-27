package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColor;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlowingHexoriumCoatedStone extends HexBlockConnectedReinforceable
{
	public BlockGlowingHexoriumCoatedStone(EHexColor color)
	{
		super("glowing_hexorium_coated_stone_" + color.name, HexCreativeTabs.tabDecorative, Material.IRON, color);
		this.setHardness(1.5F);
		this.setResistance(6F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<HexStateMapper> addStateMapper()
	{
		return this.color == EHexColor.RAINBOW ? Optional.empty() : Optional.of(new HexStateMapper("glowing_hexorium_coated_stone"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride(ResourceLocation path)
	{
		return Optional.of(HexUtilities.createFullbrightOverride(string -> string.endsWith("glow") || string.endsWith("rainbow"), false));
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
	}
}