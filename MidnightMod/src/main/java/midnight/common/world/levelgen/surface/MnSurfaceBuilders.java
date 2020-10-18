/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.world.levelgen.surface;

import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.ArrayList;
import java.util.List;

public final class MnSurfaceBuilders {
    private static final List<SurfaceBuilder<?>> REGISTRY = new ArrayList<>();

    public static final DeceitfulBogSurfaceBuilder DECEITFUL_BOG = register("deceitful_bog", new DeceitfulBogSurfaceBuilder(SurfaceBuilderConfig.CODEC));

    private MnSurfaceBuilders() {
    }

    public static void registerSurfaceBuilders(IRegistry<SurfaceBuilder<?>> registry) {
        REGISTRY.forEach(registry::register);
    }

    private static <T extends SurfaceBuilder<?>> T register(String id, T obj) {
        REGISTRY.add(obj.setRegistryName(Midnight.resLoc(id)));
        return obj;
    }
}
