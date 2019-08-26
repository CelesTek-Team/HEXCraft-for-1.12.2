package celestek.hexcraft.common.item;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColor;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHexoriumCrystal extends HexItem
{
	public ItemHexoriumCrystal(EHexColor color)
	{
		super("hexorium_crystal_" + color.name, HexCreativeTabs.components);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride()
	{
		return Optional.of(HexUtilities.createFullbrightOverride(HexUtilities.createFilter(this.getRegistryName().getResourcePath())));
	}
}