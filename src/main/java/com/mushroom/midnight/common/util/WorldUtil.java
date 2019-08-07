package com.mushroom.midnight.common.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public class WorldUtil {
    @Nullable
    public static BlockPos findSurface(IWorld world, BlockPos pos, int maxSteps) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
        int steps = 0;
        while (!world.getBlockState(mutablePos).getMaterial().blocksMovement()) {
            mutablePos.move(Direction.DOWN);
            if (steps++ > maxSteps) {
                return null;
            }
        }
        mutablePos.move(Direction.UP);
        return mutablePos.toImmutable();
    }

    @Nonnull
    public static BlockPos findSurfaceOrInput(World world, BlockPos pos, int maxSteps) {
        BlockPos surface = findSurface(world, pos, maxSteps);
        return surface != null ? surface : pos;
    }

    public static BlockPos min(BlockPos a, BlockPos b) {
        return new BlockPos(Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
    }

    public static BlockPos min(Collection<BlockPos> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        for (BlockPos pos : positions) {
            if (pos.getX() < minX) {
                minX = pos.getX();
            }
            if (pos.getY() < minY) {
                minY = pos.getY();
            }
            if (pos.getZ() < minZ) {
                minZ = pos.getZ();
            }
        }
        return new BlockPos(minX, minY, minZ);
    }

    public static BlockPos max(BlockPos a, BlockPos b) {
        return new BlockPos(Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
    }

    public static BlockPos max(Collection<BlockPos> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        for (BlockPos pos : positions) {
            if (pos.getX() > maxX) {
                maxX = pos.getX();
            }
            if (pos.getY() > maxY) {
                maxY = pos.getY();
            }
            if (pos.getZ() > maxZ) {
                maxZ = pos.getZ();
            }
        }
        return new BlockPos(maxX, maxY, maxZ);
    }
}
