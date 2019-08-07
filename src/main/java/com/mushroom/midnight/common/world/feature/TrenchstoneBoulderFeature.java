package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class TrenchstoneBoulderFeature extends BoulderFeature {
    private static final float RADIUS = 2.0F;
    private static final float INNER_RADIUS_SQ = (RADIUS / 2.0F) * (RADIUS / 2.0F);

    public TrenchstoneBoulderFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    protected float getRadius(Random random) {
        return RADIUS;
    }

    @Override
    protected BlockState getStateForPlacement(IWorld world, BlockPos origin, BlockPos pos, double distSquare, float radiusSquare, Random random) {
        return distSquare <= INNER_RADIUS_SQ && random.nextFloat() < 0.1f ? MidnightBlocks.ARCHAIC_ORE.getDefaultState() : MidnightBlocks.TRENCHSTONE.getDefaultState();
    }
}
