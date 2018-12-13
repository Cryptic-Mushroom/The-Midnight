package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightStairs extends BlockStairs implements IModelProvider {
    private final Supplier<IBlockState> parentSupplier;

    public BlockMidnightStairs(Supplier<IBlockState> parentSupplier) {
        super(Blocks.AIR.getDefaultState());
        this.parentSupplier = parentSupplier;
        this.setCreativeTab(Midnight.BUILDING_TAB);
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return this.parentSupplier.get().getMaterial();
    }

    @Override
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return this.parentSupplier.get().getBlockHardness(world, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        IBlockState parentState = this.parentSupplier.get();
        return parentState.getBlock().getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        IBlockState parentState = this.parentSupplier.get();
        return parentState.getBlock().getSoundType(parentState, world, pos, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        IBlockState parentState = this.parentSupplier.get();
        parentState.getBlock().randomDisplayTick(parentState, world, pos, rand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.parentSupplier.get().getPackedLightmapCoords(source, pos);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        IBlockState parentState = this.parentSupplier.get();
        return parentState.getBlock().canRenderInLayer(parentState, layer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.parentSupplier.get().getBlock().getRenderLayer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        return this.parentSupplier.get().getSelectedBoundingBox(world, pos);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.parentSupplier.get().getMapColor(world, pos);
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return parentSupplier.get().getBlock().canCollideCheck(state, hitIfLiquid);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return parentSupplier.get().getBlock().canSustainPlant(state, world, pos, direction, plantable);
    }
}
