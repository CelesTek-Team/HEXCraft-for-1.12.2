package celestek.hexcraft.utility;

import celestek.hexcraft.client.event.HexClientEvents;
import celestek.hexcraft.client.model.BakedModelBrightness;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HexUtilities
{
	private HexUtilities() {}

	public static boolean isLightMapDisabled()
	{
		return FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled;
	}

	private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

	public static VertexFormat getFormatWithLightMap(VertexFormat format)
	{
		if (isLightMapDisabled()) return format;
		if (format == DefaultVertexFormats.BLOCK) return DefaultVertexFormats.BLOCK;
		else if (format == DefaultVertexFormats.ITEM) return ITEM_FORMAT_WITH_LIGHTMAP;
		else if (!format.hasUvOffset(1))
		{
			VertexFormat result = new VertexFormat(format);
			result.addElement(DefaultVertexFormats.TEX_2S);
			return result;
		}
		return format;
	}

	public static void addFullbright(String texture, IForgeRegistryEntry... entries)
	{
		for(IForgeRegistryEntry entry : entries) HexClientEvents.addBakedModelOverride(entry.getRegistryName(), base -> new BakedModelBrightness(base, texture));
	}

	public static void addFullbrightNoCache(String texture, IForgeRegistryEntry... entries)
	{
		for(IForgeRegistryEntry entry : entries) HexClientEvents.addBakedModelOverride(entry.getRegistryName(), base -> new BakedModelBrightness(base, texture).disableCache());
	}
}