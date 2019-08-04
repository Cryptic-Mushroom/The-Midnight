package com.mushroom.midnight.common.world;

import com.google.common.collect.Iterables;
import com.mushroom.midnight.common.biome.BiomeLayers;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.util.NoiseChunkPrimer;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.carver.ConfiguredCarver;

import java.util.BitSet;
import java.util.Collection;

import static com.mushroom.midnight.common.world.MidnightNoiseGenerator.*;

public class MidnightChunkGenerator extends NoiseChunkGenerator<MidnightChunkGenerator.Config> {
    public static final int SURFACE_LEVEL = 78;

    public static final int MIN_CAVE_HEIGHT = 20;
    public static final int MAX_CAVE_HEIGHT = 46;

    public static final int SURFACE_CAVE_BOUNDARY = MAX_CAVE_HEIGHT + 12;

    public static final int SEA_LEVEL = SURFACE_LEVEL + 2;

    private final World world;
    private final MidnightNoiseGenerator noiseGenerator;
    private final NoiseChunkPrimer noisePrimer;

    private final BiomeLayers<Biome> surfaceLayers;
    private final BiomeLayers<CavernousBiome> undergroundLayers;

    private final INoiseGenerator surfaceDepthNoise;

    public MidnightChunkGenerator(World world, BiomeLayers<Biome> surfaceLayers, BiomeLayers<CavernousBiome> undergroundLayers, Config config) {
        super(world, new MidnightBiomeProvider(surfaceLayers), HORIZONTAL_GRANULARITY, VERTICAL_GRANULARITY, 256, config, true);

        this.world = world;
        this.noiseGenerator = new MidnightNoiseGenerator(this.randomSeed);
        this.noisePrimer = new NoiseChunkPrimer(HORIZONTAL_GRANULARITY, VERTICAL_GRANULARITY, NOISE_WIDTH, NOISE_HEIGHT);

        this.surfaceLayers = surfaceLayers;
        this.undergroundLayers = undergroundLayers;

        this.surfaceDepthNoise = new PerlinNoiseGenerator(this.randomSeed, 4);
    }

    @Override
    public void makeBase(IWorld world, IChunk chunk) {
        double[] noise = this.noiseGenerator.sampleChunkNoise(chunk.getPos(), this.surfaceLayers, this.undergroundLayers);
        this.noisePrimer.primeChunk((ChunkPrimer) chunk, noise, (density, x, y, z) -> {
            if (density > 0.0F) {
                return this.defaultBlock;
            } else if (y < SEA_LEVEL && y > SURFACE_CAVE_BOUNDARY) {
                return this.defaultFluid;
            }
            return null;
        });
    }

    @Override
    public void generateSurface(IChunk chunk) {
        long seed = this.world.getSeed();

        ChunkPos chunkPos = chunk.getPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;

        SharedSeedRandom random = new SharedSeedRandom();
        random.setBaseChunkSeed(chunkX, chunkZ);

        int minChunkX = chunkPos.getXStart();
        int minChunkZ = chunkPos.getZStart();

        Biome[] biomes = chunk.getBiomes();

        for (int localZ = 0; localZ < 16; localZ++) {
            for (int localX = 0; localX < 16; localX++) {
                int globalX = minChunkX + localX;
                int globalZ = minChunkZ + localZ;

                Biome surfaceBiome = biomes[localX + localZ * 16];
                CavernousBiome cavernousBiome = this.getCavernousBiome(globalX, globalZ);

                int height = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, localX, localZ) + 1;

                double depth = this.surfaceDepthNoise.func_215460_a(globalX * 0.0625, globalZ * 0.0625, 0.0625, localX * 0.0625);

                surfaceBiome.buildSurface(random, chunk, globalX, globalZ, height, depth, this.defaultBlock, this.defaultFluid, SEA_LEVEL, seed);
                cavernousBiome.generateSurface(random, chunk, globalX, globalZ, height, depth, this.defaultBlock, this.defaultFluid, SEA_LEVEL, seed);
            }
        }

        this.makeBedrock(chunk, random);
    }

    @Override
    public void carve(IChunk chunk, GenerationStage.Carving stage) {
        Collection<ConfiguredCarver<?>> surfaceCarvers = this.getBiome(chunk).getCarvers(stage);
        Collection<ConfiguredCarver<?>> undergroundCarvers = this.getCavernousBiome(chunk).getCarversFor(stage);

        this.applyCarvers(chunk, stage, Iterables.concat(surfaceCarvers, undergroundCarvers));
    }

    private void applyCarvers(IChunk chunk, GenerationStage.Carving stage, Iterable<ConfiguredCarver<?>> carvers) {
        ChunkPos chunkPos = chunk.getPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;

        SharedSeedRandom random = new SharedSeedRandom();
        BitSet mask = chunk.getCarvingMask(stage);

        for (int nz = chunkZ - 8; nz <= chunkZ + 8; nz++) {
            for (int nx = chunkX - 8; nx <= chunkX + 8; nx++) {
                int i = 0;

                for (ConfiguredCarver<?> carver : carvers) {
                    random.setLargeFeatureSeed(this.seed + i, nx, nz);
                    if (carver.shouldCarve(random, nx, nz)) {
                        carver.carve(chunk, random, this.getSeaLevel(), nx, nz, chunkX, chunkZ, mask);
                    }

                    i++;
                }
            }
        }
    }

    @Override
    public void decorate(WorldGenRegion world) {
        super.decorate(world);

        int chunkX = world.getMainChunkX();
        int chunkZ = world.getMainChunkZ();

        int minX = chunkX * 16;
        int minZ = chunkZ * 16;

        BlockPos origin = new BlockPos(minX, 0, minZ);
        CavernousBiome cavernousBiome = this.getCavernousBiome(origin.getX() + 8, origin.getZ() + 8);

        SharedSeedRandom random = new SharedSeedRandom();

        long seed = random.setDecorationSeed(world.getSeed(), minX, minZ);
        for (GenerationStage.Decoration stage : GenerationStage.Decoration.values()) {
            cavernousBiome.placeFeatures(stage, this, world, seed, random, origin);
        }
    }

    // TODO: spawning for cavernous biomes

    @Override
    public void spawnMobs(WorldGenRegion region) {
        int chunkX = region.getMainChunkX();
        int chunkZ = region.getMainChunkZ();

        SharedSeedRandom random = new SharedSeedRandom();
        random.setDecorationSeed(region.getSeed(), chunkX << 4, chunkZ << 4);

        // TODO: Worldgen spawning
//        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
//            this.world.getCapability(Midnight.WORLD_SPAWNERS_CAP).ifPresent(MidnightWorldSpawners::populateChunk);
//        }
    }

    @Override
    public void spawnMobs(ServerWorld world, boolean spawnHostileMobs, boolean spawnPeacefulMobs) {
        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            //this.world.getCapability(Midnight.WORLD_SPAWNERS_CAP).ifPresent(MidnightWorldSpawners::spawnAroundPlayers);
        }
    }

    @Override
    protected void func_222548_a(double[] noise, int x, int z) {
        this.noiseGenerator.populateColumnNoise(noise, x, z, this.surfaceLayers, this.undergroundLayers);
    }

    @Override
    protected double func_222545_a(double x, double z, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected double[] func_222549_a(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getGroundHeight() {
        return this.world.getSeaLevel() + 1;
    }

    @Override
    public int getSeaLevel() {
        return SEA_LEVEL;
    }

    protected CavernousBiome getCavernousBiome(IChunk chunk) {
        ChunkPos pos = chunk.getPos();
        return this.undergroundLayers.block.sample(pos.getXStart(), pos.getZStart());
    }

    protected CavernousBiome getCavernousBiome(int x, int z) {
        return this.undergroundLayers.block.sample(x, z);
    }

    public static class Config extends GenerationSettings {
        public static Config createDefault() {
            Config config = new Config();
            config.setDefaultBlock(MidnightBlocks.NIGHTSTONE.getDefaultState());
            config.setDefaultFluid(MidnightBlocks.DARK_WATER.getDefaultState());

            return config;
        }
    }
}
