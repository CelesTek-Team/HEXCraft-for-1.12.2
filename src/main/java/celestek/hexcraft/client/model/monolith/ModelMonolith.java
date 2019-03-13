package celestek.hexcraft.client.model.monolith;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class ModelMonolith implements IModel
{
	protected final ResourceLocation texture;
	protected final Optional<TRSRTransformation> transform;

	public ModelMonolith(ResourceLocation texture, Optional<TRSRTransformation> transform)
	{
		this.texture = texture;
		this.transform = transform;
	}

	// FIXME Return missing model if not enough textures are specified
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		return new BakedModelMonolith(state, format, this.transform, getter.apply(this.texture));
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return this.texture == null ? ImmutableList.of() : ImmutableList.of(this.texture);
	}

	public static final String TAG_ROTATION_X = "rotation_x", TAG_ROTATION_Y = "rotation_y", TAG_ROTATION_Z = "rotation_z";

	@Override
	public IModel process(ImmutableMap<String, String> data)
	{
		if(!data.containsKey(TAG_ROTATION_X) && !data.containsKey(TAG_ROTATION_Y) && !data.containsKey(TAG_ROTATION_Z)) return this;
		float rotationX = data.containsKey(TAG_ROTATION_X) ? Float.parseFloat(data.get(TAG_ROTATION_X)) : 0f;
		float rotationY = data.containsKey(TAG_ROTATION_Y) ? Float.parseFloat(data.get(TAG_ROTATION_Y)) : 0f;
		float rotationZ = data.containsKey(TAG_ROTATION_Z) ? Float.parseFloat(data.get(TAG_ROTATION_Z)) : 0f;
		return new ModelMonolith(this.texture, Optional.of(ForgeBlockStateV1.Transforms.convert(0f, 0f, 0f, rotationX, rotationY, rotationZ, 1f)));
	}

	public static final String TAG_TEXTURE = "base";

	@Override
	public IModel retexture(ImmutableMap<String, String> textures)
	{
		return new ModelMonolith(new ResourceLocation(textures.get(TAG_TEXTURE)), this.transform);
	}
}