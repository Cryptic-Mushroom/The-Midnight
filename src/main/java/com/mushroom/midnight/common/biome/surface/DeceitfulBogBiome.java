package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightSurfaceConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

public class DeceitfulBogBiome extends SurfaceBiome {
    public DeceitfulBogBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.PATCHED_MUD, MidnightSurfaceBuilders.PEAT_CONFIG)
                .category(Category.SWAMP)
                .grassColor(0x554E70)
                .skyColor(0x1E1716)
                .depth(-1F)
                .scale(0.2F)
                .ridgeWeight(0.0F)
                .fog(0.1F, 80.0F)
        );

        MidnightSurfaceConfigurator.addStructureFeatures(this);
        MidnightSurfaceConfigurator.addGlobalOres(this);

        MidnightSurfaceConfigurator.addLargeBogshrooms(this);

        MidnightSurfaceConfigurator.addBogDeadTrees(this);

        MidnightSurfaceConfigurator.addBogTrees(this);
        MidnightSurfaceConfigurator.addFallenDeadLogs(this);

        MidnightSurfaceConfigurator.addBogweed(this);
        MidnightSurfaceConfigurator.addBogshroomFlowers(this);
        MidnightSurfaceConfigurator.addViridshroomFlowers(this);

        MidnightSurfaceConfigurator.addAlgaeAndMoss(this);
        MidnightSurfaceConfigurator.addGrasses(this);

        MidnightSurfaceConfigurator.addGlobalFeatures(this);

        MidnightSurfaceConfigurator.addStandardCreatureSpawns(this);
        MidnightSurfaceConfigurator.addStandardMonsterSpawns(this);

        this.add(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(MidnightEntities.DECEITFUL_SNAPPER, 100, 5, 10));
        this.add(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(MidnightEntities.BULB_ANGLER, 60, 2, 4));
    }
}
