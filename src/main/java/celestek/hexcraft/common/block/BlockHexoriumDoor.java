package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import celestek.hexcraft.utility.HexFilters;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHexoriumDoor extends HexBlockDoor
{
	/**
	 * A special state mapper that functions as {@link HexStateMapper}, but also adds a certain suffix to the renamed model path depending on the door's half property
	 */
	@SideOnly(Side.CLIENT)
	public static class StateMapper extends HexStateMapper
	{
		public StateMapper(String path, IProperty... properties)
		{
			super(new ResourceLocation[] { new ResourceLocation(HexCraft.ID, path + "_bottom"), new ResourceLocation(HexCraft.ID, path + "_top") }, properties);
		}

		@Override
		public ResourceLocation getPathForState(IBlockState state)
		{
			return state.getValue(HALF) == EnumDoorHalf.LOWER ? this.paths[0] : this.paths[1];
		}

		@Override
		public ResourceLocation getPathForItem(ItemBlock item)
		{
			return this.paths[0];
		}
	}

	public BlockHexoriumDoor(EHexColors color)
	{
		super("hexorium_door_" + color.name, Optional.of(new StateMapper(color == EHexColors.RAINBOW ? "hexorium_door_rainbow" : "hexorium_door", HALF)), HexCreativeTabs.tabDecorative, Material.IRON, color.color,HexFilters.CONTAINS_GLOW);
		this.setHardness(1.5F);
		this.setResistance(6F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.METAL);
		this.setLightOpacity(0);
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
	}
}