package com.mushroom.midnight.common.biome;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.config.IPlacementConfig;
import net.minecraft.world.biome.Biome;

import java.util.Collection;

public class MidnightBiomeConfig {
    private final SurfaceConfig surfaceConfig;
    private final float ridgeWeight;
    private final float densityScale;
    private final boolean wet;

    private final int grassColor;
    private final int foliageColor;

    private final ImmutableList<FeatureEntry> features;
    private final ImmutableList<Biome.SpawnListEntry> monsterSpawns;
    private final ImmutableList<Biome.SpawnListEntry> creatureSpawns;

    private MidnightBiomeConfig(
            SurfaceConfig surfaceConfig,
            float ridgeWeight, float densityScale, boolean wet,
            int grassColor, int foliageColor,
            ImmutableList<FeatureEntry> features,
            ImmutableList<Biome.SpawnListEntry> monsterSpawns,
            ImmutableList<Biome.SpawnListEntry> creatureSpawns
    ) {
        this.surfaceConfig = surfaceConfig;
        this.ridgeWeight = ridgeWeight;
        this.densityScale = densityScale;
        this.wet = wet;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
        this.features = features;
        this.monsterSpawns = monsterSpawns;
        this.creatureSpawns = creatureSpawns;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(MidnightBiomeConfig config) {
        return new Builder(config);
    }

    public Collection<FeatureEntry> getFeatures() {
        return this.features;
    }

    public Collection<Biome.SpawnListEntry> getMonsterSpawns() {
        return this.monsterSpawns;
    }

    public Collection<Biome.SpawnListEntry> getCreatureSpawns() {
        return this.creatureSpawns;
    }

    public float getRidgeWeight() {
        return this.ridgeWeight;
    }

    public float getDensityScale() {
        return this.densityScale;
    }

    public boolean isWet() {
        return this.wet;
    }

    public int getGrassColor() {
        return this.grassColor;
    }

    public int getFoliageColor() {
        return this.foliageColor;
    }

    public SurfaceConfig getSurfaceConfig() {
        return this.surfaceConfig;
    }

    public static class Builder {
        private SurfaceConfig surfaceConfig = new SurfaceConfig();
        private float ridgeWeight = 1.0F;
        private float densityScale = 1.0F;
        private boolean wet;

        private int grassColor = 0xB084BC;
        private int foliageColor = 0x8F6DBC;

        private final ImmutableList.Builder<FeatureEntry> features = new ImmutableList.Builder<>();
        private final ImmutableList.Builder<Biome.SpawnListEntry> monsterSpawns = new ImmutableList.Builder<>();
        private final ImmutableList.Builder<Biome.SpawnListEntry> creatureSpawns = new ImmutableList.Builder<>();

        Builder() {
        }

        Builder(MidnightBiomeConfig config) {
            this.surfaceConfig = config.surfaceConfig;
            this.ridgeWeight = config.ridgeWeight;
            this.densityScale = config.densityScale;
            this.features.addAll(config.features);
        }

        public Builder withFeature(IMidnightFeature feature, IPlacementConfig config) {
            this.features.add(new FeatureEntry(new IMidnightFeature[] { feature }, config));
            return this;
        }

        public Builder withFeature(IMidnightFeature[] features, IPlacementConfig config) {
            this.features.add(new FeatureEntry(features, config));
            return this;
        }

        public Builder withSurface(SurfaceConfig surfaceConfig) {
            this.surfaceConfig = surfaceConfig;
            return this;
        }

        public Builder withRidgeWeight(float ridgeWeight) {
            this.ridgeWeight = ridgeWeight;
            return this;
        }

        public Builder withDensityScale(float densityScale) {
            this.densityScale = densityScale;
            return this;
        }

        public Builder withGrassColor(int color) {
            this.grassColor = color;
            return this;
        }

        public Builder withFoliageColor(int color) {
            this.foliageColor = color;
            return this;
        }

        public Builder withMonster(Biome.SpawnListEntry entry) {
            this.monsterSpawns.add(entry);
            return this;
        }

        public Builder withCreature(Biome.SpawnListEntry entry) {
            this.creatureSpawns.add(entry);
            return this;
        }

        public Builder wet() {
            this.wet = true;
            return this;
        }

        public MidnightBiomeConfig build() {
            return new MidnightBiomeConfig(
                    this.surfaceConfig,
                    this.ridgeWeight, this.densityScale, this.wet,
                    this.grassColor, this.foliageColor,
                    this.features.build(),
                    this.monsterSpawns.build(),
                    this.creatureSpawns.build()
            );
        }
    }

    public static class FeatureEntry {
        private final IMidnightFeature[] features;
        private final IPlacementConfig placementConfig;

        private FeatureEntry(IMidnightFeature[] feature, IPlacementConfig placementConfig) {
            this.features = feature;
            this.placementConfig = placementConfig;
        }

        public IMidnightFeature[] getFeatures() {
            return this.features;
        }

        public IPlacementConfig getPlacementConfig() {
            return this.placementConfig;
        }
    }
}
