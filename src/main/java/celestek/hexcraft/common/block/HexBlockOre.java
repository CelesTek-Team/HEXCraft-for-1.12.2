package celestek.hexcraft.common.block;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.Drop;
import celestek.hexcraft.utility.EHexColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * An ore block which drops a random amount of the specified item and experience
 */
public class HexBlockOre extends HexBlock
{
	public final Drop drop;

	public HexBlockOre(String name, Drop drop)
	{
		super(name, HexCreativeTabs.components, Material.ROCK, EHexColor.DIMMED);
		this.setHardness(3F);
		this.setResistance(5F);
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.STONE);
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