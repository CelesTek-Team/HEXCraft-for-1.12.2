package celestek.hexcraft.common.item;

import celestek.hexcraft.common.creativetab.HexCreativeTabs;
import celestek.hexcraft.utility.HexProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHexoriumReinforcer extends HexItem
{
	public ItemHexoriumReinforcer()
	{
		super("hexorium_reinforcer", HexCreativeTabs.tabMachines);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos position, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(position);
		if(!state.getPropertyKeys().contains(HexProperties.REINFORCED) || state.getValue(HexProperties.REINFORCED)) return EnumActionResult.PASS;
		if(!player.isCreative()) player.getHeldItem(hand).shrink(1);
		world.setBlockState(position, state.withProperty(HexProperties.REINFORCED, true));
		return EnumActionResult.SUCCESS;
	}
}