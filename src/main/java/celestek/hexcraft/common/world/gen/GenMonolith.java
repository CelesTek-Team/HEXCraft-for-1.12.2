package celestek.hexcraft.common.world.gen;

import java.util.Random;

import com.google.common.base.Predicate;

import celestek.hexcraft.common.block.HexBlockRotationFull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenMonolith extends HexWorldGenerator
{
	public final IBlockState monolithState;
	public final int chance;
	public final Predicate<IBlockState> canReplace;

	public GenMonolith(boolean enabled, IBlockState monolithState, int chance, int amount, int heightMin, int heightMax, Predicate<IBlockState> canReplace)
	{
		super(enabled, amount, heightMin, heightMax);
		this.monolithState = monolithState;
		this.chance = chance;
		this.canReplace = canReplace;
	}

	@Override
	protected void generateVein(World world, Random random, BlockPos position)
	{
		// Prepare direction of moving, side of placement and chance of spawning a monolith
		EnumFacing direction = random.nextBoolean() ? EnumFacing.DOWN : EnumFacing.UP;
		EnumFacing side = random.nextBoolean() ? EnumFacing.DOWN : EnumFacing.UP;
		int skip = random.nextInt(101);
		// Proceed only if the result is within the chance threshold.
		if(skip > chance) return;
		do
		{
			IBlockState state = world.getBlockState(position);
			BlockPos adjacentPosition = position.offset(side.getOpposite());
			IBlockState adjacentState = world.getBlockState(adjacentPosition);
			if(!world.isAirBlock(position) || !adjacentState.getBlock().isReplaceableOreGen(adjacentState, world, adjacentPosition, this.canReplace)) position = position.offset(direction);
			else
			{
				world.setBlockState(position, this.monolithState.withProperty(HexBlockRotationFull.FACING, side), 2);
				return;
			}
		}
		while(position.getY() >= this.heightMin && position.getY() <= this.heightMax);
	}
}