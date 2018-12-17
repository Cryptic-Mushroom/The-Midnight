package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.block.BlockBladeshroom;
import com.mushroom.midnight.common.entity.creature.EntityHunter;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.feature.BoulderFeature;
import com.mushroom.midnight.common.world.feature.CrystalClusterFeature;
import com.mushroom.midnight.common.world.feature.DarkWillowTreeFeature;
import com.mushroom.midnight.common.world.feature.DeadLogFeature;
import com.mushroom.midnight.common.world.feature.DeadTreeFeature;
import com.mushroom.midnight.common.world.feature.DoubleFungiFeature;
import com.mushroom.midnight.common.world.feature.DoublePlantFeature;
import com.mushroom.midnight.common.world.feature.FungiFeature;
import com.mushroom.midnight.common.world.feature.GourdFeature;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.LargeFungiFeature;
import com.mushroom.midnight.common.world.feature.MossFeature;
import com.mushroom.midnight.common.world.feature.PlantFeature;
import com.mushroom.midnight.common.world.feature.ShadowrootTreeFeature;
import com.mushroom.midnight.common.world.feature.SpikeFeature;
import com.mushroom.midnight.common.world.feature.config.ScatterPlacementConfig;
import com.mushroom.midnight.common.world.feature.config.SurfacePlacementConfig;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;
import java.util.function.Consumer;

public class MidnightBiomeConfigs {
    public static final IMidnightFeature SHADOWROOT_TREE_FEATURE = new ShadowrootTreeFeature();
    public static final IMidnightFeature DARK_WILLOW_TREE_FEATURE = new DarkWillowTreeFeature();
    public static final IMidnightFeature DEAD_TREE_FEATURE = new DeadTreeFeature();
    public static final IMidnightFeature DEAD_LOG_FEATURE = new DeadLogFeature();

    public static final IMidnightFeature TALL_GRASS_FEATURE = new PlantFeature(
            ModBlocks.TALL_MIDNIGHT_GRASS.getDefaultState(),
            ((BlockBush) ModBlocks.TALL_MIDNIGHT_GRASS)::canBlockStay
    );

    public static final IMidnightFeature DOUBLE_GRASS_FEATURE = new DoublePlantFeature(
            ModBlocks.DOUBLE_MIDNIGHT_GRASS.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_MIDNIGHT_GRASS.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature LUMEN_FEATURE = new PlantFeature(
            ModBlocks.LUMEN_BUD.getDefaultState(),
            ((BlockBush) ModBlocks.LUMEN_BUD)::canBlockStay
    );

    public static final IMidnightFeature BLADESHROOM_FEATURE = new PlantFeature(
            ModBlocks.BLADESHROOM.getDefaultState().withProperty(BlockBladeshroom.STAGE, BlockBladeshroom.Stage.CAPPED),
            ((BlockBush) ModBlocks.BLADESHROOM)::canBlockStay
    );

    public static final IMidnightFeature SUAVIS_FEATURE = new GourdFeature(
            ModBlocks.SUAVIS.getDefaultState(),
            ModBlocks.MIDNIGHT_GRASS,
            2
    );

    public static final IMidnightFeature DECEITFUL_ALGAE_FEATURE = new PlantFeature(
            ModBlocks.DECEITFUL_ALGAE.getDefaultState(),
            (world, pos, state) -> ModBlocks.DECEITFUL_ALGAE.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature DECEITFUL_MOSS_FEATURE = new MossFeature(ModBlocks.DECEITFUL_MOSS.getDefaultState());

    public static final IMidnightFeature BOGWEED_FEATURE = new PlantFeature(
            ModBlocks.BOGWEED.getDefaultState(),
            ((BlockBush) ModBlocks.BOGWEED)::canBlockStay
    );

    public static final IMidnightFeature GHOST_PLANT_FEATURE = new PlantFeature(
            ModBlocks.GHOST_PLANT.getDefaultState(),
            ((BlockBush) ModBlocks.GHOST_PLANT)::canBlockStay
    );

    public static final IMidnightFeature FINGERED_GRASS_FEATURE = new PlantFeature(
            ModBlocks.FINGERED_GRASS.getDefaultState(),
            ((BlockBush) ModBlocks.FINGERED_GRASS)::canBlockStay
    );

    public static final IMidnightFeature DOUBLE_LUMEN_FEATURE = new DoublePlantFeature(
            ModBlocks.DOUBLE_LUMEN_BUD.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_LUMEN_BUD.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature FUNGI_FEATURE = new FungiFeature();
    public static final IMidnightFeature DOUBLE_FUNGI_FEATURE = new DoubleFungiFeature();

    public static final IMidnightFeature CRYSTAL_FLOWER_FEATURE = new PlantFeature(
            ModBlocks.CRYSTAL_FLOWER.getDefaultState(),
            ((BlockBush) ModBlocks.CRYSTAL_FLOWER)::canBlockStay
    );

    public static final IMidnightFeature[] LARGE_FUNGI_FEATURES = new LargeFungiFeature[] {
            new LargeFungiFeature(
                    ModBlocks.DEWSHROOM_STEM.getDefaultState(),
                    ModBlocks.DEWSHROOM_HAT.getDefaultState()
            ),
            new LargeFungiFeature(
                    ModBlocks.NIGHTSHROOM_STEM.getDefaultState(),
                    ModBlocks.NIGHTSHROOM_HAT.getDefaultState()
            ),
            new LargeFungiFeature(
                    ModBlocks.VIRIDSHROOM_STEM.getDefaultState(),
                    ModBlocks.VIRIDSHROOM_HAT.getDefaultState()
            )
    };

    public static final IMidnightFeature BLOOMCRYSTAL_FEATURE = new CrystalClusterFeature(3, 4,
            ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
            ModBlocks.BLOOMCRYSTAL.getDefaultState()
    );

    public static final IMidnightFeature BLOOMCRYSTAL_SPIRE_FEATURE = new CrystalClusterFeature(4, 13,
            ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
            ModBlocks.BLOOMCRYSTAL.getDefaultState()
    );

    public static final IMidnightFeature NIGHTSTONE_BOULDER_FEATURE = new BoulderFeature(2) {
        @Override
        protected IBlockState getStateForPlacement(World world, BlockPos origin, BlockPos pos, double dist, float radiusSquare, Random random) {
            return ModBlocks.NIGHTSTONE.getDefaultState();
        }
    };
    public static final IMidnightFeature NIGHTSTONE_SPIKE_FEATURE = new SpikeFeature(ModBlocks.NIGHTSTONE.getDefaultState());

    public static final IMidnightFeature TRENCHSTONE_BOULDER_FEATURE = new BoulderFeature(2) {
        private final float radiusSquareIn = radius <= 1f ? 0f : (radius - 1f) * (radius - 1f);
        @Override
        protected IBlockState getStateForPlacement(World world, BlockPos origin, BlockPos pos, double dist, float radiusSquare, Random random) {
            return dist <= radiusSquareIn && random.nextFloat() < 0.1f ? ModBlocks.ARCHAIC_ORE.getDefaultState() : ModBlocks.TRENCHSTONE.getDefaultState();
        }
    };

    public static final SurfaceConfig ROCKY_SURFACE_CONFIG = new SurfaceConfig()
            .withTopState(ModBlocks.NIGHTSTONE.getDefaultState())
            .withFillerState(ModBlocks.NIGHTSTONE.getDefaultState())
            .withWetState(ModBlocks.NIGHTSTONE.getDefaultState());

    public static final MidnightBiomeConfig VEGETATED_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(6, 64))
            .withFeature(DOUBLE_GRASS_FEATURE, new ScatterPlacementConfig(3, 32))
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(GHOST_PLANT_FEATURE, new ScatterPlacementConfig(3, 6))
            .build();

    public static final MidnightBiomeConfig ROCKY_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(TRENCHSTONE_BOULDER_FEATURE, new SurfacePlacementConfig(-3, 1))
            .withFeature(GHOST_PLANT_FEATURE, new ScatterPlacementConfig(3, 6))
            .withSurface(ROCKY_SURFACE_CONFIG)
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 1, 0, 2))
            .build();

    public static final MidnightBiomeConfig BLACK_RIDGE_CONFIG = MidnightBiomeConfig.builder(ROCKY_CONFIG)
            .build();

    public static final MidnightBiomeConfig OBSCURED_PEAKS_CONFIG = MidnightBiomeConfig.builder(ROCKY_CONFIG)
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(1, 16))
            .build();

    public static final MidnightBiomeConfig VIGILANT_FOREST_CONFIG = MidnightBiomeConfig.builder(VEGETATED_CONFIG)
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(8))
            .withFeature(SUAVIS_FEATURE, new ScatterPlacementConfig(1, 8))
            .withFeature(DEAD_LOG_FEATURE, new SurfacePlacementConfig(6))
            .withFeature(DEAD_TREE_FEATURE, new SurfacePlacementConfig(-5, 1))
            .withMonster(new Biome.SpawnListEntry(EntityRifter.class, 1, 0, 1))
            .build();

    public static final MidnightBiomeConfig FUNGI_FOREST_CONFIG = MidnightBiomeConfig.builder(VEGETATED_CONFIG)
            .withFeature(LARGE_FUNGI_FEATURES, new SurfacePlacementConfig(6))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(6, 16))
            .withFeature(DOUBLE_FUNGI_FEATURE, new ScatterPlacementConfig(4, 8))
            .withFeature(BLADESHROOM_FEATURE, new ScatterPlacementConfig(1, 32))
            .withMonster(new Biome.SpawnListEntry(EntityRifter.class, 1, 0, 1))
            .withGrassColor(0x8489B5)
            .build();

    public static final MidnightBiomeConfig CRYSTAL_SPIRES_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(SHADOWROOT_TREE_FEATURE, new SurfacePlacementConfig(-3, 1))
            .withFeature(BLOOMCRYSTAL_FEATURE, new SurfacePlacementConfig(3))
            .withFeature(BLOOMCRYSTAL_SPIRE_FEATURE, new SurfacePlacementConfig(2, 3))
            .withFeature(LUMEN_FEATURE, new ScatterPlacementConfig(1, 32))
            .withFeature(DOUBLE_LUMEN_FEATURE, new ScatterPlacementConfig(1, 16))
            .withFeature(CRYSTAL_FLOWER_FEATURE, new ScatterPlacementConfig(5, 12))
            .withFeature(GHOST_PLANT_FEATURE, new ScatterPlacementConfig(3, 6))
            .withGrassColor(0xBAA3C6)
            .build();

    public static final MidnightBiomeConfig WARPED_FIELDS_CONFIG = MidnightBiomeConfig.builder(VEGETATED_CONFIG)
            .withFeature(SHADOWROOT_TREE_FEATURE, new SurfacePlacementConfig(-3, 1))
            .withFeature(CRYSTAL_FLOWER_FEATURE, new ScatterPlacementConfig(1, 12))
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 1, 0, 2))
            .withDensityScale(0.5F)
            .withRidgeWeight(0.0F)
            .wet()
            .build();

    public static final MidnightBiomeConfig DECEITFUL_BOG_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(DEAD_TREE_FEATURE, new SurfacePlacementConfig(-1, 1))
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DARK_WILLOW_TREE_FEATURE
            }, new SurfacePlacementConfig(4))
            .withFeature(BOGWEED_FEATURE, new ScatterPlacementConfig(2, 32))
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(5, 32))
            .withFeature(DOUBLE_GRASS_FEATURE, new ScatterPlacementConfig(3, 32))
            .withFeature(DECEITFUL_MOSS_FEATURE, new ScatterPlacementConfig(16, 32))
            .withFeature(FUNGI_FEATURE, new ScatterPlacementConfig(2, 4))
            .withFeature(DEAD_LOG_FEATURE, new SurfacePlacementConfig(5))
            .withFeature(DECEITFUL_ALGAE_FEATURE, new ScatterPlacementConfig(10, 20))
            .withFeature(GHOST_PLANT_FEATURE, new ScatterPlacementConfig(3, 6))
            .withGrassColor(0x8893AD)
            .withRidgeWeight(0.0F)
            .wet()
            .build();

    public static final MidnightBiomeConfig NIGHT_PLAINS_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(TALL_GRASS_FEATURE, new ScatterPlacementConfig(2, 32))
            .withFeature(GHOST_PLANT_FEATURE, new ScatterPlacementConfig(3, 6))
            .withFeature(FINGERED_GRASS_FEATURE, new ScatterPlacementConfig(8, 16) {
                @Override
                public void apply(World world, Random random, BlockPos chunkOrigin, Consumer<BlockPos> generator) {
                    if (world.rand.nextFloat() < 0.2f) {
                        super.apply(world, random, chunkOrigin, generator);
                    }
                }
            })
            .withFeature(new IMidnightFeature[] {
                    SHADOWROOT_TREE_FEATURE,
                    DEAD_TREE_FEATURE
            }, new SurfacePlacementConfig(-5, 1))
            .withFeature(new IMidnightFeature[] {
                    NIGHTSTONE_BOULDER_FEATURE,
                    NIGHTSTONE_SPIKE_FEATURE
            }, new SurfacePlacementConfig(-3, 1))
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 1, 0, 2))
            .withRidgeWeight(0.0F)
            .withGrassColor(0xBAA3C6)
            .build();

    public static final MidnightBiomeConfig BLACK_PLATEAU_CONFIG = MidnightBiomeConfig.builder(ROCKY_CONFIG)
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 1, 0, 2))
            .build();

    public static final MidnightBiomeConfig PHANTASMAL_VALLEY_CONFIG = MidnightBiomeConfig.builder()
            .withFeature(GHOST_PLANT_FEATURE, new ScatterPlacementConfig(3, 6))
            .withMonster(new Biome.SpawnListEntry(EntityHunter.class, 1, 0, 2))
            .withRidgeWeight(0.0F)
            .wet()
            .build();
}
