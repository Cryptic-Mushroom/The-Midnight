/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.world.levelgen.surface;

import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public final class MnConfiguredSurfaceBuilders {
    public static final ConfiguredSurfaceBuilder<?> DEFAULT_NIGHT_GRASS = register(
        "default_night_grass",
        SurfaceBuilder.DEFAULT.withConfig(
            new SurfaceBuilderConfig(
                MnBlocks.NIGHT_GRASS_BLOCK.getDefaultState(),
                MnBlocks.NIGHT_DIRT.getDefaultState(),
                MnBlocks.NIGHT_DIRT.getDefaultState()
            )
        )
    );
    public static final ConfiguredSurfaceBuilder<?> DECEITFUL_BOG = register(
        "deceitful_bog",
        MnSurfaceBuilders.DECEITFUL_BOG.withConfig(new SurfaceBuilderConfig(
            MnBlocks.NIGHT_GRASS_BLOCK.getDefaultState(),
            MnBlocks.NIGHT_DIRT.getDefaultState(),
            MnBlocks.NIGHT_DIRT.getDefaultState()
        ))
    );

    private MnConfiguredSurfaceBuilders() {
    }

    private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String id, ConfiguredSurfaceBuilder<SC> builder) {
        return WorldGenRegistries.add(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, Midnight.idStr(id), builder);
    }
}
