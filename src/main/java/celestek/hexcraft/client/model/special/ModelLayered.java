package celestek.hexcraft.client.model.special;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import celestek.hexcraft.client.model.HexModelLoader;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * A model which parses so called {@link Layer}s containing all the the parameters used for rendering under the "textures" tag in the blockstate
 */
public class ModelLayered implements IModel
{
	/**
	 * A wrapper which contains a texture path for each face, a tint layer, the render layers in which it should render and configurable shading
	 */
	public static class Layer
	{
		public final ImmutableMap<EnumFacing, ResourceLocation> textures;
		/**
		 * The texture path which is used for faces without a specified texture or a null face. This is required because guava's immutable API does not support null values
		 */
		public final ResourceLocation texture;
		public final int tint;
		public final ImmutableSet<BlockRenderLayer> renderLayers;
		/**
		 * Effectively determines if this layer should render in the null render layer. This is required, again, because guava's immutable API does not support null values
		 */
		public final boolean renderCracks;
		public final boolean shade;

		public Layer(ImmutableMap<EnumFacing, ResourceLocation> textures, ResourceLocation texture, int tint, ImmutableSet<BlockRenderLayer> renderLayers, boolean renderCracks, boolean shade)
		{
			this.textures = textures;
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
			return this.tint == layer.tint && this.renderCracks == layer.renderCracks && this.shade == layer.shade && this.texture.equals(layer.texture) && this.renderLayers.equals(layer.renderLayers) && this.textures.equals(layer.textures);
		}

		@Override
		public int hashCode()
		{
			int hash = this.textures == null ? 0 : this.textures.hashCode();
			hash = 31 * hash + (this.texture == null ? 0 : this.texture.hashCode());
			hash = 31 * hash + this.tint;
			hash = 31 * hash + (this.renderLayers == null ? 0 : this.renderLayers.hashCode());
			hash = 31 * hash + (this.renderCracks ? 1 : 0);
			hash = 31 * hash + (this.shade ? 1 : 0);
			return hash;
		}

		public static final String NULL = "NULL";

		public static final String ARGUMENT_ALL = "all", ARGUMENT_TINT = "tint", ARGUMENT_LAYER = "layer", ARGUMENT_SHADE = "shade";

		/**
		 * Returns a new layer with the parameters parsed from the given string.
		 * Parameters are set by specifying the name of the parameter followed by = and the desired value. Parameters are separated by commas. Multiple values can be specified by separating them with /.
		 * If a parameter is not specified, the default value will be used.
		 * The currently implemented parameters are:
		 * <ul>
		 * <li> all - texture path for faces without a specified texture or a null face. Defaults to a missing texture </li>
		 * <li> down/up/north/south/west/east - texture path for a specific face. Defaults to the "all" texture </li>
		 * <li> tint - the tint index for all the layer's quads. Defaults to -1 </li>
		 * <li> layer - the render layer in which this layer should render in. Defaults to solid </li>
		 * <li> shade - determines if the all the layer's quads should have diffuse lighting. Defaults to true </li>
		 * </ul>
		 */
		public static Layer parse(String string) // Constructor?
		{
			// Split all the name-value pairs into their own strings
			String[] parameters = string.split(",");
			// Set all the default parameters
			ImmutableMap.Builder textures = ImmutableMap.builder();
			ResourceLocation texture = HexModelLoader.TEXTURE_MISSING;
			int tint = -1;
			ImmutableSet.Builder renderLayers = ImmutableSet.builder().add(BlockRenderLayer.SOLID);
			boolean renderCracks = true;
			boolean shade = true;

			for(String pair : parameters) 
			{
				// Split the name-value pair into two strings
				String[] elements = pair.split("=");
				String name = elements[0];
				String value = elements[1];
				// Parse the value depending on the parameter name
				if(name.equals(ARGUMENT_ALL)) texture = new ResourceLocation(value);
				else if(name.equals(ARGUMENT_TINT)) tint = Integer.parseInt(value);
				else if(name.equals(ARGUMENT_SHADE)) shade = Boolean.parseBoolean(value);
				else if(name.equals(ARGUMENT_LAYER))
				{
					// Clear the stored layers. In case this parameter is specified more than once
					renderLayers = ImmutableSet.builder();
					renderCracks = false;
					// Split all the values into their own strings
					String[] values = value.split("/");
					for(String value1 : values)
					{
						value1 = value1.toUpperCase();
						if(value1.equals(NULL)) renderCracks = true;
						else renderLayers.add(BlockRenderLayer.valueOf(value1));
					}
				}
				else if(EnumFacing.byName(name) != null) textures.put(EnumFacing.byName(name), new ResourceLocation(value)); // FIXME Cache
			}
			return new Layer(textures.build(), texture, tint, renderLayers.build(), renderCracks, shade);
		}
	}

	protected final ImmutableList<Layer> layers;
	protected final ResourceLocation particle;
	protected final boolean ambientOcclusion;

	public ModelLayered(ImmutableList<Layer> layers, ResourceLocation particle, boolean ambientOcclusion) // FIXME use missing tex if particle is null
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
			ImmutableMap.Builder sprites = ImmutableMap.builder();
			for(Entry<EnumFacing, ResourceLocation> entry : layer.textures.entrySet()) sprites.put(entry.getKey(), getter.apply(entry.getValue()));
			layers.add(new BakedModelLayered.Layer(sprites.build(), getter.apply(layer.texture), layer.tint, layer.renderLayers, layer.renderCracks, layer.shade));
		}
		// Also pass the item perspective transforms to the baked model, convert and pass the particle texture and set ambient occlusion
		return new BakedModelLayered(state, format, this.ambientOcclusion, layers.build(), getter.apply(this.particle));
	}

	@Override
	public Collection<ResourceLocation> getTextures() 
	{
		// Return all the saved layers' texture paths as dependencies
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
		// Parse all the layers mapped to numbers starting with 0 in order
		String tag = TAG_LAYER + "0";
		for(int a = 0; textures.containsKey(tag); ++a)
		{
			Layer layer = Layer.parse(textures.get(tag));
			builder.add(layer);
			tag = TAG_LAYER + (a + 1);
		}
		ImmutableList<Layer> layers = builder.build();
		// Also save the particle texture if present
		return new ModelLayered(layers, textures.containsKey(TAG_PARTICLE) ? new ResourceLocation(textures.get(TAG_PARTICLE)) : layers.isEmpty() ? HexModelLoader.TEXTURE_MISSING : layers.get(0).texture, this.ambientOcclusion);
	}

	@Override
	public IModel smoothLighting(boolean value)
	{
		return value == this.ambientOcclusion ? this : new ModelLayered(this.layers, this.particle, value);
	}
}