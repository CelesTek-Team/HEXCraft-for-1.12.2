package celestek.hexcraft.common.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class HexRecipes
{
	private HexRecipes() {}

	public static void register()
	{
		GameRegistry.addSmelting(HexItems.hexorium_glass_package, new ItemStack(HexBlocks.tempered_hexorium_glass, 4), 0.1f);
	}
}