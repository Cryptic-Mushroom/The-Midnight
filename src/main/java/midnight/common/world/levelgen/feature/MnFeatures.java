/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.world.levelgen.feature;

import midnight.common.Midnight;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

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
}
