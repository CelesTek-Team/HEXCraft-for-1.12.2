package celestek.hexcraft.utility;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Drop
{
	public final Item item;
	public final int minimum, maximum;

	public Drop(Item item, int amount)
	{
		this(item, amount, amount);
	}

	public Drop(Item item, int minimum, int maximum)
	{
		this.item = item;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public int getQuantity(Random random, int fortune)
	{
		System.out.println(fortune);
		return fortune + (this.minimum == this.maximum ? this.minimum : this.minimum + random.nextInt(this.maximum - this.minimum + 1));
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