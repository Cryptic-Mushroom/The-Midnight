package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BladeshroomBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightFeatures;
import com.mushroom.midnight.common.registry.MidnightPlacements;
import com.mushroom.midnight.common.world.feature.config.CrystalClusterConfig;
import com.mushroom.midnight.common.world.feature.config.MidnightOreConfig;
import com.mushroom.midnight.common.world.feature.config.UniformCompositionConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GrassFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SingleRandomFeature;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class MidnightBiomeConfigurator {
    public static void addGlobalOres(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
                MidnightFeatures.ORE, new MidnightOreConfig(MidnightBlocks.DARK_PEARL_ORE.getDefaultState(), 14),
                Placement.COUNT_RANGE, new CountRangeConfig(8, 0, 0, 56)
        ));

        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
                MidnightFeatures.ORE, new MidnightOreConfig(MidnightBlocks.TENEBRUM_ORE.getDefaultState(), 4),
                Placement.COUNT_RANGE, new CountRangeConfig(6, 0, 0, 56)
        ));

        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
                MidnightFeatures.ORE, new MidnightOreConfig(MidnightBlocks.NAGRILITE_ORE.getDefaultState(), 4),
                Placement.COUNT_RANGE, new CountRangeConfig(4, 0, 0, 24)
        ));

        biome.add(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
                MidnightFeatures.ORE, new MidnightOreConfig(MidnightBlocks.EBONITE_ORE.getDefaultState(), 6),
                Placement.COUNT_RANGE, new CountRangeConfig(4, 0, 0, 24)
        ));
    }

    public static void addGlobalFeatures(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(
                MidnightFeatures.HEAP, new UniformCompositionConfig(MidnightBlocks.ROCKSHROOM.getDefaultState()),
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(100)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.GHOST_PLANT.getDefaultState()),
                MidnightPlacements.COUNT_CHANCE_SURFACE_DOUBLE, new HeightWithChanceConfig(4, 0.3F)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.DRAGON_NEST.getDefaultState()),
                MidnightPlacements.DRAGON_NEST, IPlacementConfig.NO_PLACEMENT_CONFIG
        ));

        // TODO
//      .withFeature(FeatureSorting.LAST, UNSTABLE_BUSH_FEATURE, new ParcelPlacementConfig(6, 10, 0.2f))
//      .withFeature(FeatureSorting.LAST, UNDERGROUND_FEATURES, new UndergroundPlacementConfig(1, 3, 10, 50))
    }

    public static void addLumen(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.LUMEN_BUD.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(1)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DOUBLE_PLANT, new DoublePlantConfig(MidnightBlocks.DOUBLE_LUMEN_BUD.getDefaultState()),
                MidnightPlacements.CHANCE_SURFACE_DOUBLE, new ChanceConfig(3)
        ));
    }

    public static void addSmallFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.FUNGI_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(2)
        ));
    }

    public static void addDenseSmallFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.FUNGI_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(6)
        ));
    }

    public static void addSmallBogFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.BOG_FUNGI_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(2)
        ));
    }

    public static void addTallFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.TALL_FUNGI, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(1)
        ));
    }

    public static void addDenseTallFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.TALL_FUNGI, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(6)
        ));
    }

    public static void addTallBogFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.TALL_BOG_FUNGI, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(6)
        ));
    }

    public static void addCrystalFlowers(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.CRYSTAL_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(8)
        ));
    }

    public static void addCrystalClusters(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(
                MidnightFeatures.CRYSTAL_CLUSTER, new CrystalClusterConfig(MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), MidnightBlocks.BLOOMCRYSTAL.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE, new FrequencyConfig(3)
        ));

        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(
                MidnightFeatures.CRYSTAL_SPIRE, new CrystalClusterConfig(MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(), MidnightBlocks.BLOOMCRYSTAL.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE, new FrequencyConfig(2)
        ));
    }

    public static void addRouxeClusters(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(
                MidnightFeatures.CRYSTAL_CLUSTER, new CrystalClusterConfig(MidnightBlocks.ROUXE_ROCK.getDefaultState(), MidnightBlocks.ROUXE.getDefaultState()),
                MidnightPlacements.COUNT_UNDERGROUND, new FrequencyConfig(5)
        ));
    }

    public static void addGrasses(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.GRASS, new GrassFeatureConfig(MidnightBlocks.GRASS.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(4)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DOUBLE_PLANT, new DoublePlantConfig(MidnightBlocks.TALL_GRASS.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(3)
        ));
    }

    public static void addFingeredGrass(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.GRASS, new GrassFeatureConfig(MidnightBlocks.FINGERED_GRASS.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(1)
        ));
    }

    public static void addTrenchstoneBoulders(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(
                MidnightFeatures.TRENCHSTONE_BOULDER, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(3)
        ));
    }

    public static void addNightstoneSpikesAndBoulders(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(
                Feature.RANDOM_BOOLEAN_SELECTOR, new TwoFeatureChoiceConfig(
                        MidnightFeatures.NIGHTSTONE_BOULDER, IFeatureConfig.NO_FEATURE_CONFIG,
                        MidnightFeatures.SPIKE, new UniformCompositionConfig(MidnightBlocks.NIGHTSTONE.getDefaultState())
                ),
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(4)
        ));
    }

    public static void addSparseShadowrootTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.RANDOM_BOOLEAN_SELECTOR, new TwoFeatureChoiceConfig(
                        MidnightFeatures.SHADOWROOT_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                        MidnightFeatures.DEAD_TREE, IFeatureConfig.NO_FEATURE_CONFIG
                ),
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(5)
        ));
    }

    public static void addSparseSuavis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.SUAVIS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.CHANCE_SURFACE_DOUBLE, new ChanceConfig(4)
        ));
    }

    public static void addCommonSuavis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.SUAVIS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(8)
        ));
    }

    public static void addSparseDeadTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DEAD_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(6)
        ));
    }

    public static void addVioleafs(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.VIOLEAF.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(2)
        ));
    }

    public static void addRunebushes(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.GRASS, new GrassFeatureConfig(MidnightBlocks.RUNEBUSH.getDefaultState()),
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(32)
        ));
    }

    public static void addBogweed(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.BOGWEED_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_32, new FrequencyConfig(1)
        ));
    }

    public static void addDeadLogs(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DEAD_LOG, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE, new FrequencyConfig(5)
        ));
    }

    public static void addVigilantForestTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.RANDOM_BOOLEAN_SELECTOR, new TwoFeatureChoiceConfig(
                        MidnightFeatures.SHADOWROOT_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                        MidnightFeatures.DARK_WILLOW_TREE, IFeatureConfig.NO_FEATURE_CONFIG
                ),
                MidnightPlacements.COUNT_SURFACE, new FrequencyConfig(8)
        ));
    }

    public static void addBogTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.RANDOM_BOOLEAN_SELECTOR, new TwoFeatureChoiceConfig(
                        MidnightFeatures.SHADOWROOT_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                        MidnightFeatures.DARK_WILLOW_TREE, IFeatureConfig.NO_FEATURE_CONFIG
                ),
                MidnightPlacements.COUNT_SURFACE, new FrequencyConfig(4)
        ));
    }

    public static void addBogDeadTrees(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.BOG_DEAD_TREE, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(3)
        ));
    }

    public static void addLargeBogshrooms(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.LARGE_BOGSHROOM, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(3)
        ));
    }

    public static void addLargeFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.SIMPLE_RANDOM_SELECTOR, new SingleRandomFeature(
                        new Feature[] { MidnightFeatures.LARGE_NIGHTSHROOM, MidnightFeatures.LARGE_DEWSHROOM, MidnightFeatures.LARGE_VIRIDSHROOM },
                        new IFeatureConfig[] { IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG }
                ),
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(2)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.SIMPLE_RANDOM_SELECTOR, new SingleRandomFeature(
                        new Feature[] { MidnightFeatures.MEDIUM_NIGHTSHROOM, MidnightFeatures.MEDIUM_DEWSHROOM, MidnightFeatures.MEDIUM_VIRIDSHROOM },
                        new IFeatureConfig[] { IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG }
                ),
                MidnightPlacements.COUNT_SURFACE, new FrequencyConfig(1)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.SIMPLE_RANDOM_SELECTOR, new SingleRandomFeature(
                        new Feature[] { MidnightFeatures.SMALL_NIGHTSHROOM, MidnightFeatures.SMALL_DEWSHROOM, MidnightFeatures.SMALL_VIRIDSHROOM },
                        new IFeatureConfig[] { IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG }
                ),
                MidnightPlacements.COUNT_SURFACE, new FrequencyConfig(3)
        ));
    }

    public static void addBladeshrooms(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                Feature.BUSH, new BushConfig(MidnightBlocks.BLADESHROOM.getDefaultState().with(BladeshroomBlock.STAGE, BladeshroomBlock.Stage.CAPPED)),
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(1)
        ));
    }

    public static void addAlgaeAndMoss(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DECEITFUL_ALGAE, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(10)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.DECEITFUL_MOSS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_SURFACE_DOUBLE, new FrequencyConfig(16)
        ));
    }

    public static void addUndergroundSmallFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.FUNGI_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_UNDERGROUND_32, new FrequencyConfig(6)
        ));
    }

    public static void addUndergroundTallFungis(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.TALL_FUNGI, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_UNDERGROUND_32, new FrequencyConfig(6)
        ));
    }

    public static void addUndergroundBulbFungi(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.BULB_FUNGI_FLOWERS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_UNDERGROUND_32, new FrequencyConfig(7)
        ));

        biome.add(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(
                MidnightFeatures.LARGE_BULB_FUNGUS, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.COUNT_UNDERGROUND, new FrequencyConfig(3)
        ));
    }

    public static void addWell(ConfigurableBiome biome) {
        biome.add(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(
                MidnightFeatures.WELL, IFeatureConfig.NO_FEATURE_CONFIG,
                MidnightPlacements.CHANCE_SURFACE, new ChanceConfig(60))
        );
    }

    public static void addStandardCreatureSpawns(ConfigurableBiome biome) {
        addCreature(biome, MidnightEntities.NIGHTSTAG, 100, 1, 3);
    }

    public static void addStandardMonsterSpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.RIFTER, 100, 1, 2);
        addMonster(biome, EntityType.ENDERMAN, 10, 4, 4);
    }

    public static void addRockySpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.HUNTER, 5, 1, 2);
    }

    public static void addUndergroundSpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.STINGER, 100, 2, 4);
        addMonster(biome, EntityType.ENDERMAN, 10, 4, 4);
    }

    public static void addForestSpawns(ConfigurableBiome biome) {
        addMonster(biome, MidnightEntities.SKULK, 100, 1, 2);
        addCreature(biome, MidnightEntities.SHADE_SQUIRREL, 60, 2, 3);
    }

    private static void addCreature(ConfigurableBiome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        biome.add(EntityClassification.CREATURE, new Biome.SpawnListEntry(type, weight, minGroupSize, maxGroupSize));
    }

    private static void addMonster(ConfigurableBiome biome, EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        biome.add(Midnight.MIDNIGHT_MOB, new Biome.SpawnListEntry(type, weight, minGroupSize, maxGroupSize));
    }
}
