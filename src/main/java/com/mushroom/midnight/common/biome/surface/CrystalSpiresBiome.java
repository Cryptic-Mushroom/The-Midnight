package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

public class CrystalSpiresBiome extends SurfaceBiome {
    public CrystalSpiresBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.PLAINS)
                .grassColor(0xD184BC)
                .skyColor(0x543353)
                .depth(0.6F)
                .scale(0.26F)
                .ridgeWeight(0.0F)
        );

        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addCrystalClusters(this);
        MidnightBiomeConfigurator.addSparseShadowrootTrees(this);

        MidnightBiomeConfigurator.addCrystalFlowers(this);
        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addGrasses(this);
        MidnightBiomeConfigurator.addGlobalFeatures(this);

        this.add(EntityClassification.CREATURE, new Biome.SpawnListEntry(MidnightEntities.NIGHTSTAG, 100, 1, 3));
        this.add(Midnight.MIDNIGHT_AMBIENT, new Biome.SpawnListEntry(MidnightEntities.CRYSTAL_BUG, 100, 7, 10));
    }
}
