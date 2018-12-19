package com.mushroom.midnight.common.world.feature.config;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public class UndergroundPlacementConfig implements IPlacementConfig {
    private final int minCount;
    private final int maxCount;

    public UndergroundPlacementConfig(int minCount, int maxCount) {
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public UndergroundPlacementConfig(int count) {
        this(count, count);
    }

    @Override
    public void apply(World world, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
        int count = random.nextInt(this.maxCount - this.minCount + 1) + this.minCount;
        for (int i = 0; i < count; i++) {
            int offsetX = random.nextInt(16) + 8;
            int offsetZ = random.nextInt(16) + 8;
            int maxY = Math.min(world.getSeaLevel(), world.getHeight(offsetX, offsetZ));
            if (maxY <= 10) { continue; }
            generator.accept(new BlockPos(offsetX, random.nextInt(maxY - 10) + 10, offsetZ));
        }
    }
}
