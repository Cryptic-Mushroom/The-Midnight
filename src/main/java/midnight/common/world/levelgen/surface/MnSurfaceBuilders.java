/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.world.levelgen.surface;

import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.HashMap;
import java.util.Map;

public final class MnSurfaceBuilders {
    private static final Map<ResourceLocation, SurfaceBuilder<?>> REGISTRY = new HashMap<>();

    public static final DeceitfulBogSurfaceBuilder DECEITFUL_BOG = register("deceitful_bog", new DeceitfulBogSurfaceBuilder(SurfaceBuilderConfig.CODEC));

    private MnSurfaceBuilders() {
    }

    public static void registerSurfaceBuilders(IRegistry<SurfaceBuilder<?>> registry) {
        registry.registerAll(REGISTRY);
    }

    private static <T extends SurfaceBuilder<?>> T register(String id, T obj) {
        REGISTRY.put(Midnight.id(id), obj.setRegistryName(Midnight.id(id)));
        return obj;
    }
}
