/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.block;

import midnight.common.misc.tags.MnBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * A block resembling Night Reed. This block grows higher over a certain time, up to 12 blocks if fully waterlogged. As
 * such, this block is waterloggable. Night reed can be placed on blocks in the {@link MnBlockTags#NIGHT_REED_GROWABLE
 * midnight:night_reed_growable} tag, and they require water nearby or inside the reed in order to be grown or placed.
 *
 * Night reed has the following properties:
 * <ul>
 * <li>{@code root} (inherited from {@link UpwardPlantBlock}) - whether this is the block that is directly in contact
 * with the ground</li>
 * <li>{@code end} (inherited from {@link UpwardPlantBlock}) - whether this is the highest block of the reed</li>
 * <li>{@code waterlogged} - whether this reed is placed in Dark Water</li>
 * <li>{@code age} - the grow age of this reed, goes from 0 to 15, after which it resets to 0 and tries to grow a reed
 * on top of itself</li>
 * </ul>
 * <p>
 * Note that even though the reed might not be able to grow, when it's age is 15 and it's ticked, it will reset its age
 * to 0 regardless of whether it grew or not.
 * </p>
 *
 * @author Shadew
 * @since 0.6.0
 */
public class NightReedBlock extends UpwardPlantBlock implements IMnWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    public NightReedBlock(Properties props) {
        super(props);

        // Since we inherit from UpwardPlantBlock, and the constructor of that already initializes the default state
        // we only need to update it - hence not getStateContainer().getBaseState()
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, AGE);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        tickFluid(world, pos, state);
        return super.updateShape(state, dir, adjState, world, pos, adjPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return waterlog(super.getStateForPlacement(ctx), ctx);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return fluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        // We cannot grow if we are unstable - instead play dead
        BlockPos down = pos.below();
        BlockState stateDown = world.getBlockState(down);
        if (!stateDown.is(this) && !mayPlaceOn(stateDown, world, down)) {
            world.destroyBlock(pos, true);
            return;
        }

        // Grow older
        int age = state.getValue(AGE);
        if (age < 15) {
            world.setBlockAndUpdate(pos, state.setValue(AGE, age + 1));
        } else {
            // Attempt grow and reset age to 0
            // Note that age resets even though it might not have grown - this is intentional!
            state = state.setValue(AGE, 0);
            world.setBlockAndUpdate(pos, state);
            growUp(world, pos, state);
        }
    }

    /**
     * Checks if the reed can grow higher - it may not grow higher than 12 blocks underwater and 4 above water. This
     * function computes a certain 'height cost', which increases by 1 for each block in water, and by 3 for each block
     * in air. When this cost exceeds 12 the reed will no longer grow higher.
     *
     * @param world The world
     * @param pos   The position of the reed (does not need to be the upper position because it doesn't make sense for
     *              the logic)
     * @return True if this reed can grow higher
     *
     * @since 0.6.0
     */
    private boolean canGrowHigher(World world, BlockPos pos) {
        BlockPos.Mutable mpos = new BlockPos.Mutable();
        mpos.set(pos);

        int h = 0;
        while (mpos.getY() > 0) {
            BlockState state = world.getBlockState(mpos);
            if (!state.is(this))
                return true;

            if (state.getFluidState().isEmpty())
                h += 3; // Grow less high above water, make one block above water count effectively as 3 grow blocks
            else
                h += 1;

            if (h >= 12) // Limit reached - stop loop here because we do not need to count any further
                return false;

            mpos.move(Direction.DOWN);
        }

        return false; // Usually not reached, unless in a very strange case where a reed is placed on the void...
    }

    /**
     * Grows this reed, if possible. This updates the reed at the current pos to have the correct state after a reed
     * block has grown on top of it (i.e. it's end state will be set to false). The reed can grow through dark water and
     * air, and requires a 'height cost' less than 12 to grow (see {@link #canGrowHigher}).
     *
     * @param world The world we're growing in
     * @param pos   The location of the reed
     * @param state The reed's block state
     * @since 0.6.0
     */
    private void growUp(World world, BlockPos pos, BlockState state) {
        BlockPos up = pos.above();
        BlockState stateUp = world.getBlockState(up);
        if (stateUp.getBlock().isAir(stateUp, world, up) || stateUp.getFluidState().is(FluidTags.WATER)) {
            if (canGrowHigher(world, pos)) { // Check height cost
                world.setBlockAndUpdate(up, waterlog(defaultBlockState().setValue(END, true), world, up));
                world.setBlockAndUpdate(pos, state.setValue(END, false));
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
        // Need appropriate soil
        if (!state.is(MnBlockTags.NIGHT_REED_GROWABLE))
            return false;

        // Need water inside or ...
        if (world.getFluidState(pos.above()).getType() == Fluids.WATER) {
            return true;
        }

        // ... need water nearby
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (world.getFluidState(pos.relative(dir)).getType() == Fluids.WATER) {
                return true;
            }
        }
        return false;
    }
}
