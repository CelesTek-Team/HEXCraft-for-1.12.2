package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Predicate;

import celestek.hexcraft.HexCraft;
import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * HEXCraft's base block that stores a special state mapper,, a color code for tinting and a texture filter for the fullbright model
 * All of these features are handled automatically in {@link HexBlocks}
 */
public class HexBlock extends Block {
	/**
	 * The block's special state mapper. which is used to map multiple blocks to the same model or ignore certain properties.
	 * Can be left empty in which case the block's registry name is used instead
	 */
	public final Optional<HexStateMapper> mapper;
	/**
	 * The block's color code which is used to register a tint to the block's model. Can be set to negative values to not register a tint
	 */
	public final int color;
	/**
	 * The block's texture filter which determines the textures to apply the fullbright effect. Can be left empty to not register a fullbright model
	 */
	public final Optional<Predicate<String>> filter;

	public HexBlock(String name, Optional<HexStateMapper> mapper, CreativeTabs tab, Material material, int color, Optional<Predicate<String>> filter) {
		super(material);

		this.setRegistryName(HexCraft.ID, name);
		this.setUnlocalizedName(HexCraft.ID + "." + name);
		this.setCreativeTab(tab);
		this.mapper = mapper;

		this.color = color;
		this.filter = filter;
	}

	/**
	 * Determines whether the attached fullbright model will have cache enabled
	 */
	public boolean enableCache() // FIXME Different approach?
	{
		return false;
	}
}