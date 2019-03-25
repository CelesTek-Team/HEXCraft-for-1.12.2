package celestek.hexcraft.client.model.special.connected;

import java.util.Collection;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import celestek.hexcraft.client.model.HexModelLoader;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

/**
 * A model which parses so called {@link Layer}s containing all the the parameters used for rendering under the "textures" tag in the blockstate
 */
public class ModelConnectedLayered implements IModel
{
	/**
	 * A wrapper which contains a list of texture paths for each possible connection, a tint layer, the render layers in which it should render and configurable shading
	 */
	public static class Layer
	{
		/**
		 * A list of 47 texture paths for each possible connection. If there aren't enough textures, the first one is used by default
		 */
		public final ImmutableList<ResourceLocation> textures;
		public final int tint;
		public final ImmutableSet<BlockRenderLayer> renderLayers;
		/**
		 * Effectively determines if this layer should render in the null render layer. This is required because guava's immutable API does not support null values
		 */
		public final boolean renderCracks;
		public final boolean shade;

		public Layer(ImmutableList<ResourceLocation> textures, int tint, ImmutableSet<BlockRenderLayer> renderLayers, boolean renderCracks, boolean shade)
		{
			this.textures = textures;
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
			return this.tint == layer.tint && this.renderCracks == layer.renderCracks && this.shade == layer.shade && this.renderLayers.equals(layer.renderLayers) && this.textures.equals(layer.textures);
		}

		@Override
		public int hashCode()
		{
			int hash = this.textures == null ? 0 : this.textures.hashCode();
			hash = 31 * hash + this.tint;
			hash = 31 * hash + (this.renderLayers == null ? 0 : this.renderLayers.hashCode());
			hash = 31 * hash + (this.renderCracks ? 1 : 0);
			hash = 31 * hash + (this.shade ? 1 : 0);
			return hash;
		}

		public static final String NULL = "NULL";

		public static final String PARAMETER_PATHS = "paths", PARAMETER_TINT = "tint", PARAMETER_LAYER = "layers", PARAMETER_SHADE = "shade";

		/**
		 * Returns a new layer with the parameters parsed from the given string.
		 * Parameters are set by specifying the name of the parameter followed by = and the desired value. Parameters are separated by commas. Multiple values can be specified by separating them with ;.
		 * If a parameter is not specified, the default value will be used.
		 * The currently implemented parameters are:
		 * <ul>
		 * <li> paths - texture paths for all possible connections, but can have less. Defaults to a missing texture </li>
		 * <li> tint - the tint index for all the layer's quads. Defaults to -1 </li>
		 * <li> layers - the render layers in which this layer should render in. Defaults to solid and null</li>
		 * <li> shade - determines if the all the layer's quads should have diffuse lighting. Defaults to true </li>
		 * </ul>
		 */
		public static Layer parse(String string) // FIXME Replace paths with numbers from 0-47
		{
			// Remove all whitespace and split all the name-value pairs into their own strings
			String[] parameters = string.replaceAll("\\s", "").split(",");
			// Set all the default parameters
			ImmutableList.Builder textures = ImmutableList.builder().add(HexModelLoader.TEXTURE_MISSING);
			int tint = -1;
			ImmutableSet.Builder renderLayers = ImmutableSet.builder().add(BlockRenderLayer.SOLID);
			boolean renderCracks = true;
			boolean shade = true;

			for(String parameter : parameters)
			{
				// Split the name-value pair into two strings
				String[] pair = parameter.split("=");
				String name = pair[0];
				String value = pair[1];
				// Parse the value depending on the parameter name
				if(name.equals(PARAMETER_PATHS))
				{
					textures = ImmutableList.builder();
					// Split all the values into their own strings
					String values[] = value.split(";");
					for(String value1 : values) textures.add(new ResourceLocation(value1));
				}
				else if(name.equals(PARAMETER_TINT)) tint = Integer.parseInt(value);
				else if(name.equals(PARAMETER_LAYER))
				{
					// Clear the stored layers to override the defaults
					renderLayers = ImmutableSet.builder();
					renderCracks = false;
					// Split all the values into their own strings
					String[] values = value.split(";");
					for(String value1 : values)
					{
						value1 = value1.toUpperCase();
						if(value1.equals(NULL)) renderCracks = true;
						else renderLayers.add(BlockRenderLayer.valueOf(value1));
					}
				}
				else if(name.equals(PARAMETER_SHADE)) shade = Boolean.parseBoolean(value);
			}
			// Build the layer with the parsed parameters
			return new Layer(textures.build(), tint, renderLayers.build(), renderCracks, shade);
		}
	}

	protected final ImmutableList<Layer> layers;
	protected final ResourceLocation particle;
	protected final boolean ambientOcclusion;

	public ModelConnectedLayered(ImmutableList<Layer> layers, ResourceLocation particle, boolean ambientOcclusion)
	{
		this.layers = layers;
		this.particle = particle;
		this.ambientOcclusion = ambientOcclusion;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> getter)
	{
		ImmutableList.Builder layers = ImmutableList.builder();
		// Convert all the texture paths from each layer to sprites and pass the new layers to the baked model
		for(Layer layer : this.layers)
		{
			ImmutableList.Builder sprites = ImmutableList.builder();
			for(ResourceLocation texture : layer.textures) sprites.add(getter.apply(texture));
			layers.add(new BakedModelConnectedLayered.Layer(sprites.build(), layer.tint, layer.renderLayers, layer.renderCracks, layer.shade));
		}
		// Also convert and pass the particle texture and set ambient occlusion
		return new BakedModelConnectedLayered(format, this.ambientOcclusion, layers.build(), getter.apply(this.particle));
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		// Return all the saved layers' texture paths as dependencies
		ImmutableSet.Builder builder = ImmutableSet.builder();
		for(Layer layer : this.layers) builder.addAll(layer.textures);
		if(this.particle != null) builder.add(this.particle);
		return builder.build();
	}

	public static final String TAG_LAYER = "layer", TAG_PARTICLE = "particle";

	@Override
	public IModel retexture(ImmutableMap<String, String> textures)
	{
		ImmutableList.Builder builder = ImmutableList.builder();
		// Parse all the layers mapped to numbers starting with 0 in order
		String tag = TAG_LAYER + "0";
		for(int a = 0; textures.containsKey(tag); ++a)
		{
			Layer layer = Layer.parse(textures.get(tag));
			builder.add(layer);
			tag = TAG_LAYER + (a + 1);
		}
		// Also save the particle texture if present
		return new ModelConnectedLayered(builder.build(), textures.containsKey(TAG_PARTICLE) ? new ResourceLocation(textures.get(TAG_PARTICLE)) : HexModelLoader.TEXTURE_MISSING, this.ambientOcclusion);
	}

	@Override
	public IModel smoothLighting(boolean value)
	{
		return this.ambientOcclusion == value ? this : new ModelConnectedLayered(this.layers, this.particle, value);
	}
}