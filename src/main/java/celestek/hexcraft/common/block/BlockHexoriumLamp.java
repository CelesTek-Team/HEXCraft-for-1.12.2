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
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHexoriumLamp extends HexBlockLamp
{
	public BlockHexoriumLamp(EHexColor color)
	{
		super("hexorium_lamp_" + color.name, HexCreativeTabs.decorative, Material.GLASS, color);
		this.setHardness(1.5f);
		this.setResistance(10f);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<HexStateMapper> addStateMapper()
	{
		return Optional.of(new HexStateMapper(this.color.isSpecial() ? "hexorium_lamp_rainbow" : "hexorium_lamp", POWERED));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride(ResourceLocation path)
	{
		return Optional.of(HexUtilities.createFullbrightOverride(HexUtilities.FILTER_CONTAINS_GLOW));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<IBlockColor> addBlockColor()
	{
		return Optional.of((state, world, position, tint) ->
		{
			EHexColor color = this.color.isSpecial() ? EHexColor.WHITE : this.color;
			return state.getValue(POWERED) ? color.color : color.darken(EHexColor.DARKEN_MULTIPLIER);
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<IItemColor> addItemColor()
	{
		return Optional.of((stack, index) ->
		{
			EHexColor color = this.color.isSpecial() ? EHexColor.WHITE : this.color;
			return color.darken(EHexColor.DARKEN_MULTIPLIER);
		});
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
	}
}