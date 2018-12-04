package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class MidnightBiome extends Biome implements IMidnightBiome {
    protected final MidnightBiomeConfig config;
    private final SurfaceConfig surfaceConfig;
    private final SurfaceConfig localSurfaceConfig;

    public MidnightBiome(BiomeProperties properties, MidnightBiomeConfig config) {
        super(properties);

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();

        this.spawnableMonsterList.addAll(config.getMonsterSpawns());
        this.spawnableCreatureList.addAll(config.getCreatureSpawns());

        this.config = config;
        this.decorator = new MidnightBiomeDecorator(config);

        this.surfaceConfig = config.getSurfaceConfig();
        this.localSurfaceConfig = new SurfaceConfig(this.surfaceConfig);

        this.topBlock = this.surfaceConfig.getTopState();
        this.fillerBlock = this.surfaceConfig.getFillerState();
    }

    @Override
    public String getBiomeName() {
        return I18n.format("biome." + Midnight.MODID + "." + super.getBiomeName() + ".name");
    }

    @Override
    public float getRidgeWeight() {
        return this.config.getRidgeWeight();
    }

    @Override
    public float getDensityScale() {
        return this.config.getDensityScale();
    }

    @Override
    public boolean isWet() {
        return this.config.isWet();
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, ChunkPrimer primer, int z, int x, double noiseVal) {
        this.configureSurface(this.localSurfaceConfig, this.surfaceConfig, x, z, rand);

        IBlockState chosenTopBlock = this.localSurfaceConfig.getTopState();
        IBlockState chosenFillerBlock = this.localSurfaceConfig.getFillerState();
        IBlockState chosenWetBlock = this.localSurfaceConfig.getWetState();

        int seaLevel = world.getSeaLevel();
        IBlockState topBlock = chosenTopBlock;
        IBlockState fillerBlock = chosenFillerBlock;

        int currentDepth = -1;
        int fillerDepth = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int localZ = z & 15;
        int localX = x & 15;

        boolean wet = false;
        for (int height = 255; height >= 0; --height) {
            if (height <= rand.nextInt(5)) {
                primer.setBlockState(localX, height, localZ, BEDROCK);
            } else {
                IBlockState state = primer.getBlockState(localX, height, localZ);
                if (state.getMaterial() == Material.WATER) {
                    wet = true;
                }
                if (state.getMaterial() == Material.AIR) {
                    currentDepth = -1;
                } else if (state.getBlock() == ModBlocks.NIGHTSTONE) {
                    if (currentDepth == -1) {
                        if (fillerDepth <= 0) {
                            topBlock = AIR;
                            fillerBlock = ModBlocks.NIGHTSTONE.getDefaultState();
                        } else if (height >= seaLevel - 4 && height <= seaLevel + 1) {
                            topBlock = chosenTopBlock;
                            fillerBlock = chosenFillerBlock;
                        }

                        currentDepth = fillerDepth;

                        primer.setBlockState(localX, height, localZ, wet ? chosenWetBlock : topBlock);
                    } else if (currentDepth > 0) {
                        --currentDepth;
                        primer.setBlockState(localX, height, localZ, wet ? chosenWetBlock : fillerBlock);
                    }
                }
            }
        }
    }

    protected SurfaceConfig configureSurface(SurfaceConfig config, SurfaceConfig parent, int x, int z, Random random) {
        return config;
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return this.getModdedBiomeGrassColor(this.config.getGrassColor());
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
        return this.getModdedBiomeFoliageColor(this.config.getFoliageColor());
    }
}
