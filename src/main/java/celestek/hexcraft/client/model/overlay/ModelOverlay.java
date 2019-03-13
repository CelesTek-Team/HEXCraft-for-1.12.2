package celestek.hexcraft.client.model.overlay;

import java.util.Collection;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class ModelOverlay implements IModel
{
	public static final ResourceLocation MISSING = new ResourceLocation("missingno");

	public static class Layer
	{
		public final ResourceLocation texture;
		public final int tint;
		public final ImmutableSet<BlockRenderLayer> renderLayers;
		public final boolean renderCracks; // Because immutable collections can't handle null
		public final boolean shade;

		public Layer(ResourceLocation texture, int tint, ImmutableSet<BlockRenderLayer> renderLayers, boolean renderCracks, boolean shade)
		{
			this.texture = texture;
			this.tint = tint;
			this.renderLayers = renderLayers;
			this.renderCracks = renderCracks;
			this.shade = shade;
		}

		@Override
		public boolean equals(Object other)
		{
			if(this == other) return true;
			if(other == null || this.getClass() != other.getClass()) return false;
			Layer layer = (Layer) other;
			return this.texture.equals(layer.texture) && this.tint == layer.tint && this.renderCracks == layer.renderCracks && this.shade == layer.shade && this.renderLayers.equals(layer.renderLayers);
		}

		@Override
		public int hashCode()
		{
			int hash = this.texture == null ? 0 : this.texture.hashCode();
			hash = 31 * hash + this.tint;
			hash = 31 * hash + (this.renderLayers == null ? 0 : this.renderLayers.hashCode());
			hash = 31 * hash + (this.renderCracks ? 1 : 0);
			hash = 31 * hash + (this.shade ? 1 : 0);
			return hash;
		}

		public static final String NULL = "NULL";

		public static final String ARGUMENT_PATH = "path", ARGUMENT_TINT = "tint", ARGUMENT_LAYER = "layer", ARGUMENT_SHADE = "shade";

		public static Layer parse(String[] arguments) // Constructor?
		{
			ResourceLocation texture = MISSING;
			int tint = -1;
			ImmutableSet.Builder builder = ImmutableSet.builder().add(BlockRenderLayer.SOLID);
			boolean renderCracks = true;
			boolean shade = true;
			for(String argument : arguments) 
			{
				String[] values = argument.split("=");
				if(values[0].equals(ARGUMENT_PATH)) texture = new ResourceLocation(values[1]);
				else if(values[0].equals(ARGUMENT_TINT)) tint = Integer.parseInt(values[1]);
				else if(values[0].equals(ARGUMENT_LAYER))
				{
					builder = ImmutableSet.builder();
					renderCracks = false;
					String[] elements = values[1].split("/");
					for(String element : elements)
					{
						element = element.toUpperCase();
						if(element.equals(NULL)) renderCracks = true;
						else builder.add(BlockRenderLayer.valueOf(element));
					}
				}
				else if(values[0].equals(ARGUMENT_SHADE)) shade = Boolean.parseBoolean(values[1]);
			}
			return new Layer(texture, tint, builder.build(), renderCracks, shade);
		}
	}

	protected final ImmutableList<Layer> layers;
	protected ResourceLocation particle;

	public ModelOverlay(ImmutableList<Layer> layers, ResourceLocation particle)
	{
		this.layers = layers;
		this.particle = particle;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		ImmutableList.Builder builder = ImmutableList.builder();
		for(Layer layer : this.layers) builder.add(new BakedModelOverlay.Layer(getter.apply(layer.texture), layer.tint, layer.renderLayers, layer.renderCracks, layer.shade));
		return new BakedModelOverlay(state, format, builder.build(), getter.apply(this.particle));
	}

	@Override
	public Collection<ResourceLocation> getTextures() 
	{
		ImmutableList.Builder builder = ImmutableList.builder();
		for(Layer layer : this.layers) builder.add(layer.texture);
		if(this.particle != null) builder.add(this.particle);
		return builder.build();
	}

	public static final String TAG_LAYER = "layer", TAG_PARTICLE = "particle";

	@Override
	public IModel retexture(ImmutableMap<String, String> textures)
	{
		ImmutableList.Builder builder = ImmutableList.builder();
		String tag = TAG_LAYER + "0";
		for(int a = 0; textures.containsKey(tag); ++a)
		{
			builder.add(Layer.parse(textures.get(tag).split(",")));
			tag = TAG_LAYER + (a + 1);
		}
		ImmutableList<Layer> layers = builder.build();
		return new ModelOverlay(layers, textures.containsKey(TAG_PARTICLE) ? new ResourceLocation(textures.get(TAG_PARTICLE)) : layers.isEmpty() ? MISSING : layers.get(0).texture);
	}
}