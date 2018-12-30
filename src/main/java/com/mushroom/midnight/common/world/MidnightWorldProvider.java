package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.registry.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

public class MidnightWorldProvider extends WorldProvider {
    private static final Vec3d FOG_COLOR = new Vec3d(0.085, 0.04, 0.1225);
    private static final Vec3d LIGHTING_FOG_COLOR = new Vec3d(1.0, 0.35, 0.25);

    @Override
    protected void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new MidnightBiomeProvider(this.world.getWorldInfo());
        this.nether = false;
    }

    @Override
    public int getActualHeight() { return 255; }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new MidnightChunkGenerator(this.world);
    }

    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.MIDNIGHT;
    }

    @Override
    public WorldSleepResult canSleepAt(EntityPlayer player, BlockPos pos) {
        return WorldSleepResult.BED_EXPLODES;
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
    public boolean hasSkyLight() {
        return false;
    }

    @Override
    public float getSunBrightnessFactor(float partialTicks) {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float partialTicks) {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        if (this.world.getLastLightningBolt() > 0) {
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
    public void updateWeather() {
        if (this.world instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) this.world;
            PlayerChunkMap chunkMap = worldServer.getPlayerChunkMap();
            Random rand = this.world.rand;

            Iterator<Chunk> iterator = this.world.getPersistentChunkIterable(chunkMap.getChunkIterator());
            iterator.forEachRemaining(chunk -> {
                int globalX = chunk.x << 4;
                int globalZ = chunk.z << 4;
                if (rand.nextInt(200000) == 0) {
                    int lightningX = globalX + rand.nextInt(16);
                    int lightningZ = globalZ + rand.nextInt(16);
                    BlockPos pos = this.world.getPrecipitationHeight(new BlockPos(lightningX, 0, lightningZ));

                    Entity lightning = new EntityLightningBolt(this.world, pos.getX(), pos.getY(), pos.getZ(), true);
                    this.world.addWeatherEffect(lightning);
                }
            });
        }
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return MidnightConfig.canRespawnInMidnight;
    }

    @Override
    public boolean shouldClientCheckLighting() {
        return true;
    }

    @Override
    @Nullable
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer() {
        return new IRenderHandler() {
            @Override
            public void render(float partialTicks, WorldClient world, Minecraft mc) {
            }
        };
    }
}
