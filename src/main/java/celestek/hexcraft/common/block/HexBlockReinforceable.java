package celestek.hexcraft.common.block;

import java.util.Optional;

import javax.annotation.Nullable;

import celestek.hexcraft.client.model.HexStateMapper;
import celestek.hexcraft.utility.HexProperties;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/**
 * A block that can be reinforced. Reinforcing increases the hardness, harvest level and explosion resistance to the level of obsidian
 */
public class HexBlockReinforceable extends HexBlock
{
	public static final PropertyBool REINFORCED = HexProperties.REINFORCED;

	public HexBlockReinforceable(String name, Optional<HexStateMapper> mapper, CreativeTabs tab, Material material, int color, String... textures)
	{
		super(name, mapper, tab, material, color, textures);
		this.setDefaultState(this.blockState.getBaseState().withProperty(REINFORCED, false));
	}

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos position)
	{
		return state.getValue(REINFORCED) ? 50F : super.getBlockHardness(state, world, position);
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return state.getValue(REINFORCED) ? 3 : super.getHarvestLevel(state);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos position, @Nullable Entity exploder, Explosion explosion)
	{
		return world.getBlockState(position).getValue(REINFORCED) ? 1200F : super.getExplosionResistance(world, position, exploder, explosion);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {REINFORCED});
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		// bit 4 is reinforced
		return state.getValue(REINFORCED) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(REINFORCED, meta == 1);
	}
}