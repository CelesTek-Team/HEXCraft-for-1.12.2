package celestek.hexcraft.common.item;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.common.init.HexItems;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * HEXCraft's base item that provides a custom model override. This feature is handled automatically in {@link HexItems}
 */
public class HexItem extends Item { // FIXME Cache the model override

	public HexItem(String name, CreativeTabs tab) {
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(tab);
	}

	/**
	 * The item's model override function which wraps or replaces its existing model. Mostly used for fullbright model overrides. Can be left empty to not insert a model
	 */
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride()
	{
		return Optional.empty();
	}
}