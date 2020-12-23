/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.levelgen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public final class MnFeatureFactory {
    public static ConfiguredFeature<?, ?> patch(BlockClusterFeatureConfig.Builder config) {
        return Feature.RANDOM_PATCH.configure(config.build());
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
}
