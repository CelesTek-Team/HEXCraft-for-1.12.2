package celestek.hexcraft.client.model.special;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * A model which parses 3 textures, extra tags: "flip" and "edge" under the "custom" tag in the blockstate and generates a matrix transform based on these tags
 */
public class ModelDoor implements IModel
{
	protected final ResourceLocation base, front, side;
	/**
	 * The matrix transform based on the parsed tags
	 */
	protected final Optional<TRSRTransformation> transform;
	protected final int flip, edge;

	public ModelDoor(ResourceLocation base, ResourceLocation front, ResourceLocation side, Optional<TRSRTransformation> transform, int flip, int edge)
	{
		this.base = base;
		this.front = front;
		this.side = side;
		this.transform = transform;
		this.flip = flip;
		this.edge = edge;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		// Convert the saved texture paths to sprites and pass them, the generated matrix and perspective transforms and the parsed custom tags to the baked model
		return new BakedModelDoor(state, format, getter.apply(this.base), getter.apply(this.front), getter.apply(this.side), this.transform, this.flip, this.edge);
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		// Return all the saved texture paths as dependencies
		ImmutableSet.Builder builder = ImmutableSet.builder();
		if(this.base != null) builder.add(this.base);
		if(this.front != null) builder.add(this.front);
		if(this.side != null) builder.add(this.side);
		return builder.build();
	}

	public static final String TAG_FLIP = "flip", TAG_EDGE = "edge", TAG_ROTATION_X = "rotation_x", TAG_ROTATION_Y = "rotation_y", TAG_ROTATION_Z = "rotation_z";

	@Override
	public IModel process(ImmutableMap<String, String> data)
	{
		// Parse each tag if present
		float rotationX = data.containsKey(TAG_ROTATION_X) ? Float.parseFloat(data.get(TAG_ROTATION_X)) : 0f;
		float rotationY = data.containsKey(TAG_ROTATION_Y) ? Float.parseFloat(data.get(TAG_ROTATION_Y)) : 0f;
		float rotationZ = data.containsKey(TAG_ROTATION_Z) ? Float.parseFloat(data.get(TAG_ROTATION_Z)) : 0f;
		int flip = data.containsKey(TAG_FLIP) ? Integer.parseInt(data.get(TAG_FLIP)) : this.flip;
		int edge = data.containsKey(TAG_EDGE) ? Integer.parseInt(data.get(TAG_EDGE)) : this.edge;
		// Set the matrix transform and tags if any are present
		return rotationX != 0f || rotationY != 0f || rotationZ != 0f || this.flip != flip || this.edge != edge ? new ModelDoor(this.base, this.front, this.side, Optional.of(ForgeBlockStateV1.Transforms.convert(0f, 0f, 0f, rotationX, rotationY, rotationZ, 1f)), flip, edge) : this;
	}

	public static final String TEXTURE_BASE = "base", TEXTURE_FRONT = "front", TEXTURE_SIDE = "side";

	@Override
	public IModel retexture(ImmutableMap<String, String> textures) // FIXME Use missing textures if any are missing
	{
		// Save all the texture paths
		return new ModelDoor(new ResourceLocation(textures.get(TEXTURE_BASE)), new ResourceLocation(textures.get(TEXTURE_FRONT)), new ResourceLocation(textures.get(TEXTURE_SIDE)), this.transform, this.flip, this.edge);
	}
}