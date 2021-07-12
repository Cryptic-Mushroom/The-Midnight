/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.world.levelgen.feature;

import java.util.ArrayList;
import java.util.List;

import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import midnight.common.block.SuavisBlock;
import midnight.common.block.VioleafBlock;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class MnFeatures {
    private static final List<Feature<?>> REGISTRY = new ArrayList<>();

    public static final Feature<DeadLogFeatureConfig> DEAD_LOG = feature("dead_log", new DeadLogFeature());


    public static void registerFeatures(IForgeRegistry<Feature<?>> registry) {
        REGISTRY.forEach(registry::register);
    }

    private static <FC extends IFeatureConfig, F extends Feature<FC>> F feature(String id, F feature) {
        feature.setRegistryName(Midnight.id(id));
        REGISTRY.add(feature);
        return feature;
    }
    
    public static final class ConfiguredFeatures {
    	
    	public static final ConfiguredFeature<?, ?> GRASS_PLAIN =
    	        MnFeatureFactory.simplePatch(MnBlocks.NIGHT_GRASS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8, 5, 10)));
    	
    	public static final ConfiguredFeature<?, ?> GRASS_FOREST =
    	        MnFeatureFactory.simplePatch(MnBlocks.NIGHT_GRASS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(2);
    	
    	public static final ConfiguredFeature<?, ?> TALL_GRASS_PLAIN =
    			MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_NIGHT_GRASS.defaultBlockState(), 32)
    			.decorated(Features.Placements.ADD_32)
    			.decorated(Features.Placements.HEIGHTMAP_SQUARE)
    			.decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8, 0, 7)));
    	
    	public static final ConfiguredFeature<?, ?> TALL_GRASS_FOREST =
    			MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_NIGHT_GRASS.defaultBlockState(), 32)
    			.decorated(Features.Placements.ADD_32)
    			.decorated(Features.Placements.HEIGHTMAP_SQUARE);
    	
    	public static final ConfiguredFeature<?, ?> GHOST_PLANTS =
    	        MnFeatureFactory.simplePatch(MnBlocks.GHOST_PLANT.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(3)
    	        .chance(2);
    			
    	public static final ConfiguredFeature<?, ?> GLOB_FUNGUS =
    			MnFeatureFactory.simplePatch(MnBlocks.GLOB_FUNGUS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(5)
    	        .chance(3);
    	
    	public static final ConfiguredFeature<?, ?> BRISTLY_GRASS =
    			MnFeatureFactory.simplePatch(MnBlocks.BRISTLY_GRASS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(3)
    	        .chance(7);
    	
    	public static final ConfiguredFeature<?, ?> CRYSTALOTUS =
    	        MnFeatureFactory.simplePatch(MnBlocks.CRYSTALOTUS.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .chance(6);
    	
    	public static final ConfiguredFeature<?, ?> VIOLEAF =
    	        MnFeatureFactory.simplePatch(MnBlocks.VIOLEAF.defaultBlockState().setValue(VioleafBlock.GROWN, true), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(2);
    	
    	public static final ConfiguredFeature<?, ?> CRYSTAL_FLOWERS =
    	        MnFeatureFactory.simplePatch(MnBlocks.CRYSTAL_FLOWER.defaultBlockState(), 48)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(2);
    	
    	public static final ConfiguredFeature<?, ?> BOGSHROOM =
    	        MnFeatureFactory.simplePatch(MnBlocks.BOGSHROOM.defaultBlockState(), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .chance(3);

    	public static final ConfiguredFeature<?, ?> TALL_BOGSHROOM =
    	        MnFeatureFactory.doublePlantPatch(MnBlocks.TALL_BOGSHROOM.defaultBlockState(), 32)
    	        .decorated(Features.Placements.ADD_32)
    	        .decorated(Features.Placements.HEIGHTMAP_SQUARE)
    	        .chance(13);
    	
    	public static final ConfiguredFeature<?, ?> SUAVIS_COMMON =
    	        MnFeatureFactory.simplePatch(MnBlocks.SUAVIS.defaultBlockState().setValue(SuavisBlock.STAGE, 3), 32)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE)
    	        .count(8);
    	
    	public static final ConfiguredFeature<?, ?> SUAVIS_SPARSE =
    	        MnFeatureFactory.simplePatch(MnBlocks.SUAVIS.defaultBlockState().setValue(SuavisBlock.STAGE, 3), 4)
    	        .decorated(Features.Placements.HEIGHTMAP_DOUBLE)
    	        .chance(4);
    		
    	public static final ConfiguredFeature<?, ?> DEAD_LOG = 
    			MnFeatureFactory.deadLog(MnBlocks.DEAD_WOOD_LOG)
    			.decorated(Features.Placements.HEIGHTMAP_SQUARE)
    			.chance(2);
    	
    }
}
