package celestek.hexcraft.client.model;

import com.google.common.collect.ImmutableList;

import celestek.hexcraft.HexCraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class ModelLoaderConnected implements ICustomModelLoader
{
	public static final String PATH_OVERLAY = "models/block/connected/overlay";

	@Override
	public boolean accepts(ResourceLocation location)
	{
		return location.getResourceDomain().equals(HexCraft.ID) && location.getResourcePath().equals(PATH_OVERLAY);
	}

	@Override
	public IModel loadModel(ResourceLocation location) throws Exception
	{
		return new ModelConnectedOverlay(ImmutableList.of(), null);
	}

	@Override
	public void onResourceManagerReload(IResourceManager manager) {}
}