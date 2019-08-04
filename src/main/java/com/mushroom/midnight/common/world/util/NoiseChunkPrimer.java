package com.mushroom.midnight.common.world.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.Heightmap;

public class NoiseChunkPrimer {
    private final int horizontalGranularity;
    private final int verticalGranularity;

    private final int noiseWidth;
    private final int noiseHeight;

    public NoiseChunkPrimer(int horizontalGranularity, int verticalGranularity, int noiseWidth, int noiseHeight) {
        this.horizontalGranularity = horizontalGranularity;
        this.verticalGranularity = verticalGranularity;
        this.noiseWidth = noiseWidth;
        this.noiseHeight = noiseHeight;
    }

    public void primeChunk(ChunkPrimer primer, double[] sampledNoise, Handler handler) {
        Heightmap oceanFloor = primer.func_217303_b(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap worldSurface = primer.func_217303_b(Heightmap.Type.WORLD_SURFACE_WG);

        int sampleWidth = this.noiseWidth + 1;
        int sampleHeight = this.noiseHeight + 1;

        double noiseScaleXZ = 1.0 / this.horizontalGranularity;
        double noiseScaleY = 1.0 / this.verticalGranularity;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int noiseZ = 0; noiseZ < this.noiseWidth; noiseZ++) {
            int indexZ = noiseZ * sampleWidth;
            int indexZDown = (noiseZ + 1) * sampleWidth;

            for (int noiseX = 0; noiseX < this.noiseWidth; noiseX++) {
                int indexX = (indexZ + noiseX) * sampleHeight;
                int indexXRight = (indexZ + noiseX + 1) * sampleHeight;
                int indexXDown = (indexZDown + noiseX) * sampleHeight;
                int indexXDownRight = (indexZDown + noiseX + 1) * sampleHeight;

                ChunkSection section = primer.func_217332_a(15);
                section.lock();

                for (int noiseY = 0; noiseY < this.noiseHeight; noiseY++) {
                    double valueOrigin = sampledNoise[indexX + noiseY];
                    double valueDown = sampledNoise[indexXRight + noiseY];
                    double valueRight = sampledNoise[indexXDown + noiseY];
                    double valueDownRight = sampledNoise[indexXDownRight + noiseY];

                    double stepOrigin = (sampledNoise[indexX + noiseY + 1] - valueOrigin) * noiseScaleY;
                    double stepDown = (sampledNoise[indexXRight + noiseY + 1] - valueDown) * noiseScaleY;
                    double stepRight = (sampledNoise[indexXDown + noiseY + 1] - valueRight) * noiseScaleY;
                    double stepDownRight = (sampledNoise[indexXDownRight + noiseY + 1] - valueDownRight) * noiseScaleY;

                    for (int intY = 0; intY < this.verticalGranularity; intY++) {
                        double originZ = valueOrigin;
                        double targetZ = valueDown;
                        double verticalStepZ1 = (valueRight - valueOrigin) * noiseScaleXZ;
                        double verticalStepZ2 = (valueDownRight - valueDown) * noiseScaleXZ;

                        int y = noiseY * this.verticalGranularity + intY;

                        int sectionY = y >> 4;
                        if (section.getYLocation() >> 4 != sectionY) {
                            section.unlock();
                            section = primer.func_217332_a(sectionY);
                            section.lock();
                        }

                        for (int intZ = 0; intZ < this.horizontalGranularity; intZ++) {
                            double densityStep = (targetZ - originZ) * noiseScaleXZ;
                            double density = originZ;

                            int z = noiseZ * this.horizontalGranularity + intZ;

                            for (int intX = 0; intX < this.horizontalGranularity; intX++) {
                                int x = noiseX * this.horizontalGranularity + intX;

                                BlockState state = handler.getState(density, x, y, z);
                                if (state != null) {
                                    if (state.getLightValue() > 0) {
                                        mutablePos.setPos(x, y, z);
                                        primer.addLightPosition(mutablePos);
                                    }

                                    int localX = x & 15;
                                    int localY = y & 15;
                                    int localZ = z & 15;

                                    section.setBlockState(localX, localY, localZ, state, false);
                                    oceanFloor.update(localX, y, localZ, state);
                                    worldSurface.update(localX, y, localZ, state);
                                }

                                density += densityStep;
                            }

                            originZ += verticalStepZ1;
                            targetZ += verticalStepZ2;
                        }

                        valueOrigin += stepOrigin;
                        valueDown += stepDown;
                        valueRight += stepRight;
                        valueDownRight += stepDownRight;
                    }
                }

                section.unlock();
            }
        }
    }

    public interface Handler {
        BlockState getState(double density, int x, int y, int z);
    }
}
