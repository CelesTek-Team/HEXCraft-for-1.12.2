package celestek.hexcraft.client.proxy;

import celestek.hexcraft.client.model.HexModelLoader;
import celestek.hexcraft.common.proxy.ACommonProxy;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class ClientProxy extends ACommonProxy
{
	@Override
	public void preInitialization()
	{
		ModelLoaderRegistry.registerLoader(new HexModelLoader());
	}
}