package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.AlgaeFeature;
import com.mushroom.midnight.common.world.feature.BogFungiFlowersFeature;
import com.mushroom.midnight.common.world.feature.BogweedFlowersFeature;
import com.mushroom.midnight.common.world.feature.BulbFungiFlowersFeature;
import com.mushroom.midnight.common.world.feature.CrystalClusterFeature;
import com.mushroom.midnight.common.world.feature.CrystalFlowersFeature;
import com.mushroom.midnight.common.world.feature.DeadLogFeature;
import com.mushroom.midnight.common.world.feature.FungiFlowersFeature;
import com.mushroom.midnight.common.world.feature.HeapFeature;
import com.mushroom.midnight.common.world.feature.MidnightDoublePlantFeature;
import com.mushroom.midnight.common.world.feature.MidnightOreFeature;
import com.mushroom.midnight.common.world.feature.MossFeature;
import com.mushroom.midnight.common.world.feature.NightstoneBoulderFeature;
import com.mushroom.midnight.common.world.feature.ScatteredPlantFeature;
import com.mushroom.midnight.common.world.feature.SpikeFeature;
import com.mushroom.midnight.common.world.feature.TallBogFungiFeature;
import com.mushroom.midnight.common.world.feature.TallFungiFeature;
import com.mushroom.midnight.common.world.feature.TrenchstoneBoulderFeature;
import com.mushroom.midnight.common.world.feature.config.CrystalClusterConfig;
import com.mushroom.midnight.common.world.feature.config.MidnightOreConfig;
import com.mushroom.midnight.common.world.feature.config.UniformCompositionConfig;
import com.mushroom.midnight.common.world.feature.structure.WellStructure;
import com.mushroom.midnight.common.world.feature.tree.DarkWillowTreeFeature;
import com.mushroom.midnight.common.world.feature.tree.DeadTreeFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeBogshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeBulbFungusFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeDewshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeNightshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeViridshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.MediumDewshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.MediumNightshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.MediumViridshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.ShadowrootTreeFeature;
import com.mushroom.midnight.common.world.feature.tree.SmallDewshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.SmallNightshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.SmallViridshroomFeature;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

// TODO: Register from correct event when Forge is fixed
@ObjectHolder(Midnight.MODID)
public class MidnightFeatures {
    // TODO
//    public static final IMidnightFeature UNSTABLE_BUSH_FEATURE = new PlantFeature(
//            MidnightBlocks.UNSTABLE_BUSH.getDefaultState(),
//            GeneratablePlant::canGenerate
//    ) {
//        @Override
//        public boolean placeFeature(World world, Random rand, BlockPos origin) {
//            Block block = rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED : (rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED : MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED);
//            BlockState state = block.getDefaultState().with(UnstableBushBloomedBlock.HAS_FRUIT, true);
//            if (this.predicate.canSpawn(world, origin, state)) {
//                setBlockAndNotifyAdequately(world, origin, state);
//                return true;
//            }
//            return false;
//        }
//    };
//
//    public static final IMidnightFeature[] LARGE_FUNGI_FEATURES = new TemplateFungiFeature[] {
//            new TemplateFungiFeature(
//                    MidnightBlocks.DEWSHROOM_STEM.getDefaultState(),
//                    MidnightBlocks.DEWSHROOM_HAT.getDefaultState()
//            ),
//            new TemplateFungiFeature(
//                    MidnightBlocks.NIGHTSHROOM_STEM.getDefaultState(),
//                    MidnightBlocks.NIGHTSHROOM_HAT.getDefaultState()
//            ),
//            new TemplateFungiFeature(
//                    MidnightBlocks.VIRIDSHROOM_STEM.getDefaultState(),
//                    MidnightBlocks.VIRIDSHROOM_HAT.getDefaultState()
//            )
//    };
//
//    public static final IMidnightFeature LARGE_BOGSHROOM_FEATURE = new LargeBogshroomFeature();
//
//    public static final IMidnightFeature LARGE_BULB_FUNGUS_FEATURE = new LargeBulbFungusFeature();
//
//    public static final IMidnightFeature BULB_FUNGUS_FEATURE = new PlantFeature(
//            MidnightBlocks.GLOB_FUNGUS.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature[] UNDERGROUND_FEATURES = new IMidnightFeature[] {
//            new PlantFeature(MidnightBlocks.TENDRILWEED.getDefaultState(), GeneratablePlant::canGenerate),
//            FUNGI_FEATURE,
//            new MidnightAbstractFeature() {
//                @Override
//                public boolean placeFeature(World world, Random random, BlockPos origin) {
//                    if (world.getBlockState(origin.down()).isFullBlock()) {
//                        world.setBlockState(origin, MidnightBlocks.STINGER_EGG.getDefaultState().with(PileOfEggsBlock.EGGS, random.nextInt(4) + 1), 2 | 16);
//                        return true;
//                    }
//                    return false;
//                }
//            }
//    };
    public static final AbstractTreeFeature<NoFeatureConfig> SHADOWROOT_TREE = new ShadowrootTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> DARK_WILLOW_TREE = new DarkWillowTreeFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> DEAD_TREE = new DeadTreeFeature(NoFeatureConfig::deserialize, ShelfAttachProcessor.FOREST_SHELF_BLOCKS);
    public static final Feature<NoFeatureConfig> BOG_DEAD_TREE = new DeadTreeFeature(NoFeatureConfig::deserialize, ShelfAttachProcessor.SHELF_BLOCKS);
    public static final Feature<NoFeatureConfig> DEAD_LOG = new DeadLogFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_BOGSHROOM = new LargeBogshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_BULB_FUNGUS = new LargeBulbFungusFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_NIGHTSHROOM = new LargeNightshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_DEWSHROOM = new LargeDewshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_VIRIDSHROOM = new LargeViridshroomFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature<NoFeatureConfig> MEDIUM_NIGHTSHROOM = new MediumNightshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> MEDIUM_DEWSHROOM = new MediumDewshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> MEDIUM_VIRIDSHROOM = new MediumViridshroomFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature<NoFeatureConfig> SMALL_NIGHTSHROOM = new SmallNightshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> SMALL_DEWSHROOM = new SmallDewshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> SMALL_VIRIDSHROOM = new SmallViridshroomFeature(NoFeatureConfig::deserialize);

    public static final Feature<NoFeatureConfig> SUAVIS = new ScatteredPlantFeature(NoFeatureConfig::deserialize, MidnightBlocks.SUAVIS.getDefaultState());
    public static final Feature<NoFeatureConfig> DECEITFUL_ALGAE = new AlgaeFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> DECEITFUL_MOSS = new MossFeature(NoFeatureConfig::deserialize);

    public static final Feature<DoublePlantConfig> DOUBLE_PLANT = new MidnightDoublePlantFeature(DoublePlantConfig::deserialize);
    public static final Feature<NoFeatureConfig> FUNGI_FLOWERS = new FungiFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> BOG_FUNGI_FLOWERS = new BogFungiFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> BOGWEED_FLOWERS = new BogweedFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> CRYSTAL_FLOWERS = new CrystalFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> BULB_FUNGI_FLOWERS = new BulbFungiFlowersFeature(NoFeatureConfig::deserialize);

    public static final Feature<NoFeatureConfig> TALL_FUNGI = new TallFungiFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> TALL_BOG_FUNGI = new TallBogFungiFeature(NoFeatureConfig::deserialize);

    public static final Feature<UniformCompositionConfig> HEAP = new HeapFeature(UniformCompositionConfig::deserialize);
    public static final Feature<UniformCompositionConfig> SPIKE = new SpikeFeature(UniformCompositionConfig::deserialize);
    public static final Feature<NoFeatureConfig> NIGHTSTONE_BOULDER = new NightstoneBoulderFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> TRENCHSTONE_BOULDER = new TrenchstoneBoulderFeature(NoFeatureConfig::deserialize);
    public static final Feature<CrystalClusterConfig> CRYSTAL_CLUSTER = new CrystalClusterFeature(CrystalClusterConfig::deserialize, 3, 4);
    public static final Feature<CrystalClusterConfig> CRYSTAL_SPIRE = new CrystalClusterFeature(CrystalClusterConfig::deserialize, 4, 13);

    public static final Feature<MidnightOreConfig> ORE = new MidnightOreFeature(MidnightOreConfig::deserialize);

    //structure
    public static final Feature<NoFeatureConfig> WELL = new WellStructure(NoFeatureConfig::deserialize);
    @SubscribeEvent
    public static void registerFeatures(IForgeRegistry<Feature<?>> event) {
        RegUtil.generic(event)
                .add("shadowroot_tree", SHADOWROOT_TREE)
                .add("dark_willow_tree", DARK_WILLOW_TREE)
                .add("dead_tree", DEAD_TREE)
                .add("bog_dead_tree", BOG_DEAD_TREE)
                .add("dead_log", DEAD_LOG)
                .add("large_bogshroom", LARGE_BOGSHROOM)
                .add("large_bulb_fungus", LARGE_BULB_FUNGUS)
                .add("large_nightshroom", LARGE_NIGHTSHROOM)
                .add("large_dewshroom", LARGE_DEWSHROOM)
                .add("large_viridshroom", LARGE_VIRIDSHROOM)
                .add("medium_nightshroom", MEDIUM_NIGHTSHROOM)
                .add("medium_dewshroom", MEDIUM_DEWSHROOM)
                .add("medium_viridshroom", MEDIUM_VIRIDSHROOM)
                .add("small_nightshroom", SMALL_NIGHTSHROOM)
                .add("small_dewshroom", SMALL_DEWSHROOM)
                .add("small_viridshroom", SMALL_VIRIDSHROOM)
                .add("suavis", SUAVIS)
                .add("deceitful_algae", DECEITFUL_ALGAE)
                .add("deceitful_moss", DECEITFUL_MOSS)
                .add("double_plant", DOUBLE_PLANT)
                .add("fungi_flowers", FUNGI_FLOWERS)
                .add("bog_fungi_flowers", BOG_FUNGI_FLOWERS)
                .add("tall_fungi", TALL_FUNGI)
                .add("tall_bog_fungi", TALL_BOG_FUNGI)
                .add("bogweed_flowers", BOGWEED_FLOWERS)
                .add("crystal_flowers", CRYSTAL_FLOWERS)
                .add("bulb_fungi_flowers", BULB_FUNGI_FLOWERS)
                .add("heap", HEAP)
                .add("spike", SPIKE)
                .add("nightstone_boulder", NIGHTSTONE_BOULDER)
                .add("trenchstone_boulder", TRENCHSTONE_BOULDER)
                .add("crystal_cluster", CRYSTAL_CLUSTER)
                .add("crystal_spire", CRYSTAL_SPIRE)
                .add("ore", ORE)
                .add("well", WELL);

        //I think it is necessary when generating the structure
        //Feature.STRUCTURES.put("Well".toLowerCase(Locale.ROOT), WELL);
        //Registry.register(Registry.STRUCTURE_FEATURE, new ResourceLocation(Midnight.MODID,"well"), WELL);
    }

}
