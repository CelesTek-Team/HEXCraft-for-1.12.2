package celestek.hexcraft.common.block;

import java.util.Optional;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColors;
import celestek.hexcraft.utility.HexFilters;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergizedHexorium extends HexBlock {
	public BlockEnergizedHexorium(EHexColors color) {
		super("energized_hexorium_" + color.name, color == EHexColors.RAINBOW ? Optional.empty() : Optional.of(new HexStateMapper("energized_hexorium")), HexCreativeTabs.tabDecorative, Material.GLASS, color.color, HexFilters.ALWAYS_TRUE);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos position, IBlockState state, EntityPlayer player)
	{
		return false;
	}

	// FIXME manipulator compat
	// Store the EHexColor instead?
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos position, IBlockState state, int fortune)
	{
		EHexColors.fromColor(this.color).addDrops(drops);
	}

	@Override
	public boolean enableCache()
	{
		return true;
	}
}