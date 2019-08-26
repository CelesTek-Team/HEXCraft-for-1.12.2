package celestek.hexcraft.common.block;

import java.util.Optional;
import java.util.function.Function;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.common.init.HexCreativeTabs;
import celestek.hexcraft.utility.EHexColor;
import celestek.hexcraft.utility.HexUtilities;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnergizedHexorium extends HexBlock {
	public BlockEnergizedHexorium(EHexColor color) {
		super("energized_hexorium_" + color.name, HexCreativeTabs.decorative, Material.GLASS, color);
		this.setHardness(0.3f);
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
		this.color.addDrops(drops);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<HexStateMapper> addStateMapper()
	{
		return this.color.isSpecial() ? Optional.empty() : Optional.of(new HexStateMapper("energized_hexorium"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Optional<Function<IBakedModel, IBakedModel>> addModelOverride(ResourceLocation path)
	{
		return Optional.of(HexUtilities.createFullbrightOverride(HexUtilities.FILTER_TRUE));
	}
}