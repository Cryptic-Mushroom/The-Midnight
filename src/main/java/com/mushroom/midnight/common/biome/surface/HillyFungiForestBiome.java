package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;

public class HillyFungiForestBiome extends SurfaceBiome {
    public HillyFungiForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .grassColor(0x8489B5)
                .skyColor(0x15292D)
                .depth(2.25F)
                .scale(0.4F)
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
