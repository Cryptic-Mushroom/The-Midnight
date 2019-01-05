package com.mushroom.midnight.common.world.feature.config;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public class ParcelPlacementConfig implements IPlacementConfig {
    private final int minCount, maxCount;
    private final float chance;

    public ParcelPlacementConfig(int minCount, int maxCount, float chance) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.chance = chance;
    }

    @Override
    public void apply(World world, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
        if (random.nextFloat() > chance) {
            return;
        }
        int offsetX = random.nextInt(16) + 8;
        int offsetZ = random.nextInt(16) + 8;
        BlockPos pos = chunkOrigin.add(offsetX, 0, offsetZ);
        int maxY = world.getHeight(pos).getY() + 32;
        if (maxY > 0) {
            placeParcel(world, random, pos.up(random.nextInt(maxY)), generator);
        }
    }

    private void placeParcel(World world, Random random, BlockPos pos, Consumer<BlockPos> generator) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
        IBlockState state;
        while (mutablePos.getY() > 0) {
            state = world.getBlockState(mutablePos);
            if (!state.getBlock().isAir(state, world, mutablePos) && !state.getBlock().isLeaves(state, world, mutablePos)) {
                break;
            }
            mutablePos.move(EnumFacing.DOWN);
        }
        pos = mutablePos.toImmutable();

        int count = minCount;
        if (minCount != maxCount) {
            count = random.nextInt(maxCount - minCount + 1) + minCount;
        }
        for (int i = 0; i < count; i++) {
            int offsetX = random.nextInt(8) - random.nextInt(8);
            int offsetZ = random.nextInt(8) - random.nextInt(8);

            mutablePos.setPos(pos.getX() + offsetX, Math.max(1, pos.getY() - 1), pos.getZ() + offsetZ);
            boolean isValid;
            while ((isValid = mutablePos.getY() <= pos.getY() + 1) && !world.isAirBlock(mutablePos)) {
                mutablePos.move(EnumFacing.UP);
            }
            if (isValid) {
                generator.accept(mutablePos.toImmutable());
            }
        }
    }
}
