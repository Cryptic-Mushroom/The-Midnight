/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.levelgen.midnight;

import midnight.api.biome.IMidnightBiome;
import midnight.common.block.MnBlocks;
import midnight.core.util.MnMath;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;

import java.util.Random;

import net.shadew.ptg.noise.Noise2D;
import net.shadew.ptg.noise.Noise3D;
import net.shadew.ptg.noise.perlin.FractalPerlin2D;
import net.shadew.ptg.noise.perlin.FractalPerlin3D;
import net.shadew.ptg.noise.perlin.InverseFractalPerlin3D;

/**
 * Terrain generator of the Midnight dimension. This makes the base terrain shape.
 */
public class MidnightTerrainGenerator extends MidnightGenerator {

    // Reuse buffers as much as possible, but stay thread-safe
    private final ThreadLocal<Biome[]> biomeBuffer = ThreadLocal.withInitial(() -> new Biome[11 * 11]);
    private final ThreadLocal<double[]> terrainBuffer = ThreadLocal.withInitial(() -> new double[5 * 5 * 2]);
    private final ThreadLocal<double[]> noiseBuffer = ThreadLocal.withInitial(() -> new double[5 * 65 * 5]);

    // Base terrain noise: A and B are interpolated with Mix to generate a new noise field
    private final Noise3D mixNoise;
    private final Noise3D noiseA;
    private final Noise3D noiseB;

    // Depth noise, for generating granularity
    private final Noise2D depthNoise;

    // The base height, which is the water level divided by 4
    private final double baseHeight;

    public MidnightTerrainGenerator(long seed, BiomeProvider biomeProvider, TheMidnightChunkGenerator chunkGenerator, int baseHeight) {
        super(seed, biomeProvider, chunkGenerator);
        this.baseHeight = baseHeight / 4D;

        Random rand = new Random(seed); // RNG to scramble seeds for noise gens

        mixNoise = new FractalPerlin3D(rand.nextInt(), 12.561823, 8);

        // Scale smaller as inverse noise is just huge
        noiseA = new InverseFractalPerlin3D(rand.nextInt(), 1 / 641.278137, 16);
        noiseB = new InverseFractalPerlin3D(rand.nextInt(), 1 / 641.278137, 16);

        depthNoise = new FractalPerlin2D(rand.nextInt(), 17.241212, 8);
    }

    /**
     * Generates the terrain in a chunk.
     *
     * @param world The world region to generate in. Not used but maybe for future use.
     * @param chunk The chunk to generate in.
     */
    public void generateTerrain(IWorld world, IChunk chunk) {
        BlockPos.Mutable mpos = new BlockPos.Mutable();

        int cx = chunk.getPos().x;
        int cz = chunk.getPos().z;

        generateBiomeField(cx, cz);
        generateTerrainHeights(cx, cz);
        generateNoiseField(cx, cz);

        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    mpos.set(cx * 16 + x, y, cz * 16 + z);
                    double noise = getNoiseAt(mpos);

                    if (noise > 0) {
                        chunk.setBlockState(mpos, MnBlocks.NIGHTSTONE.defaultBlockState(), false);
                    } else if (y < chunkGenerator.getSeaLevel()) {
                        chunk.setBlockState(mpos, Blocks.WATER.defaultBlockState(), false);
                    }
                }
            }
        }
    }

    /**
     * Computes the noise value at a given block coordinate, by interpolating the nearest values from the current
     * buffer.
     *
     * @return The result of the noise computation.
     */
    protected double getNoiseAt(BlockPos pos) {
        double[] buf = noiseBuffer.get();

        // Cell-local coordinates, range from 0-1 within the noise cell
        double lx = (pos.getX() & 3) / 4D;
        double ly = (pos.getY() & 3) / 4D;
        double lz = (pos.getZ() & 3) / 4D;

        // Chunk-local cell coordinates
        int px = pos.getX() >> 2 & 3;
        int py = pos.getY() >> 2;
        int pz = pos.getZ() >> 2 & 3;

        // Get cached noise values and interpolate
        double n000 = getCachedNoiseAt(buf, px, py, pz);
        double n001 = getCachedNoiseAt(buf, px, py, pz + 1);
        double n100 = getCachedNoiseAt(buf, px + 1, py, pz);
        double n101 = getCachedNoiseAt(buf, px + 1, py, pz + 1);
        double n010 = getCachedNoiseAt(buf, px, py + 1, pz);
        double n011 = getCachedNoiseAt(buf, px, py + 1, pz + 1);
        double n110 = getCachedNoiseAt(buf, px + 1, py + 1, pz);
        double n111 = getCachedNoiseAt(buf, px + 1, py + 1, pz + 1);

        double n00 = MathHelper.lerp(ly, n000, n010);
        double n01 = MathHelper.lerp(ly, n001, n011);
        double n10 = MathHelper.lerp(ly, n100, n110);
        double n11 = MathHelper.lerp(ly, n101, n111);

        double n0 = MathHelper.lerp(lx, n00, n10);
        double n1 = MathHelper.lerp(lx, n01, n11);

        return MathHelper.lerp(lz, n0, n1);
    }

    /**
     * Returns the noise value in the given buffer.
     */
    protected double getCachedNoiseAt(double[] buf, int x, int y, int z) {
        return buf[(x * 5 + z) * 65 + y];
    }

    /**
     * Generates the biome field for the given chunk and nearby.
     */
    protected void generateBiomeField(int cx, int cz) {
        Biome[] buf = biomeBuffer.get();

        for (int x = 0; x < 11; x++) {
            for (int z = 0; z < 11; z++) {
                int lx = x - 3;
                int lz = z - 3;

                int gx = lx + cx * 4;
                int gz = lz + cz * 4;

                Biome biome = biomeProvider.getNoiseBiome(gx, (int) baseHeight + 1, gz);

                buf[x * 11 + z] = biome;
            }
        }
    }

    /**
     * Generate the noise field buffer for the given chunk coordinates.
     */
    protected void generateNoiseField(int cx, int cz) {
        double[] buf = noiseBuffer.get();

        for (int y = 0; y < 65; y++) {
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 5; z++) {
                    buf[(x * 5 + z) * 65 + y] = generateNoiseAt(cx * 4 + x, y, cz * 4 + z, cx * 4, cz * 4);
                }
            }
        }
    }

    /**
     * Generate hill noise for the given coordinates and granularity. Higher granularity values result in a rougher and
     * cliffier noise field.
     */
    protected double genDepthNoise(double x, double z, double granularity) {
        double n = depthNoise.generate(x, z);
        double dpt = n;

        if (dpt > 0) {
            dpt *= 15;
            if (dpt > 1) {
                dpt = 1;
            }
            dpt += n * 0.2;
        } else {
            dpt = -dpt;
        }

        return MnMath.lerp(MnMath.unlerp(-1, 1, n), dpt, granularity);
    }

    /**
     * Generate the range of interpolation for a given noise column. This blends the biome terrain factors at the given
     * coordinates and uses them to generate a lower and a higher value. The final noise field is interpolated between
     * these two values.
     *
     * @return The generated lower and higher values.
     */
    protected double[] getTerrainInterpolationRange(int rx, int rz, int x, int z) {
        Biome[] biomes = biomeBuffer.get();

        double hgt = 0;  // Terrain height
        double diff = 0; // Terrain difference
        double hill = 0; // Hilliness
        double gran = 0; // Granularity

        double wgt = 0;

        Biome center = biomes[(x + 3) * 11 + z + 3];
        IMidnightBiome mncenter = IMidnightBiome.get(center);
        double chgt = mncenter.getTerrainHeight();

        for (int ix = -2; ix <= 2; ix++) {
            for (int iz = -2; iz <= 2; iz++) {
                int jx = x + ix + 3;
                int jz = z + iz + 3;

                Biome biome = biomes[jx * 11 + jz];
                IMidnightBiome mnbiome = IMidnightBiome.get(biome);

                double lhgt = mnbiome.getTerrainHeight();
                double ldiff = mnbiome.getTerrainDifference();
                double lhill = mnbiome.getTerrainHilliness();
                double lgran = mnbiome.getTerrainGranularity();

                double lwgt = 1;
                if (lhgt < chgt) {
                    lwgt /= 2; // Makes terrain a bit more exotic when going down steeper
                }

                wgt += lwgt;

                hgt += lhgt * lwgt;
                diff += ldiff * lwgt;
                hill += lhill * lwgt;
                gran += lgran * lwgt;
            }
        }

        hgt /= wgt;
        diff /= wgt;
        hill /= wgt;
        gran /= wgt;

        // Apply depth noise
        double dptn = genDepthNoise(rx + x, rz + z, gran) * hill;

        return new double[] {
            hgt - diff / 2 + baseHeight + dptn,
            hgt + diff / 2 + baseHeight + dptn
        };
    }

    /**
     * Generates the buffer of terrain heights, combining the results of {@link #getTerrainInterpolationRange} into one
     * large buffer. Saves performance a lot as we would otherwise call {@link #getTerrainInterpolationRange} many times
     * for the same column.
     */
    protected void generateTerrainHeights(int cx, int cz) {
        double[] buf = terrainBuffer.get();

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                double[] hgts = getTerrainInterpolationRange(cx * 4, cz * 4, x, z);

                buf[(x * 5 + z) * 2] = hgts[0];
                buf[(x * 5 + z) * 2 + 1] = hgts[1];
            }
        }
    }

    /**
     * Generates noise for the given cell coordinates. A cell is a region of 4x4x4 blocks.
     *
     * @param x  The cell x
     * @param y  The cell y
     * @param z  The cell z
     * @param rx The chunk root x in cell coords
     * @param rz The chunk root z in cell coords
     */
    protected double generateNoiseAt(int x, int y, int z, int rx, int rz) {
        double[] terrain = terrainBuffer.get();

        double ex = 0;
        if (y < 4) {
            int h = 4 - y;
            // Add extreme extrapolation at the bottom so that we'll never generate gaps into the void
            ex = h * h * h * 10;
        }

        int lx = x - rx;
        int lz = z - rz;

        double lo = terrain[(lx * 5 + lz) * 2];
        double hi = terrain[(lx * 5 + lz) * 2 + 1];

        double a = noiseA.generate(x, y, z) / 500;
        double b = noiseB.generate(x, y, z) / 500;

        double mix = MnMath.unlerp(-1, 1, mixNoise.generate(x, y, z));

        // Interpolate from 20 to -20 as y coordinate ranges between lo and hi
        // Adding this to the final noise field makes it generate true terrain
        double hr = MnMath.relerp(lo, hi, 20, -20, y);

        // Raw noise + Void Barrier Extrapolation + Terrain Extrapolation
        return MnMath.lerp(a, b, mix) + ex + hr;
    }
}
