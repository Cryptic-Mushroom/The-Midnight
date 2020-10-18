/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.world.levelgen.surface;

import com.mojang.serialization.Codec;
import midnight.common.block.MnBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

import net.shadew.ptg.noise.Noise3D;
import net.shadew.ptg.noise.opensimplex.FractalOpenSimplex3D;

public class DeceitfulBogSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    private static final BlockState COARSE_DIRT = MnBlocks.COARSE_NIGHT_DIRT.getDefaultState();
    private static final BlockState DIRT = MnBlocks.NIGHT_DIRT.getDefaultState();
    private static final BlockState GRASS = MnBlocks.NIGHT_GRASS_BLOCK.getDefaultState();
    private static final BlockState MUD = MnBlocks.DECEITFUL_MUD.getDefaultState();
    private static final BlockState PEAT = MnBlocks.DECEITFUL_PEAT.getDefaultState();

    private static final BlockState[] TOP_BLOCKS = {
        GRASS, COARSE_DIRT, MUD, PEAT
    };
    private static final BlockState[] UNDER_BLOCKS = {
        DIRT, COARSE_DIRT, MUD, PEAT
    };

    private Noise3D mainSoilNoise;
    private Noise3D dirtSoilNoise;
    private Noise3D mudSoilNoise;

    public DeceitfulBogSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random rand, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.buildSurface(rand, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random rand, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState top, BlockState under, BlockState underwater, int sealevel) {
        BlockState placeTop = top;
        BlockState placeUnder = under;
        BlockPos.Mutable mpos = new BlockPos.Mutable();
        int control = -1;
        int depth = (int) (noise / 3 + 3 + rand.nextDouble() * 0.25);
        int lx = x & 15;
        int lz = z & 15;

        for(int y = startHeight; y >= 0; --y) {
            mpos.setPos(lx, y, lz);
            BlockState currentState = chunk.getBlockState(mpos);
            if(currentState.isAir(chunk, mpos)) {
                control = -1;
            } else if(currentState.getBlock() == defaultBlock.getBlock()) {
                if(control == -1) {
                    if(depth <= 0) {
                        placeTop = MUD;
                        placeUnder = MUD;
                    } else if(y >= sealevel - 4 && y <= sealevel + 1) {
                        int soil = getSoilTypeAt(x, y, z);
                        placeTop = TOP_BLOCKS[soil];
                        placeUnder = UNDER_BLOCKS[soil];
                    }

                    control = depth;
                    if(y >= sealevel - 1) {
                        chunk.setBlockState(mpos, placeTop, false);
                    } else {
                        chunk.setBlockState(mpos, MUD, false);
                        placeUnder = MUD;
                    }
                } else if(control > 0) {
                    control--;
                    chunk.setBlockState(mpos, placeUnder, false);
                }
            }
        }

    }

    private int getSoilTypeAt(int x, int y, int z) {
        double n1 = mainSoilNoise.generate(x, y, z);
        if(n1 < 0.1) {
            double n2 = dirtSoilNoise.generate(x, y, z);
            if(n2 < 0.2) {
                return 0; // Grass
            } else {
                return 1; // Coarse dirt
            }
        } else {
            double n3 = mudSoilNoise.generate(x, y, z);
            if(n3 < 0) {
                return 2; // Mud
            } else {
                return 3; // Peat
            }
        }
    }

    @Override
    public void setSeed(long seed) {
        Random rand = new Random(seed);
        mainSoilNoise = new FractalOpenSimplex3D(rand.nextInt(), 42.971075, 16);
        dirtSoilNoise = new FractalOpenSimplex3D(rand.nextInt(), 17.5722318, 7);
        mudSoilNoise = new FractalOpenSimplex3D(rand.nextInt(), 9.2518499, 7);
    }
}
