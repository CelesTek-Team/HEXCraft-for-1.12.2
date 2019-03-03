package celestek.hexcraft.client.model;

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

public class ModelConnectedOverlay extends ModelConnected
{
	protected final ResourceLocation base;

	public ModelConnectedOverlay(ImmutableList<ResourceLocation> textures, ResourceLocation base)
	{
		super(textures);
		this.base = base;
	}

	// FIXME Return missing model if not enough textures are specified
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		ImmutableList.Builder builder = ImmutableList.builder();
		for(ResourceLocation texture : this.textures) builder.add(getter.apply(texture));
		return new BakedModelConnectedOverlay(format, builder.build(), getter.apply(this.base));
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return this.base == null ? this.textures : ImmutableList.<ResourceLocation>builder().addAll(this.textures).add(this.base).build(); // Change this
	}

	@Override
	public IModel retexture(ImmutableMap<String, String> textures)
	{
		if(textures.size() < 48) return this;
		ImmutableList.Builder builder = ImmutableList.builder();
		for(int a = 0; a < 47; ++a) builder.add(new ResourceLocation(textures.get(Integer.toString(a + 1))));
		return new ModelConnectedOverlay(builder.build(), new ResourceLocation(textures.get("base")));
	}
}