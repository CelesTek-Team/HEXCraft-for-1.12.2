package celestek.hexcraft.utility;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.TRSRTransformation;

public final class HexShapes
{
	private HexShapes() {}

	public static final class Cube
	{
		public static BakedQuad create(VertexFormat format, EnumFacing face, int tint, TextureAtlasSprite sprite)
		{
			if(face == null) return null;
			switch(face)
			{
			case DOWN: return HexUtilities.createQuad(format, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 1f, tint, sprite);
			case UP: return HexUtilities.createQuad(format, 0f, 1f, 0f, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0f, tint, sprite);
			case NORTH: return HexUtilities.createQuad(format, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, tint, sprite);
			case SOUTH: return HexUtilities.createQuad(format, 0f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, 1f, 1f, 1f, 1f, tint, sprite);
			case WEST: return HexUtilities.createQuad(format, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, tint, sprite);
			case EAST: return HexUtilities.createQuad(format, 1f, 1f, 1f, 1f, 0f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, tint, sprite);
			default: return null;
			}
		}
	}

	public static final class Monolith
	{
		public static final float yMin = 0F;
		public static final float yMax = 0.75F;

		public static final float xA = 0.1874F;
		public static final float xB = 0.3437F;
		public static final float xC = 0.6563F;
		public static final float xD = 0.8126F;
		public static final float xE = 0.6563F;
		public static final float xF = 0.3437F;
		public static final float zA = 0.5F;
		public static final float zB = 0.7707F;
		public static final float zC = 0.7707F;
		public static final float zD = 0.5F;
		public static final float zE = 0.2292F;
		public static final float zF = 0.2292F;

		public static final float topAu = 0.3F;
		public static final float topAv = 4.5F;
		public static final float topBu = 2.6F;
		public static final float topBv = 0.25F;
		public static final float topCu = 7.4F;
		public static final float topCv = 0.25F;
		public static final float topDu = 9.7F;
		public static final float topDv = 4.5F;
		public static final float topEu = 7.4F;
		public static final float topEv = 8.75F;
		public static final float topFu = 2.6F;
		public static final float topFv = 8.75F;

		public static final float sideu = 11.25F;
		public static final float sideU = 15.75F;
		public static final float sidev = 0.25F;
		public static final float sideV = 11.75F;

		public static final float alpha = 0.75f;

		public static List<BakedQuad> create(List<BakedQuad> quads, VertexFormat format, Optional<TRSRTransformation> transform, int tint, TextureAtlasSprite sprite)
		{
			// Top Face
			quads.add(HexUtilities.createQuad(format, transform, xB, yMax, zB, xC, yMax, zC, xD, yMax, zD, xA, yMax, zA, topBu, topBv, topCu, topCv, topDu, topDv, topAu, topAv, alpha, tint, sprite)); // BCDA
			quads.add(HexUtilities.createQuad(format, transform, xA, yMax, zA, xD, yMax, zD, xE, yMax, zE, xF, yMax, zF, topAu, topAv, topDu, topDv, topEu, topEv, topFu, topFv, alpha, tint, sprite)); // ADEF

			// Side Faces
			quads.add(HexUtilities.createQuad(format, transform, xF, yMax, zF, xE, yMax, zE, xE, yMin, zE, xF, yMin, zF, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, sprite)); // FEEF
			quads.add(HexUtilities.createQuad(format, transform, xE, yMax, zE, xD, yMax, zD, xD, yMin, zD, xE, yMin, zE, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, sprite)); // EDDE
			quads.add(HexUtilities.createQuad(format, transform, xD, yMax, zD, xC, yMax, zC, xC, yMin, zC, xD, yMin, zD, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, sprite)); // DCCD
			quads.add(HexUtilities.createQuad(format, transform, xC, yMax, zC, xB, yMax, zB, xB, yMin, zB, xC, yMin, zC, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, sprite)); // CBBC
			quads.add(HexUtilities.createQuad(format, transform, xB, yMax, zB, xA, yMax, zA, xA, yMin, zA, xB, yMin, zB, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, sprite)); // BAAB
			quads.add(HexUtilities.createQuad(format, transform, xA, yMax, zA, xF, yMax, zF, xF, yMin, zF, xA, yMin, zA, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, sprite)); // AFFA
			return quads;
		}
	}
}