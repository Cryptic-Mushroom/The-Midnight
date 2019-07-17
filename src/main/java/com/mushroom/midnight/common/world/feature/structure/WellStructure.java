package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.common.registry.MidnightFeatures;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class WellStructure extends Structure<NoFeatureConfig> {
    public WellStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49910_1_) {
        super(p_i49910_1_);
    }

    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int i = 16;//distance
        int j = 8;//separation


        int k = x + i * spacingOffsetsX;
        int l = z + i * spacingOffsetsZ;
        int i1 = k < 0 ? k - i + 1 : k;
        int j1 = l < 0 ? l - i + 1 : l;
        int k1 = i1 / i;
        int l1 = j1 / i;
        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 14357618);
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + random.nextInt(i - j);
        l1 = l1 + random.nextInt(i - j);
        return new ChunkPos(k1, l1);
    }


    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));

        if (chunkGen.hasStructure(biome, MidnightFeatures.WELL)) {
            return true;
        }

        return false;
    }

    public Structure.IStartFactory getStartFactory() {
        return WellStructure.Start::new;
    }

    public String getStructureName() {
        return "Well";
    }

    public int getSize() {
        return 3;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> p_i51165_1_, int p_i51165_2_, int p_i51165_3_, Biome p_i51165_4_, MutableBoundingBox p_i51165_5_, int p_i51165_6_, long p_i51165_7_) {
            super(p_i51165_1_, p_i51165_2_, p_i51165_3_, p_i51165_4_, p_i51165_5_, p_i51165_6_, p_i51165_7_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);
            WellPieces.generateWell(templateManagerIn, blockpos, rotation, this.components, this.rand);
            this.recalculateStructureSize();
        }

        public BlockPos getPos() {
            return new BlockPos((this.getChunkPosX() << 4) + 9, 0, (this.getChunkPosZ() << 4) + 9);
        }
    }
}