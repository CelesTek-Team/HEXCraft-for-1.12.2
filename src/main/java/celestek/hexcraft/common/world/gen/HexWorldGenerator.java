package celestek.hexcraft.common.world.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class HexWorldGenerator extends WorldGenerator
{
	public boolean enabled;
	public final int amount, heightMin, heightMax;

	public HexWorldGenerator(boolean enabled, int amount, int heightMin, int heightMax)
	{
		this.enabled = enabled;
		this.amount = amount;
		this.heightMin = heightMin;
		this.heightMax = heightMax;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos chunkPosition)
	{
		if(!enabled) return false;
		for(int a = 0; a < this.amount; ++a) this.generateVein(world, random, new BlockPos(chunkPosition.getX() * 16 + 8 + random.nextInt(16), this.heightMin + random.nextInt(this.heightMax - this.heightMin + 1), chunkPosition.getZ() * 16 + 8 + random.nextInt(16)));
		return true;
	}

	protected abstract void generateVein(World world, Random random, BlockPos position);
}