package celestek.hexcraft;

import celestek.hexcraft.common.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = HexCraft.ID, name = HexCraft.NAME, version = HexCraft.VERSION, acceptedMinecraftVersions = HexCraft.GAMEVERSIONS)
public class HexCraft
{
	public static final String ID = "hexcraft", NAME = "HEXCraft", VERSION = "0.1.0", GAMEVERSIONS = "1.12.2";

	@SidedProxy(clientSide = "celestek.hexcraft.client.proxy.ClientProxy", serverSide = "celestek.hexcraft.server.proxy.ServerProxy")
	public static IProxy proxy;

	@Mod.EventHandler
	public static void preInitialization(FMLPreInitializationEvent event)
	{
		proxy.preInitialization();
	}
}