package com.mushroom.midnight.common.world;

import com.mushroom.midnight.client.render.MidnightSkyRenderer;
import com.mushroom.midnight.common.biome.BiomeLayerType;
import com.mushroom.midnight.common.biome.BiomeLayers;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IRenderHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class MidnightDimension extends Dimension {
    public MidnightDimension(World world, DimensionType type) {
        super(world, type, 0.0F);

        float baseLight = 0.0F;
        for (int i = 0; i <= 15; ++i) {
            float alpha = 1.0F - i / 14.0F;
            float brightness = (1.0F - alpha) / (alpha * 10.0F + 1.0F);
            this.lightBrightnessTable[i] = (float) (Math.pow(brightness, 2.5F) * 3.0F) + baseLight;
        }
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
        return 256;
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
    public float getLightBrightness(int p_227174_1_) {
        return super.getLightBrightness(p_227174_1_);
    }

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, Vector3f colors) {
    	float colors0 = blockLight * 0.93F + 0.07F;
        float colors1 = blockLight * 0.96F + 0.03F;
        float colors2 = blockLight * 0.94F + 0.16F;
        if (Minecraft.getInstance().world != null && Minecraft.getInstance().world.getTimeLightningFlash() > 0) {
            float undergroundFactor = (float) MidnightAtmosphereController.INSTANCE.getUndergroundFactor();
            colors0 = MathHelper.lerp(undergroundFactor, 0.95F, colors0);
            colors1 = MathHelper.lerp(undergroundFactor, 0.3F, colors1);
            colors2 = MathHelper.lerp(undergroundFactor, 0.3F, colors2);
        }
        colors.set(colors0 + colors.getX() * 4, colors1 + colors.getY() * 4, colors2 + colors.getZ() * 4);
    }

    @Override
    public boolean isDaytime() {
        return false;
    }

    @Override
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return MidnightAtmosphereController.INSTANCE.computeSkyColor();
    }

    public Vec3d getSkyColor(BlockPos cameraPos, float partialTicks) {
        return MidnightAtmosphereController.INSTANCE.computeSkyColor();
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

            // TODO: NEEDS ATTENTION!
            if (MidnightConfig.logic.randomLightnings.get()) {
                chunkProvider.chunkManager.getLoadedChunksIterable().forEach(chunkHolder -> {
                    chunkHolder.getEntityTickingFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left().ifPresent(chunk -> {
                        Random rand = this.world.rand;

                        ChunkPos chunkPos = chunkHolder.getPosition();
                        if (!chunkProvider.chunkManager.isOutsideSpawningRadius(chunkPos)) { // TODO: NEEDS ATTENTION!
                            int globalX = chunkPos.getXStart();
                            int globalZ = chunkPos.getZStart();
                            if (rand.nextInt(200000) == 0) {
                                int lightningX = globalX + rand.nextInt(16);
                                int lightningZ = globalZ + rand.nextInt(16);
                                BlockPos pos = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING, new BlockPos(lightningX, 0, lightningZ));

                                LightningBoltEntity lightning = new LightningBoltEntity(this.world, pos.getX(), pos.getY(), pos.getZ(), !MidnightConfig.logic.allowLightningDamage.get());
                                serverWorld.addLightningBolt(lightning);
                            }
                        }
                    });
                });
            }
        }
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return MidnightConfig.logic.canRespawnInMidnight.get();
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    @Override
    public double getVoidFogYFactor() {
        return 1.0;
    }

    @Nullable
    @Override
    public IRenderHandler getSkyRenderer() {
        return MidnightSkyRenderer.INSTANCE;
    }
}
