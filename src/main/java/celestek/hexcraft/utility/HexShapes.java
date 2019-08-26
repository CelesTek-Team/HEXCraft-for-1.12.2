package celestek.hexcraft.utility;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * Contains different model shapes used predominantly in HEXCraft's baked models
 */
public final class HexShapes
{
	public static final class Cube
	{
		public static List<BakedQuad> create(List<BakedQuad> quads, VertexFormat format,  Optional<TRSRTransformation> transform, EnumFacing face, int tint, boolean shade, TextureAtlasSprite sprite)
		{
			if(face == EnumFacing.DOWN) quads.add(HexUtilities.createQuad(format, transform, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 1f, 0f, 0f, 0f, 16f, 16f, 16f, 16f, 0f, 1f, tint, shade, sprite));
			if(face == EnumFacing.UP) quads.add(HexUtilities.createQuad(format, transform, 0f, 1f, 0f, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 16f, 16f, 16f, 16f, 0f, 1f, tint, shade, sprite));
			if(face == EnumFacing.NORTH) quads.add(HexUtilities.createQuad(format, transform, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 16f, 16f, 16f, 16f, 0f, 1f, tint, shade, sprite));
			if(face == EnumFacing.SOUTH) quads.add(HexUtilities.createQuad(format, transform, 0f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 16f, 16f, 16f, 16f, 0f, 1f, tint, shade, sprite));
			if(face == EnumFacing.WEST) quads.add(HexUtilities.createQuad(format, transform, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 0f, 0f, 16f, 16f, 16f, 16f, 0f, 1f, tint, shade, sprite));
			if(face == EnumFacing.EAST) quads.add(HexUtilities.createQuad(format, transform, 1f, 1f, 1f, 1f, 0f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, 0f, 0f, 0f, 16f, 16f, 16f, 16f, 0f, 1f, tint, shade, sprite));
			return quads;
		}
	}

	public static final class Door
	{
		public static final float dThck = 0.1875f;

		public static List<BakedQuad> create(List<BakedQuad> quads, VertexFormat format, Optional<TRSRTransformation> transform, int flip, int edge, EnumFacing face, int tint, boolean shade, TextureAtlasSprite front, TextureAtlasSprite side)
		{
			if(face != null) return quads;
			// DOWN
			// Bit 1 is bottom, 2 is top
			if((edge & 0b01) != 0) quads.add(HexUtilities.createQuad(format, transform, 0f, 0f, 0f, dThck, 0f, 0f, dThck, 0f, 1f, 0f, 0f, 1f, 0f, 13f, 0f, 16f, 16f, 16f, 16f, 13f, 1f, tint, shade, side));
			// UP
			if((edge & 0b10) != 0) quads.add(HexUtilities.createQuad(format, transform, 0f, 1f, 0f, 0f, 1f, 1f, dThck, 1f, 1f, dThck, 1f, 0f, 0f, 3f, 16f, 3f, 16f, 0f, 0f, 0f, 1f, tint, shade, side));
			// NORTH
			quads.add(HexUtilities.createQuad(format, transform, dThck, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, dThck, 1f, 0f, 0f, 16f, 3f, 16f, 3f, 0f, 0f, 0f, 1f, tint, shade, side));
			// EAST
			quads.add(HexUtilities.createQuad(format, transform, 0f, 0f, 1f, dThck, 0f, 1f, dThck,1f, 1f, 0f, 1f, 1f, 13f, 16f, 16f, 16f, 16f, 0f, 13f, 0f, 1f, tint, shade, side));;
			// WEST
			// EAST
			// Bit 1 is flip over X axis, 2 is flip over Y axis
			if((flip & 0b11) == 0b00)
			{
				quads.add(HexUtilities.createQuad(format, transform, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 16f, 16f, 0f, 16f, 0f, 0f, 16f, 0f, 1f, tint, shade, front));
				quads.add(HexUtilities.createQuad(format, transform, dThck, 0f, 1f, dThck, 0f, 0f, dThck, 1f, 0f, dThck, 1f, 1f, 0f, 16f, 16f, 16f, 16f, 0f, 0f, 0f, 1f, tint, shade, front));
			}
			else if((flip & 0b11) == 0b01)
			{
				quads.add(HexUtilities.createQuad(format, transform, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 0f, 16f, 16f, 16f, 16f, 0f, 0f, 0f, 1f, tint, shade, front));
				quads.add(HexUtilities.createQuad(format, transform, dThck, 0f, 1f, dThck, 0f, 0f, dThck, 1f, 0f, dThck, 1f, 1f, 16f, 16f, 0f, 16f, 0f, 0f, 16f, 0f, 1f, tint, shade, front));
			}
			else if((flip & 0b11) == 0b10)
			{
				quads.add(HexUtilities.createQuad(format, transform, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 16f, 0f, 16f, 16f, 0f, 16f, 1f, tint, shade, front));
				quads.add(HexUtilities.createQuad(format, transform, dThck, 0f, 1f, dThck, 0f, 0f, dThck, 1f, 0f, dThck, 1f, 1f, 16f, 0f, 0f, 0f, 0f, 16f, 16f, 16f, 1f, tint, shade, front));
			}
			else if((flip & 0b11) == 0b11)
			{
				quads.add(HexUtilities.createQuad(format, transform, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 16f, 0f, 0f, 0f, 0f, 16f, 16f, 16f, 1f, tint, shade, front));
				quads.add(HexUtilities.createQuad(format, transform, dThck, 0f, 1f, dThck, 0f, 0f, dThck, 1f, 0f, dThck, 1f, 1f, 0f, 0f, 16f, 0f, 16f, 16f, 0f, 16f, 1f, tint, shade, front));
			}
			return quads;
		}
	}

	public static final class Frame
	{
		public static final float dThck = 0.1875f;
		public static final float dWidt = 0.1875f;
		public static final float dOffs = 0.001f;

		public static List<BakedQuad> create(List<BakedQuad> quads, VertexFormat format, Optional<TRSRTransformation> transform, int edge, EnumFacing face, int tint, boolean shade, TextureAtlasSprite sprite)
		{
			if(face != null) return quads;
			quads.add(HexUtilities.createQuad(format, transform, dOffs, 0f, 0f, dOffs, 0f, dWidt, dOffs, 1f, dWidt, dOffs, 1f, 0f, 0f, 16f, 3f, 16f, 3f, 0f, 0f, 0f, 1f, tint, shade, sprite));
			quads.add(HexUtilities.createQuad(format, transform, dOffs, 0f, 1f - dWidt, dOffs, 0f, 1f, dOffs, 1f, 1f, dOffs, 1f, 1f - dWidt, 13f, 16f, 16f, 16f, 16f, 0f, 13f, 0f, 1f, tint, shade, sprite));
			// Bit 1 is bottom, 2 is top
			if((edge & 0b10) != 0) // UPPER
			{
				quads.add(HexUtilities.createQuad(format, transform, dOffs, 1f - dWidt, dWidt, dOffs, 1f - dWidt, 1f - dWidt, dOffs, 1f, 1f - dWidt, dOffs, 1f, dWidt, 3f, 3f, 13f, 3f, 13f, 0f, 3f, 0f, 1f, tint, shade, sprite));
				quads.add(HexUtilities.createQuad(format, transform, dThck - dOffs, 1f - dWidt, 1f - dWidt, dThck - dOffs, 1f - dWidt, dWidt, dThck - dOffs, 1f, dWidt, dThck - dOffs, 1f, 1f - dWidt, 3f, 3f, 13f, 3f, 13f, 0f, 3f, 0f, 1f, tint, shade, sprite));
			}
			if((edge & 0b01) != 0) // LOWER
			{
				quads.add(HexUtilities.createQuad(format, transform, dOffs, 0f, dWidt, dOffs, 0f, 1f - dWidt, dOffs, dWidt, 1f - dWidt, dOffs, dWidt, dWidt, 3f, 16f, 13f, 16f, 13f, 13f, 3f, 13f, 1f, tint, shade, sprite));
				quads.add(HexUtilities.createQuad(format, transform, dThck - dOffs, 0f, 1f - dWidt, dThck - dOffs, 0f, dWidt, dThck - dOffs, dWidt, dWidt, dThck - dOffs, dWidt, 1f - dWidt, 3f, 16f, 13f, 16f, 13f, 13f, 3f, 13f, 1f, tint, shade, sprite));
			}
			quads.add(HexUtilities.createQuad(format, transform, dThck - dOffs, 0f, 1f, dThck - dOffs, 0f, 1f - dWidt, dThck - dOffs, 1f, 1f - dWidt, dThck - dOffs, 1f, 1f, 0f, 16f, 3f, 16f, 3f, 0f, 0f, 0f, 1f, tint, shade, sprite));
			quads.add(HexUtilities.createQuad(format, transform, dThck - dOffs, 0f, dWidt, dThck - dOffs, 0f, 0f, dThck - dOffs, 1f, 0f, dThck - dOffs, 1f, dWidt, 13f, 16f, 16f, 16f, 16f, 0f, 13f, 0f, 1f, tint, shade, sprite));
			return quads;
		}
	}

	public static final class Monolith
	{
		public static final float yMin = 0f;
		public static final float yMax = 0.75f;

		public static final float xA = 0.1874f;
		public static final float xB = 0.3437f;
		public static final float xC = 0.6563f;
		public static final float xD = 0.8126f;
		public static final float xE = 0.6563f;
		public static final float xF = 0.3437f;
		public static final float zA = 0.5f;
		public static final float zB = 0.7707f;
		public static final float zC = 0.7707f;
		public static final float zD = 0.5f;
		public static final float zE = 0.2292f;
		public static final float zF = 0.2292f;

		public static final float topAu = 0.3f;
		public static final float topAv = 4.5f;
		public static final float topBu = 2.6f;
		public static final float topBv = 0.25f;
		public static final float topCu = 7.4f;
		public static final float topCv = 0.25f;
		public static final float topDu = 9.7f;
		public static final float topDv = 4.5f;
		public static final float topEu = 7.4f;
		public static final float topEv = 8.75f;
		public static final float topFu = 2.6f;
		public static final float topFv = 8.75f;

		public static final float sideu = 11.25f;
		public static final float sideU = 15.75f;
		public static final float sidev = 0.25f;
		public static final float sideV = 11.75f;

		public static final float alpha = 0.75f;

		public static List<BakedQuad> create(List<BakedQuad> quads, VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing face, int tint, boolean shade, TextureAtlasSprite sprite)
		{
			// Top Face
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xB, yMax, zB, xC, yMax, zC, xD, yMax, zD, xA, yMax, zA, topBu, topBv, topCu, topCv, topDu, topDv, topAu, topAv, alpha, tint, shade, sprite)); // BCDA
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xA, yMax, zA, xD, yMax, zD, xE, yMax, zE, xF, yMax, zF, topAu, topAv, topDu, topDv, topEu, topEv, topFu, topFv, alpha, tint, shade, sprite)); // ADEF

			// Side Faces
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xF, yMax, zF, xE, yMax, zE, xE, yMin, zE, xF, yMin, zF, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, shade, sprite)); // FEEF
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xE, yMax, zE, xD, yMax, zD, xD, yMin, zD, xE, yMin, zE, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, shade, sprite)); // EDDE
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xD, yMax, zD, xC, yMax, zC, xC, yMin, zC, xD, yMin, zD, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, shade, sprite)); // DCCD
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xC, yMax, zC, xB, yMax, zB, xB, yMin, zB, xC, yMin, zC, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, shade, sprite)); // CBBC
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xB, yMax, zB, xA, yMax, zA, xA, yMin, zA, xB, yMin, zB, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, shade, sprite)); // BAAB
			if(face == null) quads.add(HexUtilities.createQuad(format, transform, xA, yMax, zA, xF, yMax, zF, xF, yMin, zF, xA, yMin, zA, sideu, sidev, sideU, sidev, sideU, sideV, sideu, sideV, alpha, tint, shade, sprite)); // AFFA
			return quads;
		}
	}

	public static final class MonolithBase
	{
		public static List<BakedQuad> create(List<BakedQuad> quads, VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing face, int tint, boolean shade, TextureAtlasSprite sprite)
		{
			return quads;
		}
	}
}