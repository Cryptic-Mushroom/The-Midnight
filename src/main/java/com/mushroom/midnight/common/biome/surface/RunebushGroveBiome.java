package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class RunebushGroveBiome extends SurfaceBiome {
    public RunebushGroveBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8C84BC)
                .skyColor(0x1F3B4A)
                .depth(0.155F)
                .scale(0.07F)
        );

        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addVigilantForestTrees(this);
        MidnightBiomeConfigurator.addDeadLogs(this);

        MidnightBiomeConfigurator.addCommonSuavis(this);
        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addRunebushes(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);
    }
}
