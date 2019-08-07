package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.block.UnstableBushBloomedBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightTags;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class UnstableBushFeature extends Feature<NoFeatureConfig> {
    private static final BlockState[] BUSHES = new BlockState[] {
            MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED.getDefaultState().with(UnstableBushBloomedBlock.HAS_FRUIT, true),
            MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED.getDefaultState().with(UnstableBushBloomedBlock.HAS_FRUIT, true),
            MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED.getDefaultState().with(UnstableBushBloomedBlock.HAS_FRUIT, true)
    };

    public UnstableBushFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos origin, NoFeatureConfig config) {
        boolean result = false;

        for (int i = 0; i < 10; i++) {
            BlockPos pos = origin.add(
                    rand.nextInt(8) - rand.nextInt(8),
                    rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(8) - rand.nextInt(8)
            );

            BlockState state = BUSHES[rand.nextInt(BUSHES.length)];

            if (world.isAirBlock(pos) && world.getBlockState(pos.down()).isIn(MidnightTags.Blocks.PLANTABLE_GROUNDS)) {
                world.setBlockState(pos, state, Constants.BlockFlags.NOTIFY_LISTENERS);
                result = true;
            }
        }

        return result;
    }
}
