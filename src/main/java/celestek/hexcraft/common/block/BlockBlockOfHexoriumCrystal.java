package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColor;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBlockOfHexoriumCrystal extends HexBlockPillar
{
	public BlockBlockOfHexoriumCrystal(EHexColor color)
	{
		super("block_of_hexorium_crystal_" + color.name, HexCreativeTabs.tabDecorative, Material.GLASS, EHexColor.DIMMED);
		this.setHardness(5F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride(ResourceLocation path)
	{
		return Optional.of(HexUtilities.createFullbrightOverride(HexUtilities.FILTER_TRUE, true));
	}
}