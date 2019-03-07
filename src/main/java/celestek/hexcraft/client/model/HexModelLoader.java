package celestek.hexcraft.client.model;

import java.util.Optional;

import com.google.common.collect.ImmutableList;

import celestek.hexcraft.HexCraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class HexModelLoader implements ICustomModelLoader
{
	public static final String
	CONNECTED_OVERLAY = "models/block/connected/overlay",
	MONOLITH = "models/block/special/monolith";

	@Override
	public boolean accepts(ResourceLocation location)
	{
		String path = location.getResourcePath();
		return location.getResourceDomain().equals(HexCraft.ID) && (path.equals(CONNECTED_OVERLAY) || path.equals(MONOLITH));
	}

	@Override
	public IModel loadModel(ResourceLocation location) throws Exception
	{
		String path = location.getResourcePath();
		if(path.equals(CONNECTED_OVERLAY)) return new ModelConnectedOverlay(ImmutableList.of(), null);
		else if(path.equals(MONOLITH)) return new ModelMonolith(null, Optional.empty());
		return null;
	}

	@Override
	public void onResourceManagerReload(IResourceManager manager) {}
}