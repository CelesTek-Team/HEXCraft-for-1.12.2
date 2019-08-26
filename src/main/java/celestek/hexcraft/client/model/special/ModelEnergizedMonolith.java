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
 * A model which parses a single texture, rotation tags under the "custom" tag which are then converted to a matrix transform and the perspective transform from the blockstate
 */
public class ModelEnergizedMonolith implements IModel
{
	protected final ResourceLocation texture;
	/**
	 * The matrix transform with the parsed rotation tags
	 */
	protected final Optional<TRSRTransformation> transform;

	public ModelEnergizedMonolith(ResourceLocation texture, Optional<TRSRTransformation> transform)
	{
		this.texture = texture;
		this.transform = transform;
	}

	// FIXME Return missing model if not enough textures are specified
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		// Convert the texture path to a sprite and pass it and the matrix and perspective transforms to the baked model
		return new BakedModelEnergizedMonolith(state, format, this.transform, getter.apply(this.texture));
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		// Return saved texture path as a dependency
		return this.texture == null ? ImmutableSet.of() : ImmutableSet.of(this.texture);
	}

	public static final String TAG_ROTATION_X = "rotation_x", TAG_ROTATION_Y = "rotation_y", TAG_ROTATION_Z = "rotation_z";

	@Override
	public IModel process(ImmutableMap<String, String> data)
	{
		// Parse each rotation tag if present
		float rotationX = data.containsKey(TAG_ROTATION_X) ? Float.parseFloat(data.get(TAG_ROTATION_X)) : 0f;
		float rotationY = data.containsKey(TAG_ROTATION_Y) ? Float.parseFloat(data.get(TAG_ROTATION_Y)) : 0f;
		float rotationZ = data.containsKey(TAG_ROTATION_Z) ? Float.parseFloat(data.get(TAG_ROTATION_Z)) : 0f;
		// Convert rotations to a matrix transform if any are present
		return rotationX != 0f || rotationY != 0f || rotationZ != 0f ? new ModelEnergizedMonolith(this.texture, Optional.of(ForgeBlockStateV1.Transforms.convert(0f, 0f, 0f, rotationX, rotationY, rotationZ, 1f))) : this;
	}

	public static final String TEXTURE = "monolith";

	@Override
	public IModel retexture(ImmutableMap<String, String> textures)
	{
		// Save the texture path
		return new ModelEnergizedMonolith(new ResourceLocation(textures.get(TEXTURE)), this.transform);
	}
}