package celestek.hexcraft;

import java.io.File;

import celestek.hexcraft.common.init.HexConfigs;
import celestek.hexcraft.common.init.HexWorldGen;
import celestek.hexcraft.common.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = HexCraft.ID, name = HexCraft.NAME, version = HexCraft.VERSION, acceptedMinecraftVersions = HexCraft.GAMEVERSIONS)
public class HexCraft
{
	public static final String ID = "hexcraft", NAME = "HEXCraft", VERSION = "0.1.0", GAMEVERSIONS = "1.12.2";

	@SidedProxy(clientSide = "celestek.hexcraft.client.proxy.ClientProxy", serverSide = "celestek.hexcraft.server.proxy.ServerProxy")
	protected static IProxy proxy;

	@Mod.EventHandler
	public static void preInitialization(FMLPreInitializationEvent event)
	{
		HexConfigs.mainDir = new File(event.getModConfigurationDirectory(), ID);
		HexConfigs.dimDir = new File(HexConfigs.mainDir, "dimensions");
		HexConfigs.load();
		GameRegistry.registerWorldGenerator(new HexWorldGen(), 0);
		proxy.preInitialization();
	}
}