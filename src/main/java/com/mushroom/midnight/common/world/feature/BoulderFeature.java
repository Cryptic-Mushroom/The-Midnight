package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public abstract class BoulderFeature extends Feature<NoFeatureConfig> {
    protected BoulderFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos origin, NoFeatureConfig config) {
        float baseRadius = this.getRadius(random);

        origin = origin.up(MathHelper.floor(baseRadius / 2.0F));

        this.generateBlob(world, random, origin, baseRadius);
        for (int i = 0; i < 2; i++) {
            int offsetX = random.nextInt(5) - 2;
            int offsetY = -random.nextInt(2);
            int offsetZ = random.nextInt(5) - 2;
            BlockPos center = origin.add(offsetX, offsetY, offsetZ);

            float radius = baseRadius + random.nextFloat() * 0.5F;
            this.generateBlob(world, random, center, radius);
        }

        return true;
    }

    private void generateBlob(IWorld world, Random random, BlockPos origin, float radius) {
        float radiusSquare = radius * radius;
        int radiusCeil = MathHelper.ceil(radius);

        BlockPos minPos = origin.add(-radiusCeil, -radiusCeil, -radiusCeil);
        BlockPos maxPos = origin.add(radiusCeil, radiusCeil, radiusCeil);

        BlockPos.getAllInBox(minPos, maxPos).forEach(pos -> {
            double distSquare = pos.distanceSq(origin);
            if (distSquare <= radiusSquare) {
                this.setBlockState(world, pos, this.getStateForPlacement(world, origin, pos, distSquare, radiusSquare, random));
            }
        });
    }

    protected abstract float getRadius(Random random);

    protected abstract BlockState getStateForPlacement(IWorld world, BlockPos origin, BlockPos pos, double distSquare, float radiusSquare, Random random);
}
