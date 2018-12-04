package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.common.biome.IMidnightBiome;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.PartialChunkGenerator;
import com.mushroom.midnight.common.world.noise.INoiseSampler;
import com.mushroom.midnight.common.world.noise.PerlinNoiseSampler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

public class WorldGenMoltenCrater extends MapGenBase {
    private static final int SPAWN_CHANCE = 350;

    private static final int MIN_RADIUS = 16;
    private static final int MAX_RADIUS = 42;

    private static final int MAX_GENERATION_Y = 70;
    private static final int SCALE_Y = 2;

    private static final int EDGE_DEPTH = 12;
    private static final int POOL_DEPTH = 2;

    private static final IBlockState AIR_STATE = Blocks.AIR.getDefaultState();
    private static final IBlockState MIASMA_STATE = ModBlocks.MIASMA.getDefaultState();
    private static final IBlockState SURFACE_STATE = ModBlocks.TRENCHSTONE.getDefaultState();

    private final PartialChunkGenerator generator;

    private final INoiseSampler noiseSampler;
    private final double[] noiseBuffer = new double[16 * 16 * 256];

    public WorldGenMoltenCrater(Random random, PartialChunkGenerator generator) {
        this.generator = generator;
        this.range = MathHelper.ceil((MAX_RADIUS + EDGE_DEPTH) / 16.0);

        this.noiseSampler = new PerlinNoiseSampler(random);
        this.noiseSampler.setFrequency(0.001);
    }

    @Override
    protected void recursiveGenerate(World world, int chunkX, int chunkZ, int genChunkX, int genChunkZ, ChunkPrimer primer) {
        if (this.rand.nextInt(SPAWN_CHANCE) == 0) {
            ChunkPrimer contextPrimer = new ChunkPrimer();
            this.generator.primeChunkBare(contextPrimer, chunkX, chunkZ);

            int globalX = chunkX << 4;
            int globalZ = chunkZ << 4;

            int centerX = globalX + this.rand.nextInt(16);
            int centerZ = globalZ + this.rand.nextInt(16);
            Biome biome = world.getBiomeProvider().getBiome(new BlockPos(centerX, 0, centerZ));
            if (this.isBiomeInvalid(biome)) {
                return;
            }

            int centerY = findSurfaceFixed(contextPrimer, centerX & 15, centerZ & 15);
            if (centerY >= MAX_GENERATION_Y) {
                return;
            }

            int radius = this.rand.nextInt(MAX_RADIUS - MIN_RADIUS + 1) + MIN_RADIUS;
            this.generateCrater(centerX, centerY, centerZ, radius, genChunkX, genChunkZ, primer);
        }
    }

    private boolean isBiomeInvalid(Biome biome) {
        return IMidnightBiome.isWet(biome);
    }

    private void generateCrater(int centerX, int centerY, int centerZ, int radius, int genChunkX, int genChunkZ, ChunkPrimer primer) {
        int edgeRadius = radius + EDGE_DEPTH;

        int minChunkX = genChunkX << 4;
        int minChunkZ = genChunkZ << 4;
        int maxChunkX = minChunkX + 15;
        int maxChunkZ = minChunkZ + 15;

        int minX = Math.max(centerX - edgeRadius, minChunkX);
        int maxX = Math.min(centerX + edgeRadius, maxChunkX);

        int minZ = Math.max(centerZ - edgeRadius, minChunkZ);
        int maxZ = Math.min(centerZ + edgeRadius, maxChunkZ);

        int minY = Math.max(centerY - edgeRadius / SCALE_Y, 0);
        int maxY = Math.min(centerY + edgeRadius / SCALE_Y, 255);

        if (minX >= maxX || minZ >= maxZ || minY >= maxY) {
            return;
        }

        Arrays.fill(this.noiseBuffer, 0.0);
        this.noiseSampler.sample3D(this.noiseBuffer, minChunkX, 0, minChunkZ, 16, 256, 16);

        BlockPos minPos = new BlockPos(minX, minY, minZ);
        BlockPos maxPos = new BlockPos(maxX, maxY, maxZ);

        this.carveCrater(centerX, centerY, centerZ, radius, edgeRadius, minPos, maxPos, primer);
        this.decorateSurface(centerX, centerZ, edgeRadius, minPos, maxPos, primer);
    }

    private void carveCrater(int centerX, int centerY, int centerZ, int radius, int edgeRadius, BlockPos minPos, BlockPos maxPos, ChunkPrimer primer) {
        int radiusSquared = radius * radius;
        int edgeRadiusSquared = edgeRadius * edgeRadius;
        int poolLevel = centerY - (radius / SCALE_Y) + POOL_DEPTH;

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minPos, maxPos)) {
            int localX = pos.getX() & 15;
            int localY = pos.getY() & 255;
            int localZ = pos.getZ() & 15;
            double noise = (this.noiseBuffer[localY + (localX + localZ * 16) * 16] + 1.0) * 8.0;
            double distanceSquared = this.computeDistanceSquared(pos, centerX, centerY, centerZ) + noise * noise;
            if (distanceSquared <= edgeRadiusSquared) {
                if (distanceSquared < radiusSquared) {
                    this.carveCraterBlock(primer, poolLevel, localX, localY, localZ);
                } else {
                    this.hardenEdgeBlock(primer, localX, localY, localZ);
                }
            }
        }
    }

    private void carveCraterBlock(ChunkPrimer primer, int poolLevel, int localX, int localY, int localZ) {
        if (primer.getBlockState(localX, localY, localZ) == AIR_STATE) {
            return;
        }
        if (localY <= poolLevel) {
            primer.setBlockState(localX, localY, localZ, MIASMA_STATE);
        } else {
            primer.setBlockState(localX, localY, localZ, AIR_STATE);
        }
    }

    private void hardenEdgeBlock(ChunkPrimer primer, int localX, int localY, int localZ) {
        IBlockState currentState = primer.getBlockState(localX, localY, localZ);
        if (currentState.isFullCube()) {
            primer.setBlockState(localX, localY, localZ, SURFACE_STATE);
        }
    }

    private void decorateSurface(int centerX, int centerZ, int edgeRadius, BlockPos minPos, BlockPos maxPos, ChunkPrimer primer) {
        int edgeRadiusSquared = edgeRadius * edgeRadius;

        for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
            for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
                int deltaX = x - centerX;
                int deltaZ = z - centerZ;
                int distanceSquared = deltaX * deltaX + deltaZ * deltaZ;

                if (distanceSquared <= edgeRadiusSquared) {
                    int localX = x & 15;
                    int localZ = z & 15;
                    int localY = findSurfaceFixed(primer, localX, localZ);
                    if (primer.getBlockState(localX, localY, localZ) == SURFACE_STATE) {
                        IBlockState state = this.selectSurfaceState();
                        if (state != null) {
                            primer.setBlockState(localX, localY, localZ, state);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    private IBlockState selectSurfaceState() {
        float chance = this.rand.nextFloat();
        if (chance > 0.95F) {
            return MIASMA_STATE;
        } else if (chance > 0.7F) {
            return ModBlocks.MIASMA_SURFACE.getDefaultState();
        }
        return null;
    }

    private double computeDistanceSquared(BlockPos pos, int centerX, int centerY, int centerZ) {
        int deltaX = pos.getX() - centerX;
        int deltaY = Math.min(pos.getY() - centerY, 0) * SCALE_Y;
        int deltaZ = pos.getZ() - centerZ;
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    private static int findSurfaceFixed(ChunkPrimer primer, int x, int z) {
        if (x != 15 && z != 15) {
            return primer.findGroundBlockIdx(x, z) - 1;
        }

        for (int y = 255; y >= 0; y--) {
            IBlockState state = primer.getBlockState(x, y, z);
            if (state != AIR_STATE) {
                return y;
            }
        }

        return 0;
    }
}
