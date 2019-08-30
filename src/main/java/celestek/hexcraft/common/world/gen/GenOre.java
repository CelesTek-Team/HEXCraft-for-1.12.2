package celestek.hexcraft.common.world.gen;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenOre extends HexWorldGenerator
{
	public final IBlockState oreState;
	public final int sizeMin, sizeMax;
	public final Predicate<IBlockState> canReplace;

	public GenOre(boolean enabled, IBlockState oreState, int amount, int sizeMin, int sizeMax, int heightMin, int heightMax, Predicate<IBlockState> canReplace)
	{
		super(enabled, amount, heightMin, heightMax);
		this.oreState = oreState;
		this.sizeMin = sizeMin;
		this.sizeMax = sizeMax;
		this.canReplace = canReplace;
	}

	@Override
	protected void generateVein(World world, Random random, BlockPos position)
	{
		int genSize;
		if(this.sizeMin == this.sizeMax) genSize = this.sizeMax;
		else genSize = this.sizeMin + random.nextInt(this.sizeMax - this.sizeMin + 1);
		// Generate the first vein block.
		IBlockState state = world.getBlockState(position);
		if(state.getBlock().isReplaceableOreGen(state, world, position, this.canReplace)) world.setBlockState(position, this.oreState, 2);
		// Loop the count of additional blocks
		for(int a = 0; a < genSize - 1; ++a)
		{
			// Create a random direction, then perform a move. If the new block is air, move 2 blocks backwards.
			EnumFacing direction = EnumFacing.random(random);
			position = position.offset(direction);
			if(world.isBlockLoaded(position) && world.isAirBlock(position)) position = position.offset(direction.getOpposite(), 2);
			// Generate a vein block.
			state = world.getBlockState(position);
			if (world.isBlockLoaded(position) && state.getBlock().isReplaceableOreGen(state, world, position, this.canReplace)) world.setBlockState(position, this.oreState, 2);
		}
	}
}