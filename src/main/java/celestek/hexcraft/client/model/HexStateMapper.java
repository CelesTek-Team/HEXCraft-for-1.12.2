package celestek.hexcraft.client.model;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import celestek.hexcraft.HexCraft;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HexStateMapper extends StateMapperBase
{
	public final ResourceLocation model;
	public final List<IProperty> properties;

	public HexStateMapper(String model, IProperty... properties)
	{
		this(new ResourceLocation(HexCraft.ID, model), properties);
	}

	public HexStateMapper(ResourceLocation model, IProperty... properties)
	{
		this.model = model;
		this.properties = Lists.newArrayList(properties);
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state)
	{
		Map< IProperty<?>, Comparable<? >> map = Maps.newLinkedHashMap(state.getProperties());
		for(IProperty property : this.properties) map.remove(property);
		return new ModelResourceLocation(this.model, this.getPropertyString(map));
	}
}