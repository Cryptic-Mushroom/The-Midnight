/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.common.block.fluid;

import midnight.client.MidnightClient;
import midnight.common.block.MnBlocks;
import midnight.common.world.biome.MnBiomeColors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * The base type of the Dark Water fluid. Behaves just like {@linkplain WaterFluid vanilla water}, except has a
 * different color and most blocks are not waterloggable in Dark Water.
 *
 * @author shadew
 * @since 0.6.0
 */
public abstract class DarkWaterFluid extends FlowingFluid {
    @Override
    public Fluid getFlowingFluid() {
        return MnFluids.FLOWING_DARK_WATER;
    }

    @Override
    public Fluid getStillFluid() {
        return MnFluids.DARK_WATER;
    }

    @Override
    public Item getFilledBucket() {
        return Items.WATER_BUCKET; // TODO This sucks
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(World world, BlockPos pos, FluidState state, Random rand) {
        if(!state.isSource() && !state.get(FALLING)) {
            if(rand.nextInt(64) == 0) {
                world.playSound(
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS,
                    rand.nextFloat() * 0.25F + 0.75F,
                    rand.nextFloat() + 0.5F,
                    false
                );
            }
        } else if(rand.nextInt(10) == 0) {
            world.addParticle(
                ParticleTypes.UNDERWATER,
                pos.getX() + rand.nextFloat(),
                pos.getY() + rand.nextFloat(),
                pos.getZ() + rand.nextFloat(),
                0, 0, 0
            );
        }

    }

    @Override
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IParticleData getDripParticleData() {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    protected boolean canSourcesMultiply() {
        return true;
    }

    @Override
    protected void beforeReplacingBlock(IWorld world, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.hasTileEntity() ? world.getTileEntity(pos) : null;
        Block.spawnDrops(state, world, pos, tileentity);
    }

    @Override
    public int getSlopeFindDistance(IWorldReader world) {
        return 4;
    }

    @Override
    public BlockState getBlockState(FluidState state) {
        return MnBlocks.DARK_WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid == MnFluids.DARK_WATER || fluid == MnFluids.FLOWING_DARK_WATER;
    }

    @Override
    public int getLevelDecreasePerBlock(IWorldReader world) {
        return 1;
    }

    @Override
    public int getTickRate(IWorldReader world) {
        return 5;
    }

    @Override
    public boolean canDisplace(FluidState state, IBlockReader world, BlockPos pos, Fluid fluid, Direction dir) {
        return dir == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    @Override
    protected float getExplosionResistance() {
        return 100;
    }

    @Override
    protected FluidAttributes createAttributes() {
        return new Attributes(this);
    }

    /**
     * Fluid attributes of {@linkplain DarkWaterFluid Dark Water}.
     *
     * @author shadew
     * @since 0.6.0
     */
    private static class Attributes extends FluidAttributes {
        Attributes(Fluid fluid) {
            super(
                FluidAttributes.builder(new ResourceLocation("midnight:block/dark_water_still"), new ResourceLocation("midnight:block/dark_water_flow"))
                               .overlay(new ResourceLocation("midnight:block/dark_water_overlay"))
                               .translationKey("block.midnight.dark_water")
                               .color(0xFF481A9C),
                fluid
            );
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public int getColor(IBlockDisplayReader world, BlockPos pos) {
            return 0xFF000000 | MidnightClient.get().getDarkWaterColorCache().getColor(pos, MnBiomeColors.DARK_WATER);
        }
    }

    /**
     * Flowing variant of {@link DarkWaterFluid}.
     *
     * @author shadew
     * @since 0.6.0
     */
    public static class Flowing extends DarkWaterFluid {
        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL_1_8);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    /**
     * Source variant of {@link DarkWaterFluid}.
     *
     * @author shadew
     * @since 0.6.0
     */
    public static class Source extends DarkWaterFluid {
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
