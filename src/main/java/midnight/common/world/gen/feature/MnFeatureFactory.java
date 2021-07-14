/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public abstract class MnFeatureFactory {
    public static ConfiguredFeature<?, ?> patch(BlockClusterFeatureConfig.Builder config) {
        return Feature.RANDOM_PATCH.configured(config.build());
    }

    public static ConfiguredFeature<?, ?> simplePatch(BlockState state, int tries) {
        return patch(simplePatchCfg(state).tries(tries));
    }

    public static ConfiguredFeature<?, ?> doublePlantPatch(BlockState state, int tries) {
        return patch(doublePlantPatchCfg(state).tries(tries));
    }

    public static BlockClusterFeatureConfig.Builder simplePatchCfg(BlockState state) {
        return new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(state),
            SimpleBlockPlacer.INSTANCE
        );
    }

    public static BlockClusterFeatureConfig.Builder doublePlantPatchCfg(BlockState state) {
        return new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(state),
            DoublePlantBlockPlacer.INSTANCE
        );
    }
    
    public static ConfiguredFeature<?, ?> deadLog(Block log) {
        return MnFeatures.DEAD_LOG.configured(new DeadLogFeatureConfig(
            new SimpleBlockStateProvider(log.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new SimpleBlockStateProvider(log.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))
        ));
    }
}
