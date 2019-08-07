package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class NightstoneBoulderFeature extends BoulderFeature {
    public NightstoneBoulderFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    protected float getRadius(Random random) {
        return 2.0F;
    }

    @Override
    protected BlockState getStateForPlacement(IWorld world, BlockPos origin, BlockPos pos, double distSquare, float radiusSquare, Random random) {
        return MidnightBlocks.NIGHTSTONE.getDefaultState();
    }
}
