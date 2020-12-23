/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.world.levelgen.feature;

import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.*;

// Deprecated: this was vanilla style usage of features, but we create the configured features in BiomeFactories now
// We neither need to register them (the vanilla configured feature registry is not used at all so why would we
// register 'em)
@Deprecated
public final class MnCfgFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_PLAINS = register(
        "patch_grass_plains",
        Feature.RANDOM_PATCH.configure(Configs.GRASS)
                            .decorate(Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                            .decorate(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 5, 10)))
    );

    public static final ConfiguredFeature<?, ?> PATCH_GRASS_FOREST = register(
        "patch_grass_forest",
        Feature.RANDOM_PATCH.configure(Configs.GRASS)
                            .decorate(Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                            .repeat(2)
    );

    public static final ConfiguredFeature<?, ?> PATCH_TALL_GRASS_PLAINS = register(
        "patch_tall_grass_plains",
        Feature.RANDOM_PATCH.configure(Configs.TALL_GRASS)
                            .decorate(Placements.SPREAD_32_ABOVE)
                            .decorate(Placements.HEIGHTMAP)
                            .spreadHorizontally()
                            .decorate(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 0, 7)))
    );

    public static final ConfiguredFeature<?, ?> PATCH_DENSE_TALL_GRASS = register(
        "patch_dense_tall_grass",
        Feature.RANDOM_PATCH.configure(Configs.DENSE_TALL_GRASS)
                            .decorate(Placements.SPREAD_32_ABOVE)
                            .decorate(Placements.SQUARE_HEIGHTMAP)
                            .repeat(7)
    );

    public static final ConfiguredFeature<?, ?> PATCH_LITE_TALL_GRASS = register(
        "patch_lite_tall_grass",
        Feature.RANDOM_PATCH.configure(Configs.TALL_GRASS)
                            .decorate(Placements.SPREAD_32_ABOVE)
                            .decorate(Placements.SQUARE_HEIGHTMAP)
                            .repeat(2)
    );

    public static final ConfiguredFeature<?, ?> PATCH_GHOST_PLANT = register(
        "patch_ghost_plant",
        Feature.RANDOM_PATCH.configure(Configs.GHOST_PLANT)
                            .decorate(Placements.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                            .decorate(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 5, 10)))
    );

    public static final class Configs {
        public static final BlockClusterFeatureConfig GRASS = simpleCluster(States.GRASS).tries(32).build();
        public static final BlockClusterFeatureConfig DENSE_GRASS = simpleCluster(States.GRASS).tries(64).build();
        public static final BlockClusterFeatureConfig TALL_GRASS = doublePlantCluster(States.TALL_GRASS).tries(32).build();
        public static final BlockClusterFeatureConfig DENSE_TALL_GRASS = doublePlantCluster(States.TALL_GRASS).tries(64).build();
        public static final BlockClusterFeatureConfig GHOST_PLANT = simpleCluster(States.GHOST_PLANT).tries(32).build();

        public static BlockClusterFeatureConfig.Builder simpleCluster(BlockState state) {
            return new BlockClusterFeatureConfig.Builder(
                new SimpleBlockStateProvider(state),
                SimpleBlockPlacer.INSTANCE
            );
        }

        public static BlockClusterFeatureConfig.Builder doublePlantCluster(BlockState state) {
            return new BlockClusterFeatureConfig.Builder(
                new SimpleBlockStateProvider(state),
                DoublePlantBlockPlacer.INSTANCE
            );
        }
    }

    public static final class Placements {
        public static final ConfiguredPlacement<?> HEIGHTMAP = Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<?> TOP_SOLID_HEIGHTMAP = Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<?> HEIGHTMAP_WORLD_SURFACE = Placement.HEIGHTMAP_WORLD_SURFACE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<?> HEIGHTMAP_SPREAD_DOUBLE = Placement.HEIGHTMAP_SPREAD_DOUBLE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<?> SPREAD_32_ABOVE = Placement.SPREAD_32_ABOVE.configure(NoPlacementConfig.INSTANCE);
        public static final ConfiguredPlacement<?> SQUARE_HEIGHTMAP = HEIGHTMAP.spreadHorizontally();
        public static final ConfiguredPlacement<?> SQUARE_HEIGHTMAP_SPREAD_DOUBLE = HEIGHTMAP_SPREAD_DOUBLE.spreadHorizontally();
        public static final ConfiguredPlacement<?> SQUARE_TOP_SOLID_HEIGHTMAP = TOP_SOLID_HEIGHTMAP.spreadHorizontally();
    }

    public static final class States {
        public static final BlockState GRASS = MnBlocks.NIGHT_GRASS.getDefaultState();
        public static final BlockState TALL_GRASS = MnBlocks.TALL_NIGHT_GRASS.getDefaultState();
        public static final BlockState GHOST_PLANT = MnBlocks.GHOST_PLANT.getDefaultState();
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Midnight.resLoc(id), feature);
    }
}
