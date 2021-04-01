/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.levelgen.midnight;

import midnight.common.block.MnBlocks;
import midnight.core.util.MnMath;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;

import java.util.Random;

import net.shadew.ptg.noise.Noise2D;
import net.shadew.ptg.noise.opensimplex.FractalOpenSimplex2D;

public class MidnightSurfaceGenerator extends MidnightGenerator {
    private final Noise2D surfaceNoise;

    public MidnightSurfaceGenerator(long seed, BiomeProvider biomeProvider, TheMidnightChunkGenerator chunkGenerator) {
        super(seed, biomeProvider, chunkGenerator);
        Random rng = new Random(seed);
        surfaceNoise = new FractalOpenSimplex2D(rng.nextInt(), 15.6587812, 5);
    }

    public void generateSurface(WorldGenRegion world, IChunk chunk) {
        ChunkPos cp = chunk.getPos();
        int cx = cp.x;
        int cz = cp.z;

        SharedSeedRandom rng = new SharedSeedRandom();
        rng.setBaseChunkSeed(cx, cz);

        int rx = cp.getMinBlockX();
        int rz = cp.getMinBlockZ();

        BlockPos.Mutable mpos = new BlockPos.Mutable();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int gx = rx + x;
                int gz = rz + z;
                int hgt = chunk.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z) + 1;
                double snoise = MnMath.unlerp(-1, 1, surfaceNoise.generate(gx, gz));

                world.getBiome(mpos.set(rx + x, hgt, rz + z))
                     .buildSurfaceAt(
                         rng, chunk, gx, gz, hgt, snoise,
                         MnBlocks.NIGHTSTONE.defaultBlockState(), //chunkGenerator.getSettings().getDefaultBlock(),
                         Blocks.WATER.defaultBlockState(),
                         chunkGenerator.getSeaLevel(),
                         world.getSeed()
                     );
            }
        }
    }
}
