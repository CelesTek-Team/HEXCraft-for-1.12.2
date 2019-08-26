package celestek.hexcraft.common.block;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.Drop;
import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

//FIXME Add corresponding crystal items to color and then construct a drop instead of passing in one
//FIXME Use getDrops instead of these 3 methods? What's the difference?
public class HexBlockNaturalMonolith extends HexBlockMonolith
{
	public final Drop drop;

	public HexBlockNaturalMonolith(String name, EHexColor color, Drop drop)
	{
		super(name, HexCreativeTabs.components, color);
		this.setHardness(3f);
		this.setResistance(5f);
		this.setHarvestLevel("pickaxe", 2);
		this.drop = drop;
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.drop.item;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return this.drop.getQuantity(random, 0);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return this.drop.getQuantity(random, fortune);
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		return this.drop.getQuantity(ThreadLocalRandom.current(), 0); // Drops as much xp as crystals
	}
}