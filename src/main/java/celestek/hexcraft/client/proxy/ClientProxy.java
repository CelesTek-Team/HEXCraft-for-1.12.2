package celestek.hexcraft.client.proxy;

import celestek.hexcraft.client.model.HexModelLoader;
import celestek.hexcraft.common.proxy.IProxy;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class ClientProxy implements IProxy
{
	@Override
	public void preInitialization()
	{
		ModelLoaderRegistry.registerLoader(new HexModelLoader());
	}
}