package celestek.hexcraft.client.model.special.connected;

import java.util.Collection;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

/**
 * A model which functions as {@link ModelConnected}, but also parses an extra texture from the blockstate
 */
public class ModelConnectedOverlay extends ModelConnected
{
	protected final ResourceLocation base;

	public ModelConnectedOverlay(ImmutableList<ResourceLocation> textures, ResourceLocation base)
	{
		super(textures);
		this.base = base;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		ImmutableList.Builder builder = ImmutableList.builder();
		// Convert all the stored texture paths to sprites and pass them to the baked model
		for(ResourceLocation texture : this.textures) builder.add(getter.apply(texture));
		// Also convert and pass the base texture path to baked model
		return new BakedModelConnectedOverlay(format, builder.build(), getter.apply(this.base));
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		// Return all the saved texture paths as dependencies
		return this.base == null ? this.textures : ImmutableList.<ResourceLocation>builder().addAll(this.textures).add(this.base).build(); // Change this
	}

	@Override
	public IModel retexture(ImmutableMap<String, String> textures)
	{
		ImmutableList.Builder builder = ImmutableList.builder();
		// Save all the texture paths mapped to numbers 1 - 47 in order
		for(int a = 0; a < 47; ++a) builder.add(new ResourceLocation(textures.get(Integer.toString(a + 1))));
		// Also save the "base" texture path
		return new ModelConnectedOverlay(builder.build(), new ResourceLocation(textures.get("base")));
	}
}