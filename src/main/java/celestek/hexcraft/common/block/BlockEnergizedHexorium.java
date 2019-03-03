package celestek.hexcraft.common.block;

import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.common.init.HexItems;
import celestek.hexcraft.utility.EHexColors;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergizedHexorium extends HexBlock {
	public BlockEnergizedHexorium(EHexColors color) {
		super("energized_hexorium_" + color.name, HexCreativeTabs.tabDecorative, Material.GLASS, color.color, color.texture);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos position, IBlockState state, EntityPlayer player)
	{
		return false;
	}

	// FIXME manipulator compat
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos position, IBlockState state, int fortune)
	{
		if (this.color == EHexColors.RED.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 8));
		}
		else if (this.color == EHexColors.ORANGE.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 6));
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 2));
		}
		else if (this.color == EHexColors.YELLOW.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 4));
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 4));
		}
		else if (this.color == EHexColors.LIME.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 2));
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 6));
		}
		else if (this.color == EHexColors.GREEN.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 8));
		}
		else if (this.color == EHexColors.TURQUOISE.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 6));
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 2));
		}
		else if (this.color == EHexColors.CYAN.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 4));
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 4));
		}
		else if (this.color == EHexColors.SKY_BLUE.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 2));
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 6));
		}
		else if (this.color == EHexColors.BLUE.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 8));
		}
		else if (this.color == EHexColors.PURPLE.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 6));
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 2));
		}
		else if (this.color == EHexColors.MAGENTA.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 4));
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 4));
		}
		else if (this.color == EHexColors.PINK.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 2));
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 6));
		}
		else if (this.color == EHexColors.WHITE.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_white, 8));
		}
		else if (this.color == EHexColors.LIGHT_GRAY.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_white, 6));
			drops.add(new ItemStack(HexItems.hexorium_crystal_black, 2));
		}
		else if (this.color == EHexColors.GRAY.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_white, 4));
			drops.add(new ItemStack(HexItems.hexorium_crystal_black, 4));
		}
		else if (this.color == EHexColors.DARK_GRAY.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_white, 2));
			drops.add(new ItemStack(HexItems.hexorium_crystal_black, 6));
		}
		else if (this.color == EHexColors.BLACK.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_black, 8));
		}
		else if (this.color == EHexColors.RAINBOW.color) {
			drops.add(new ItemStack(HexItems.hexorium_crystal_red, 2));
			drops.add(new ItemStack(HexItems.hexorium_crystal_green, 2));
			drops.add(new ItemStack(HexItems.hexorium_crystal_blue, 2));
			drops.add(new ItemStack(HexItems.hexorium_crystal_white, 2));
		}
	}
}