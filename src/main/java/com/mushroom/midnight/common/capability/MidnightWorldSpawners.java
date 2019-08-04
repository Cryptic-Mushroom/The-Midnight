package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.biome.surface.SurfaceBiome;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import com.mushroom.midnight.common.world.MidnightEntitySpawner;
import com.mushroom.midnight.common.world.feature.placement.SurfacePlacementLevel;
import com.mushroom.midnight.common.world.feature.placement.UndergroundPlacementLevel;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public interface MidnightWorldSpawners extends ICapabilityProvider {
    void populateChunk(int chunkX, int chunkZ, Random random);

    void spawnAroundPlayers();

    @Override
    @Nonnull
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return Midnight.WORLD_SPAWNERS_CAP.orEmpty(capability, LazyOptional.of(() -> this));
    }

    class Void implements MidnightWorldSpawners {
        @Override
        public void populateChunk(int chunkX, int chunkZ, Random random) {
        }

        @Override
        public void spawnAroundPlayers() {
        }
    }

    class SurfaceAndCave implements MidnightWorldSpawners {
        private final ServerWorld world;
        private final MidnightEntitySpawner<SurfaceBiome> biomeEntitySpawner;
        private final MidnightEntitySpawner<CavernousBiome> cavernEntitySpawner;

        public SurfaceAndCave(ServerWorld world) {
            this.world = world;

            this.biomeEntitySpawner = new MidnightEntitySpawner<>(this::getSurfaceSpawnBiome, SurfacePlacementLevel.INSTANCE);
            this.cavernEntitySpawner = new MidnightEntitySpawner<>(this::getCavernSpawnBiome, UndergroundPlacementLevel.INSTANCE);
        }

        private SurfaceBiome getSurfaceSpawnBiome(BlockPos pos) {
            Biome biome = this.world.getBiome(pos);
            if (biome instanceof SurfaceBiome) {
                return (SurfaceBiome) biome;
            }
            return (SurfaceBiome) MidnightSurfaceBiomes.NIGHT_PLAINS;
        }

        private CavernousBiome getCavernSpawnBiome(BlockPos pos) {
            return CavernousBiomeStore.getBiome(this.world, pos.getX(), pos.getZ());
        }

        @Override
        public void populateChunk(int chunkX, int chunkZ, Random random) {
            //this.biomeEntitySpawner.populateChunk(this.world, chunkX, chunkZ, random);
            //this.cavernEntitySpawner.populateChunk(this.world, chunkX, chunkZ, random);
        }

        @Override
        public void spawnAroundPlayers() {
            //this.biomeEntitySpawner.spawnAroundPlayers(this.world);
            //this.cavernEntitySpawner.spawnAroundPlayers(this.world);
        }
    }
}
