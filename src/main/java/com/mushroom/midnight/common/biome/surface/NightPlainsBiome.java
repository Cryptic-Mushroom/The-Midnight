package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;

public class NightPlainsBiome extends SurfaceBiome {
    public NightPlainsBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.PLAINS)
                .grassColor(0xBAA3C6)
                .depth(0.12F)
                .scale(0.26F)
                .ridgeWeight(0.0F)
        );

        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addSparseShadowrootTrees(this);
        MidnightBiomeConfigurator.addGrasses(this);
        MidnightBiomeConfigurator.addFingeredGrass(this);

        MidnightBiomeConfigurator.addNightstoneSpikesAndBoulders(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addWell(this);

        add(Midnight.MIDNIGHT_MOB, new SpawnListEntry(MidnightEntities.HUNTER, 5, 1, 2));
    }
}
