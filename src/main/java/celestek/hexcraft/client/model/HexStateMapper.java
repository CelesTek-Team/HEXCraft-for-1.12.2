package celestek.hexcraft.client.model;

import java.util.Map;

import com.google.common.collect.Maps;

import celestek.hexcraft.HexCraft;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * HEXCraft's special state mapper. It is capable of renaming a variant's model path to the supplied one and removing the supplied properties from it
 */
@SideOnly(Side.CLIENT)
public class HexStateMapper extends StateMapperBase
{
	/**
	 * All the possible model paths that can be used
	 */
	public final ResourceLocation[] paths;
	/**
	 * All the properties which should be removed
	 */
	public final IProperty[] properties;

	public HexStateMapper(String model, IProperty... properties)
	{
		this(new ResourceLocation[] { new ResourceLocation(HexCraft.ID, model) }, properties);
	}

	protected HexStateMapper(ResourceLocation[] paths, IProperty... properties)
	{
		this.paths = paths;
		this.properties = properties;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state)
	{
		Map<IProperty<?>, Comparable<? >> map = Maps.newLinkedHashMap(state.getProperties());
		// Remove all the supplied properties
		for(IProperty property : this.properties) map.remove(property);
		// Rename the variant's model path
		return new ModelResourceLocation(this.getPathForState(state), this.getPropertyString(map));
	}

	/**
	 * Returns the model path which should be used for the given state
	 */
	public ResourceLocation getPathForState(IBlockState state)
	{
		return this.paths[0];
	}

	/**
	 * Returns the model path which should be used for the given item block
	 */
	public ResourceLocation getPathForItem(ItemBlock item)
	{
		return this.paths[0];
	}
}