package com.mushroom.midnight.common.world.generator;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.feature.CrystalClusterFeature;
import com.mushroom.midnight.common.world.noise.INoiseSampler;
import com.mushroom.midnight.common.world.noise.OctaveNoiseSampler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightRouxeGenerator {
    private static final long SEED = 6035435416693430887L;
    private static final double[] SAMPLE_BUFFER = new double[1];

    private static final WorldGenerator CRYSTAL_GENERATOR = new CrystalClusterFeature(2, 3,
            ModBlocks.ROUXE_ROCK.getDefaultState(),
            ModBlocks.ROUXE.getDefaultState()
    );

    private static final int CRYSTALS_PER_CHUNK = 10;
    private static final int CRYSTAL_RETRIES = 8;

    private static final Map<World, INoiseSampler> NOISE_SAMPLERS = new WeakHashMap<>();

    @SubscribeEvent
    public static void onDecorate(DecorateBiomeEvent.Pre event) {
        World world = event.getWorld();
        if (!Helper.isMidnightDimension(world)) {
            return;
        }

        INoiseSampler sampler = getNoiseSampler(world);

        ChunkPos chunkPos = event.getChunkPos();
        int globalX = chunkPos.getXStart() + 8;
        int globalZ = chunkPos.getZStart() + 8;

        sampler.sample2D(SAMPLE_BUFFER, globalX + 8, globalZ + 8, 1, 1);

        if (SAMPLE_BUFFER[0] > 0.0) {
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
            Random random = event.getRand();

            for (int i = 0; i < CRYSTALS_PER_CHUNK; i++) {
                int offsetX = random.nextInt(16);
                int offsetZ = random.nextInt(16);
                mutablePos.setPos(globalX + offsetX, 0, globalZ + offsetZ);

                int height = world.getHeight(mutablePos).getY() / 2;
                if (height > 0) {
                    tryGenerateCrystal(world, mutablePos, random, height);
                }
            }
        }
    }

    private static void tryGenerateCrystal(World world, BlockPos.MutableBlockPos mutablePos, Random random, int height) {
        for (int i = 0; i < CRYSTAL_RETRIES; i++) {
            mutablePos.setY(random.nextInt(height));
            if (CRYSTAL_GENERATOR.generate(world, random, mutablePos)) {
                break;
            }
        }
    }

    private static INoiseSampler getNoiseSampler(World world) {
        return NOISE_SAMPLERS.computeIfAbsent(world, w -> {
            Random random = new Random(w.getSeed() ^ SEED);
            OctaveNoiseSampler noise = OctaveNoiseSampler.perlin(random, 2);
            noise.setFrequency(0.01);
            return noise;
        });
    }
}
