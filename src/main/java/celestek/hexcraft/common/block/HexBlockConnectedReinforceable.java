package celestek.hexcraft.common.block;

import javax.annotation.Nullable;

import celestek.hexcraft.utility.EHexColor;
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
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * A block which functions as {@link HexBlockConnected}, but can also be reinforced
 */
public class HexBlockConnectedReinforceable extends HexBlockConnected
{
	public static final PropertyBool REINFORCED = HexProperties.REINFORCED;

	public HexBlockConnectedReinforceable(String name, CreativeTabs tab, Material material, EHexColor color)
	{
		super(name, tab, material, color);
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
		return new ExtendedBlockState(this, new IProperty[] {REINFORCED}, new IUnlistedProperty[] {FACE_STATES});
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		// 4 bit is reinforced
		return state.getValue(REINFORCED) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(REINFORCED, meta == 1 ? true : false);
	}
}