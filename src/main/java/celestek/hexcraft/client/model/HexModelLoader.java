package celestek.hexcraft.client.model;

import java.util.Optional;

import com.google.common.collect.ImmutableList;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.client.model.special.ModelDoor;
import celestek.hexcraft.client.model.special.ModelDoubleDoor;
import celestek.hexcraft.client.model.special.ModelLayered;
import celestek.hexcraft.client.model.special.ModelMonolith;
import celestek.hexcraft.client.model.special.connected.ModelConnectedOverlay;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

/**
 * HEXCraft's model loader. Returns a custom model depending on the supplied special tag. These tags are specified under the "model" tag in the blockstate.
 * They aren't physical models, but only exist for the loader to recognize which model should be loaded
 */
public class HexModelLoader implements ICustomModelLoader // Split into separate loaders for different models?
{
	public static final ResourceLocation TEXTURE_MISSING = new ResourceLocation("missingno");

	public static final String
	CONNECTED_OVERLAY = "models/block/special/connected/overlay",
	MONOLITH = "models/block/special/monolith",
	LAYERED_CUBE = "models/block/special/layered_cube",
	DOOR = "models/block/special/door",
	DOUBLE_DOOR = "models/block/special/double_door";

	@Override
	public boolean accepts(ResourceLocation location)
	{
		String path = location.getResourcePath();
		return location.getResourceDomain().equals(HexCraft.ID) && (path.equals(LAYERED_CUBE) || path.equals(CONNECTED_OVERLAY) || path.equals(DOOR) || path.equals(DOUBLE_DOOR) || path.equals(MONOLITH));
	}

	@Override
	public IModel loadModel(ResourceLocation location) throws Exception
	{
		String path = location.getResourcePath();
		if(path.equals(LAYERED_CUBE)) return new ModelLayered(ImmutableList.of(), TEXTURE_MISSING, true);
		else if(path.equals(DOOR)) return new ModelDoor(TEXTURE_MISSING, TEXTURE_MISSING, TEXTURE_MISSING, Optional.empty(), false, false, false);
		else if(path.equals(CONNECTED_OVERLAY)) return new ModelConnectedOverlay(ImmutableList.of(), TEXTURE_MISSING);
		else if(path.equals(DOUBLE_DOOR)) return new ModelDoubleDoor(TEXTURE_MISSING, TEXTURE_MISSING, TEXTURE_MISSING, TEXTURE_MISSING);
		else if(path.equals(MONOLITH)) return new ModelMonolith(TEXTURE_MISSING, Optional.empty());
		return null;
	}

	@Override
	public void onResourceManagerReload(IResourceManager manager) {}
}