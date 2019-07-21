package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class FungiForestBiome extends SurfaceBiome {
    public FungiForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8489B5)
                .depth(0.155F)
                .scale(0.07F)
        );

        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addLargeFungis(this);
        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addDenseSmallFungis(this);
        MidnightBiomeConfigurator.addDenseTallFungis(this);
        MidnightBiomeConfigurator.addDeadViridShroomAndCache(this);
        MidnightBiomeConfigurator.addGrasses(this);
        MidnightBiomeConfigurator.addBladeshrooms(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);
    }
}
