package com.mushroom.midnight.common.biome.surface;

import com.mushroom.midnight.common.biome.MidnightBiomeConfigurator;
import com.mushroom.midnight.common.biome.MidnightSurfaceBuilders;
import com.mushroom.midnight.common.registry.MidnightFeatures;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class VigilantForestBiome extends SurfaceBiome {
    public VigilantForestBiome() {
        super(new Properties()
                .surfaceBuilder(MidnightSurfaceBuilders.SURFACE, MidnightSurfaceBuilders.GRASS_DIRT_MUD_CONFIG)
                .category(Category.FOREST)
                .depth(0.155F)
                .scale(0.07F)
        );

        MidnightBiomeConfigurator.addGlobalOres(this);

        MidnightBiomeConfigurator.addVigilantForestTrees(this);
        MidnightBiomeConfigurator.addDeadLogs(this);

        MidnightBiomeConfigurator.addSparseSuavis(this);
        MidnightBiomeConfigurator.addSparseDeadTrees(this);
        MidnightBiomeConfigurator.addVioleafs(this);

        MidnightBiomeConfigurator.addLumen(this);
        MidnightBiomeConfigurator.addSmallFungis(this);
        MidnightBiomeConfigurator.addGrasses(this);

        MidnightBiomeConfigurator.addGlobalFeatures(this);

        MidnightBiomeConfigurator.addStandardCreatureSpawns(this);
        MidnightBiomeConfigurator.addStandardMonsterSpawns(this);
        MidnightBiomeConfigurator.addForestSpawns(this);

        this.add(MidnightFeatures.SHADOWROOT_GUARDTOWER, IFeatureConfig.NO_FEATURE_CONFIG);
    }
}
