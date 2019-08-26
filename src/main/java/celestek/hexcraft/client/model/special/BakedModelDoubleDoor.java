package celestek.hexcraft.client.model.special;

import java.util.List;
import java.util.Optional;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import celestek.hexcraft.utility.HexShapes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A scaled down 2-block high baked model of a  special frame which uses 1 texture and door which uses 3 textures for the top, bottom and rear sides. The frame is rendered on the solid layer while the door - on the translucent layer.
 * Supports quad caching and item perspective transforms
 */
@SideOnly(Side.CLIENT)
public class BakedModelDoubleDoor implements IBakedModel
{
	/**
	 * A cache key used to store different quads of the model
	 */
	/*
	private class CacheKey
	{
		protected BakedModelDoubleDoor model;
		protected BlockRenderLayer renderLayer;

		public CacheKey(BakedModelDoubleDoor model, BlockRenderLayer renderLayer)
		{
			this.model = model;
			this.renderLayer = renderLayer;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey key = (CacheKey) o;
			return this.renderLayer == key.renderLayer; // Careful with this comparison in case block states are changed and are no longer compared like this
		}

		@Override
		public int hashCode()
		{
			return this.renderLayer != null ? this.renderLayer.hashCode() : 0;
		}
	}

	private static final LoadingCache<CacheKey, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<CacheKey, List<BakedQuad>>()
	{
		@Override
		public List<BakedQuad> load(CacheKey key)
		{
			List<BakedQuad> quads = Lists.newArrayList();
			if(key.renderLayer == null || key.renderLayer == BlockRenderLayer.SOLID)
			{
				HexShapes.Frame.create(quads, key.model.format, bottomTransform, 0b01, null, 0, false, key.model.base);
				HexShapes.Frame.create(quads, key.model.format, topTransform, 0b10, null, 0, false, key.model.base);
			}
			if(key.renderLayer == null || key.renderLayer == BlockRenderLayer.TRANSLUCENT)
			{
				HexShapes.Door.create(quads, key.model.format, bottomTransform, 0b00, 0b01, null, -1, true, key.model.bottom, key.model.side);
				HexShapes.Door.create(quads, key.model.format, topTransform, 0b00, 0b10, null, -1, true, key.model.top, key.model.side);
			}
			return quads;
		}
	});
	*/

	/**
	 * The item perspective transforms defined under the "transform" tag in the blockstate
	 */
	protected final IModelState state;
	/**
	 * The vertex format in which all of the model's quads should be drawn in
	 */
	protected final VertexFormat format;

	protected final TextureAtlasSprite base, bottom, top, side;

	public BakedModelDoubleDoor(IModelState state, VertexFormat format, TextureAtlasSprite base, TextureAtlasSprite bottom, TextureAtlasSprite top, TextureAtlasSprite side)
	{
		this.state = state;
		this.format = format;
		this.base = base;
		this.bottom = bottom;
		this.top = top;
		this.side = side;
	}

	/**
	 * The transforms used to scale the halves of the door
	 */
	public static final Optional<TRSRTransformation>
	bottomTransform = Optional.of(ForgeBlockStateV1.Transforms.convert(4f, -4f, 0f, 0f, 0f, 0f, 0.5f)),
	topTransform = Optional.of(ForgeBlockStateV1.Transforms.convert(4f, 4f, 0f, 0f, 0f, 0f, 0.5f));

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		// Don't draw anything if faces should be culled
		if(side != null) return quads;
		BlockRenderLayer renderLayer = MinecraftForgeClient.getRenderLayer();
		// Draw the quads
		// Render the frame part if on the solid layer
		if(renderLayer == null || renderLayer == BlockRenderLayer.SOLID)
		{
			HexShapes.Frame.create(quads, this.format, bottomTransform, 0b01, side, 0, false, this.base);
			HexShapes.Frame.create(quads, this.format, topTransform, 0b10, side, 0, false, this.base);
		}
		// Render the door part if on the translucent layer
		if(renderLayer == null || renderLayer == BlockRenderLayer.TRANSLUCENT)
		{
			HexShapes.Door.create(quads, this.format, bottomTransform, 0b00, 0b01, side, -1, true, this.bottom, this.side);
			HexShapes.Door.create(quads, this.format, topTransform, 0b00, 0b10, side, -1, true, this.top, this.side);
		}
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return false;
	}

	@Override
	public boolean isGui3d()
	{
		return true;
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return this.bottom;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType transform)
	{
		return PerspectiveMapWrapper.handlePerspective(this, this.state, transform);
	}
}