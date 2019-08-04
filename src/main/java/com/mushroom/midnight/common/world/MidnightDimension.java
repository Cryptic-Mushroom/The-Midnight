package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.BiomeLayerType;
import com.mushroom.midnight.common.biome.BiomeLayers;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.registry.MidnightDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MidnightDimension extends Dimension {
    private static final Vec3d FOG_COLOR = new Vec3d(0.085, 0.04, 0.1225);
    private static final Vec3d LIGHTING_FOG_COLOR = new Vec3d(1.0, 0.35, 0.25);

    public MidnightDimension(World world, DimensionType type) {
        super(world, type);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        long seed = this.world.getSeed();

        BiomeLayers<Biome> surfaceLayers = BiomeLayerType.SURFACE.make(seed);
        BiomeLayers<CavernousBiome> undergroundLayers = BiomeLayerType.UNDERGROUND.make(seed);

        return new MidnightChunkGenerator(this.world, surfaceLayers, undergroundLayers, MidnightChunkGenerator.Config.createDefault());
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunk, boolean checkValid) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return null;
    }

    @Override
    public int getActualHeight() {
        return 255;
    }

    @Override
    public DimensionType getType() {
        return MidnightDimensions.midnight();
    }

    @Override
    public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
        return SleepResult.BED_EXPLODES;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public boolean shouldMapSpin(String entity, double x, double z, double rotation) {
        return true;
    }

    @Override
    protected void generateLightBrightnessTable() {
        float baseLight = 0.06F;
        for (int i = 0; i <= 15; ++i) {
            float alpha = 1.0F - i / 15.0F;
            float brightness = (1.0F - alpha) / (alpha * 10.0F + 1.0F);
            this.lightBrightnessTable[i] = (float) (Math.pow(brightness, 2.5F) * 3.0F) + baseLight;
        }
    }

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
        colors[0] = blockLight * 0.93F + 0.07F;
        colors[1] = blockLight * 0.96F + 0.03F;
        colors[2] = blockLight * 0.94F + 0.16F;
        if (this.world.getLastLightningBolt() > 0) {
            colors[0] = 0.95F;
            colors[1] = 0.3F;
            colors[2] = 0.3F;
        }
    }

    @Override
    public boolean isDaytime() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getSunBrightness(float partialTicks) {
        return 0.0F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        if (this.world.getLastLightningBolt() > 0 && Minecraft.getInstance().player.posY > 50) {
            return LIGHTING_FOG_COLOR;
        }
        return FOG_COLOR;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5F;
    }

    @Override
    public void calculateInitialWeather() {
    }

    @Override
    public void updateWeather(Runnable defaultLogic) {
        //this.setAllowedSpawnTypes(false, false);

        if (this.world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) this.world;
            ServerChunkProvider chunkProvider = serverWorld.getChunkProvider();
            // disable here for custom spawner if needed
            //chunkProvider.setAllowedSpawnTypes(false, false);

            // TODO
            /*chunkProvider.chunkManager.func_223491_f().forEach(chunkHolder -> {
                chunkHolder.func_219297_b().getNow(ChunkHolder.UNLOADED_CHUNK).left().ifPresent(chunk -> {
                    Random rand = this.world.rand;

                    ChunkPos chunkPos = chunkHolder.getPosition();
                    if (!chunkProvider.chunkManager.isOutsideSpawningRadius(chunkPos)) {
                        int globalX = chunkPos.getXStart();
                        int globalZ = chunkPos.getZStart();
                        if (rand.nextInt(200000) == 0) {
                            int lightningX = globalX + rand.nextInt(16);
                            int lightningZ = globalZ + rand.nextInt(16);
                            BlockPos pos = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING, new BlockPos(lightningX, 0, lightningZ));

                            LightningBoltEntity lightning = new LightningBoltEntity(this.world, pos.getX(), pos.getY(), pos.getZ(), !MidnightConfig.general.allowLightningDamage.get());
                            serverWorld.addLightningBolt(lightning);
                        }
                    }
                });
            });*/
        }
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return MidnightConfig.general.canRespawnInMidnight.get();
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }
}
