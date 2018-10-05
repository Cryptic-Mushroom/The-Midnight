package com.mushroom.midnight.common.biomes;

import com.mushroom.midnight.common.world.decorator.VigilantDecorator;
import com.mushroom.midnight.common.world.generator.WorldGenShadowrootTrees;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeVigilantForest extends BiomeBase {

    public static final WorldGenShadowrootTrees SHADOWROOT_TREE_GEN = new WorldGenShadowrootTrees();

    public BiomeVigilantForest(BiomeProperties properties) {
        super(properties);

        this.decorator = new VigilantDecorator();

        this.decorator.treesPerChunk = 8;
        this.decorator.grassPerChunk = 10;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return SHADOWROOT_TREE_GEN;
    }

    //  @Override
    //  public void genBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
    //    super.genBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    //    int xRand = rand.nextInt(32) - 16;
    //    int zRand = rand.nextInt(32) - 16;
    //
    //    for (int height = 255; height >= 0; --height) {
    //      IBlockState iblockstate2 = chunkPrimerIn.getBlockState(x +xRand, height, z + zRand);
    //      if(iblockstate2.getBlock() == ModBlocks.MIDNIGHT_GRASS){
    //        int randomChance = rand.nextInt(50);
    //        if(randomChance == 0){
    //          BlockPos pos = new BlockPos(x +xRand, height + 1, z + zRand);
    //          chunkPrimerIn.setBlockState(pos.getX(), pos.getY(), pos.getZ(), Blocks.LIT_PUMPKIN.getDefaultState());
    //        }
    //        if(randomChance == 1){
    //          BlockPos pos = new BlockPos(x +xRand, height + 1, z + zRand);
    //          chunkPrimerIn.setBlockState(pos.getX(), pos.getY(), pos.getZ(), Blocks.PUMPKIN.getDefaultState());
    //        }
    //      }
    //    }
    //
    //  }
}
