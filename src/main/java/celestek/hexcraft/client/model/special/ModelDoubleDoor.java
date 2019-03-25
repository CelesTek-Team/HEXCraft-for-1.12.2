package celestek.hexcraft.client.model.special;

import java.util.Collection;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

/**
 * A model which parses 4 textures from the blockstate
 */
public class ModelDoubleDoor implements IModel
{
	protected final ResourceLocation base, bottom, top, side;

	public ModelDoubleDoor(ResourceLocation base, ResourceLocation bottom, ResourceLocation top, ResourceLocation side)
	{
		this.base = base;
		this.bottom = bottom;
		this.top = top;
		this.side = side;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		// Convert all the stored texture paths to sprites and pass them and the perspective transform to the baked model
		return new BakedModelDoubleDoor(state, format, getter.apply(this.base), getter.apply(this.bottom), getter.apply(this.top), getter.apply(this.side));
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		// Return all the saved texture paths as dependencies
		ImmutableSet.Builder builder = ImmutableSet.builder();
		if(this.base != null) builder.add(this.base);
		if(this.bottom != null) builder.add(this.bottom);
		if(this.top != null) builder.add(this.top);
		if(this.side != null) builder.add(this.side);
		return builder.build();
	}

	public static final String TEXTURE_BASE = "base", TEXTURE_BOTTOM = "bottom", TEXTURE_TOP = "top", TEXTURE_SIDE = "side";

	@Override
	public IModel retexture(ImmutableMap<String, String> textures) // FIXME Use missing textures if any are missing
	{
		// Save all the texture paths
		return new ModelDoubleDoor(new ResourceLocation(textures.get(TEXTURE_BASE)), new ResourceLocation(textures.get(TEXTURE_BOTTOM)), new ResourceLocation(textures.get(TEXTURE_TOP)), new ResourceLocation(textures.get(TEXTURE_SIDE)));
	}
}