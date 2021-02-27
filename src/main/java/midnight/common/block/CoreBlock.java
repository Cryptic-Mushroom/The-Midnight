/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 27
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

@SuppressWarnings("deprecation")
public class CoreBlock extends Block {
    public static final IntegerProperty STATUS = MnBlockStateProperties.STATUS_0_23;
    private final Type type;

    private CoreBlock(Properties properties, Type type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ActionResultType onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (type == Type.INACTIVE) {
            if (!world.isRemote && world instanceof ServerWorld) {
                CoreUtil.Status status = CoreUtil.checkStatus((ServerWorld) world, pos);
                player.sendStatusMessage(status.getText(), true);

                if (status == CoreUtil.Status.ACTIVATED) {
                    world.setBlockState(pos, MnBlocks.ACTIVE_CORE.getDefaultState().with(STATUS, 23));
                    world.getPendingBlockTicks().scheduleTick(pos, MnBlocks.ACTIVE_CORE, 5 + world.rand.nextInt(16));
                }
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        if (type != Type.ACTIVE) {
            return;
        }
        int status = state.get(STATUS);
        if (status < 22) {
            world.setTimeOfDay(18000);
        }

        if (status == 0) {
            world.setBlockState(pos, MnBlocks.DEAD_CORE.getDefaultState());
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x != 0 || y != 0 || z != 0) {
                            world.setBlockState(pos.add(x, y, z), Blocks.OBSIDIAN.getDefaultState());
                        }
                    }
                }
            }
            CoreUtil.detoriateSpire(world, pos);
            return;
        }
        boolean forceNextStatus = false;
        if (status == 23) {
            CoreUtil.activatePattern(world, pos);
            forceNextStatus = true;
        } else if (status == 22) {
            CoreUtil.buildSpireTeleportPlayers(world, pos);
            forceNextStatus = true;
        } else {
            if (rng.nextInt(10) == 0) {
                CoreUtil.summonItem(world, pos);
            }
            if (rng.nextInt(67) == 0) {
                CoreUtil.spawnEnderman(world, pos);
            }
        }
        if (forceNextStatus || rng.nextInt(27) == 0) {
            world.setBlockState(pos, state.with(STATUS, status - 1));
        }
        world.getPendingBlockTicks().scheduleTick(pos, this, 2);
    }



    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
    }

    public static Block create(Properties properties, Type type) {
        if (type == Type.DEAD) {
            return new Block(properties);
        }
        if (type == Type.ACTIVE) {
            return new Active(properties.tickRandomly().luminance(state -> 8), type);
        }
        return new CoreBlock(properties.luminance(state -> 5), type);
    }

    public enum Type {
        INACTIVE,
        ACTIVE,
        DEAD
    }

    private static class Active extends CoreBlock {
        private Active(Properties properties, Type type) {
            super(properties, type);
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
            builder.add(STATUS);
        }
    }
}
