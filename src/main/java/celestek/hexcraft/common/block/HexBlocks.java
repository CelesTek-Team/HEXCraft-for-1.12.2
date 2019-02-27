package celestek.hexcraft.common.block;

import celestek.hexcraft.utility.HexColors;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HexBlocks
{
	public static final Block
	energized_hexorium_red = new BlockEnergizedHexorium("energized_hexorium_red", HexColors.RED);

	private HexBlocks() {}

	public static void register(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(energized_hexorium_red);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
	}
}