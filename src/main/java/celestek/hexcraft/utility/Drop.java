package celestek.hexcraft.utility;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * A small utility class which is used to generate random drops of a specified item type
 */
public class Drop
{
	public final Item item;
	public final int minimum, maximum, fortuneMultiplier;

	public Drop(Item item, int amount, int fortuneMultiplier)
	{
		this(item, amount, amount, fortuneMultiplier);
	}

	public Drop(Item item, int minimum, int maximum, int fortuneMultiplier)
	{
		this.item = item;
		this.minimum = minimum;
		this.maximum = maximum;
		this.fortuneMultiplier = fortuneMultiplier;
	}

	public int getQuantity(Random random, int fortune)
	{
		return fortune * this.fortuneMultiplier + (this.minimum == this.maximum ? this.minimum : this.minimum + random.nextInt(this.maximum - this.minimum + 1));
	}

	public ItemStack createStack(Random random, int fortune)
	{
		return new ItemStack(this.item, this.getQuantity(random, fortune));
	}

	public ItemStack createStack()
	{
		return this.createStack(ThreadLocalRandom.current(), 0);
	}
}