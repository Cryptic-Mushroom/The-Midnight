/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 27
 */

package midnight.common.block;

import midnight.common.item.MnItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class CoreUtil {
    private static final Block BASE = Blocks.COBBLESTONE;
    private static final Block VALUABLE = Blocks.GOLD_BLOCK;

    public static boolean checkPattern(IBlockReader world, BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                int ax = Math.abs(x), az = Math.abs(z);
                boolean corner = ax == 1 && az == 1;

                BlockState lo = world.getBlockState(pos.add(x, -1, z));
                BlockState mid = world.getBlockState(pos.add(x, 0, z));
                BlockState hi = world.getBlockState(pos.add(x, 1, z));

                if (!lo.isIn(corner ? VALUABLE : BASE)) {
                    return false;
                }
                if (corner && !mid.isIn(BASE)) {
                    return false;
                }
                if (!corner && !hi.isIn(BASE)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean checkPlayers(ServerWorld world, BlockPos pos) {
        int totalPlayers = world.getPlayers().size();
        int nearbyPlayers = world.getPlayers(player -> {
            BlockPos r = pos.add(-player.getX(), -player.getY(), -player.getZ());
            return Math.max(r.getX(), r.getZ()) < 96;
        }).size();
        return totalPlayers == nearbyPlayers;
    }

    public static boolean checkDimension(ServerWorld world) {
        return world.getRegistryKey() == World.OVERWORLD;
    }

    public static boolean checkRange(BlockPos pos) {
        return pos.getY() >= 4 && pos.getY() < 256 - 32;
    }

    public static Status checkStatus(ServerWorld world, BlockPos pos) {
        if (!checkPattern(world, pos)) {
            return Status.NOT_VALID;
        }
        if (!checkDimension(world)) {
            return Status.WRONG_DIMENSION;
        }
        if (!checkRange(pos)) {
            return Status.OUT_OF_RANGE;
        }
        if (!checkPlayers(world, pos)) {
            return Status.OUT_OF_RANGE;
        }
        return Status.ACTIVATED;
    }

    public static void activatePattern(ServerWorld world, BlockPos pos) {
        world.setBlockState(pos.add(-1, -1, -1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(0, -1, -1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(1, -1, -1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(-1, -1, 0), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(0, -1, 0), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(1, -1, 0), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(-1, -1, 1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(0, -1, 1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(1, -1, 1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(-1, 0, -1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(1, 0, -1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(1, 0, 1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(-1, 0, 1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(0, 1, 0), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(0, 1, 1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(0, 1, -1), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(1, 1, 0), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
        world.setBlockState(pos.add(-1, 1, 0), MnBlocks.ACTIVE_NIGHTSTONE.getDefaultState());
    }

    public static void buildSpireTeleportPlayers(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int x = -8; x <= 8; x++) {
            for (int z = -8; z <= 8; z++) {
                int r = Math.max(Math.abs(x), Math.abs(z));

                int hoff = (int) (x / 2d + z / 2d);
                int h;
                if (r <= 3) {
                    h = 30 + hoff;
                } else if (r <= 5) {
                    h = 23 - hoff;
                } else {
                    h = 15 + hoff;
                }
                for (int y = -2; y <= h; y++) {
                    int r3 = Math.max(r, Math.abs(y));
                    if (r3 <= 1) {
                        continue;
                    }

                    BlockState state = MnBlocks.TRENCHSTONE.getDefaultState();

                    if (r <= 7 && y >= -1 && y <= 2) {
                        state = Blocks.AIR.getDefaultState();
                    }

                    mpos.setPos(pos).move(x, y, z);
                    world.setBlockState(mpos, state);
                }
            }
        }

        for (ServerPlayerEntity player : world.getPlayers()) {
            int x = 0, z = 0;
            while (Math.max(Math.abs(x), Math.abs(z)) <= 1) {
                x = world.rand.nextInt(7) * (world.rand.nextBoolean() ? -1 : 1);
                z = world.rand.nextInt(7) * (world.rand.nextBoolean() ? -1 : 1);
            }

            player.setPositionAndUpdate(pos.getX() + x + 0.5, pos.getY() - 1, pos.getZ() + z + 0.5);
        }
    }

    public static void detoriateSpire(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int x = -8; x <= 8; x++) {
            for (int z = -8; z <= 8; z++) {
                int r = Math.max(Math.abs(x), Math.abs(z));
                int hoff = (int) (x / 2d + z / 2d);
                int h;
                if (r <= 3) {
                    h = 30 + hoff;
                } else if (r <= 5) {
                    h = 23 - hoff;
                } else {
                    h = 15 + hoff;
                }
                for (int y = -2; y <= h; y++) {
                    mpos.setPos(pos).move(x, y, z);
                    if (world.getBlockState(mpos).isIn(MnBlocks.TRENCHSTONE)) {
                        if (world.rand.nextInt(3) == 0) {
                            world.setBlockState(mpos, Blocks.AIR.getDefaultState());
                        } else {
                            if (world.rand.nextInt(3) != 0) {
                                world.setBlockState(mpos, MnBlocks.DETORIATED_TRENCHSTONE.getDefaultState());
                            }
                        }
                    }
                }
            }
        }

        for (ServerPlayerEntity player : world.getPlayers()) {
            int x = 0, z = 0;
            while (Math.max(Math.abs(x), Math.abs(z)) <= 1) {
                x = world.rand.nextInt(7) * (world.rand.nextBoolean() ? -1 : 1);
                z = world.rand.nextInt(7) * (world.rand.nextBoolean() ? -1 : 1);
            }

            player.setPositionAndUpdate(pos.getX() + x + 0.5, pos.getY() - 1, pos.getZ() + z + 0.5);
        }
    }

    private static final Item[] ITEMS = {
        MnItems.ARCHAIC_SHARD,
        MnItems.GLOB_FUNGUS,
        MnItems.GLOB_FUNGUS_HAND,
        MnItems.RAW_SUAVIS,
        MnItems.BLOOMCRYSTAL,
        MnItems.VIRILUX,
        MnItems.ROUXE,
        MnItems.GEODE,
        MnItems.BOGSHROOM,
        MnItems.DEWSHROOM,
        MnItems.MISTSHROOM,
        MnItems.MOONSHROOM,
        MnItems.VIRIDSHROOM,
        MnItems.NIGHTSHROOM,
        MnItems.ROCKSHROOM_CLUMP,
        MnItems.GHOST_PLANT,
        Items.GOLD_INGOT
    };

    public static void summonItem(ServerWorld world, BlockPos pos) {
        int x = 0, z = 0;
        while (Math.max(Math.abs(x), Math.abs(z)) <= 1) {
            x = world.rand.nextInt(6) * (world.rand.nextBoolean() ? -1 : 1);
            z = world.rand.nextInt(6) * (world.rand.nextBoolean() ? -1 : 1);
        }
        double ox = world.rand.nextDouble() + x + pos.getX();
        double oz = world.rand.nextDouble() + z + pos.getZ();

        Item item = ITEMS[world.rand.nextInt(ITEMS.length)];

        ItemEntity e = new ItemEntity(world, ox, pos.getY() - world.rand.nextDouble() + 0.5, oz, new ItemStack(item));
        world.addEntity(e);
    }

    public static void spawnEnderman(ServerWorld world, BlockPos pos) {
        double x = 0, z = 0;
        while (Math.max(Math.abs(x), Math.abs(z)) <= 1.5) {
            x = world.rand.nextInt(6) + 0.5 * (world.rand.nextBoolean() ? -1 : 1);
            z = world.rand.nextInt(6) + 0.5 * (world.rand.nextBoolean() ? -1 : 1);
        }
        double ox = world.rand.nextDouble() + x + pos.getX();
        double oz = world.rand.nextDouble() + z + pos.getZ();

        EndermanEntity e = new EndermanEntity(EntityType.ENDERMAN, world);
        e.setPositionAndUpdate(ox, pos.getY() - 1, oz);
        world.addEntity(e);
    }

    public enum Status {
        NOT_VALID,
        WRONG_DIMENSION,
        OUT_OF_RANGE,
        MISSING_PLAYERS,
        ACTIVATED;

        public ITextComponent getText() {
            return new TranslationTextComponent("block.midnight.core.status_" + name().toLowerCase());
        }
    }
}
