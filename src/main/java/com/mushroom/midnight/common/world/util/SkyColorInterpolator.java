package com.mushroom.midnight.common.world.util;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT)
public final class SkyColorInterpolator {
    public static final SkyColorInterpolator INSTANCE = new SkyColorInterpolator();

    private static final double EPS = 1e-6;
    private static final double LERP_SPEED = 0.003;

    private double red;
    private double green;
    private double blue;

    private double lastRed;
    private double lastGreen;
    private double lastBlue;

    private boolean initialized;

    private SkyColorInterpolator() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !MidnightUtil.isMidnightDimension(player.world)) return;

        INSTANCE.update(player);
    }

    private void update(PlayerEntity player) {
        int playerX = MathHelper.floor(player.posX);
        int playerZ = MathHelper.floor(player.posZ);

        Chunk chunk = player.world.getChunkProvider().func_225313_a(playerX >> 4, playerZ >> 4);
        if (chunk == null) return;

        Biome biome = chunk.getBiomes()[(playerX & 15) | (playerZ & 15) << 4];
        int skyColor = biome.getSkyColorByTemp(0.0F);

        double red = (skyColor >> 16 & 0xFF) / 255.0;
        double green = (skyColor >> 8 & 0xFF) / 255.0;
        double blue = (skyColor & 0xFF) / 255.0;

        this.updateValues(red, green, blue);
    }

    private void updateValues(double targetRed, double targetGreen, double targetBlue) {
        if (!this.initialized) {
            this.red = this.lastRed = targetRed;
            this.green = this.lastGreen = targetGreen;
            this.blue = this.lastBlue = targetBlue;
            this.initialized = true;
            return;
        }

        this.lastRed = this.red;
        this.lastGreen = this.green;
        this.lastBlue = this.blue;

        this.red = updateValue(this.red, targetRed);
        this.green = updateValue(this.green, targetGreen);
        this.blue = updateValue(this.blue, targetBlue);
    }

    private static double updateValue(double value, double target) {
        double delta = target - value;
        if (Math.abs(delta) < EPS) return target;

        if (delta > 0.0) {
            return Math.min(value + LERP_SPEED, target);
        } else {
            return Math.max(value - LERP_SPEED, target);
        }
    }

    public Vec3d get(float partialTicks) {
        double red = this.lastRed + (this.red - this.lastRed) * partialTicks;
        double green = this.lastGreen + (this.green - this.lastGreen) * partialTicks;
        double blue = this.lastBlue + (this.blue - this.lastBlue) * partialTicks;
        return new Vec3d(red, green, blue);
    }
}
