package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexBlocks;
import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * HEXCraft's base block that provides a special state mapper,, a custom model override and a color handler for tinting
 * All of these features are handled automatically in {@link HexBlocks}
 */
public class HexBlock extends Block { // FIXME Cache the client side stuff?

	public final EHexColor color;

	public HexBlock(String name, CreativeTabs tab, Material material, EHexColor color) {
		super(material);
		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(tab);
		this.color = color;
	}

	/**
	 * The block's special state mapper. Mostly used to map multiple blocks to the same model or ignore certain properties. Can be left empty in which case the block's registry name is used instead
	 */
	@SideOnly(Side.CLIENT)
	public Optional<HexStateMapper> addStateMapper()
	{
		return Optional.empty();
	}

	/**
	 * The block's model override function which wraps or replaces its existing model. Mostly used for fullbright model overrides. Can be left empty to not insert a model
	 */
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride(ResourceLocation path)
	{
		return Optional.empty();
	}

	/**
	 * The block's color handler which is used to add a tint to its model. Can be left empty to not register a color handler
	 */
	@SideOnly(Side.CLIENT)
	public Optional<IBlockColor> addBlockColor()
	{
		return this.color.isSpecial() ? Optional.empty() : Optional.of((state, world, position, tint) -> this.color.color);
	}

	/**
	 * The block's item's color handler which is used to register a tint to its model. Can be left empty to not register a color handler
	 */
	@SideOnly(Side.CLIENT)
	public Optional<IItemColor> addItemColor()
	{
		return this.color.isSpecial() ? Optional.empty() : Optional.of((stack, index) -> this.color.color);
	}
}