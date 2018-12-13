package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightSlab extends BlockSlab implements IModelProvider {
    private final Supplier<IBlockState> parentSupplier;

    public BlockMidnightSlab(Supplier<IBlockState> parentSupplier) {
        super(Material.ROCK);
        this.parentSupplier = parentSupplier;
        this.setCreativeTab(Midnight.BUILDING_TAB);
        this.setDefaultState(this.buildDefaultState(this.blockState.getBaseState()));
    }

    protected IBlockState buildDefaultState(IBlockState baseState) {
        return baseState.withProperty(HALF, EnumBlockHalf.BOTTOM);
    }

    @Override
    public String getTranslationKey(int meta) {
        return super.getTranslationKey();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
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
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.parentSupplier.get().getMapColor(world, pos);
    }

    IBlockState getParentstate() {
        return parentSupplier.get();
    }
}
