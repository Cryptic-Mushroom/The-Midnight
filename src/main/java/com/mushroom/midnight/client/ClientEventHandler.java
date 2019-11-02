package com.mushroom.midnight.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.registry.MidnightParticleTypes;
import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import com.mushroom.midnight.common.util.EntityUtil;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.util.ResetHookHandler;
import com.mushroom.midnight.common.world.MidnightAtmosphereController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT)
public class ClientEventHandler {
    private static final Minecraft CLIENT = Minecraft.getInstance();

    private static final double HOOK_SENSITIVITY = 0.2;
    private static final ResetHookHandler<Double> SENSITIVITY_HOOK = new ResetHookHandler<>(HOOK_SENSITIVITY)
            .getValue(() -> CLIENT.gameSettings.mouseSensitivity)
            .setValue(sensitivity -> CLIENT.gameSettings.mouseSensitivity = sensitivity);

    private static final long AMBIENT_SOUND_INTERVAL = 140;
    private static final int AMBIENT_SOUND_CHANCE = 120;

    private static long lastAmbientSoundTime;

    private static ISound playingMusic;
    public static float flicker;
    public static float prevFlicker;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            prevFlicker = flicker;

            double targetFlicker = Math.random();
            targetFlicker *= targetFlicker;

            flicker += (targetFlicker - flicker) * 0.5;
        }
        if (!CLIENT.isGamePaused()) {
            ClientPlayerEntity player = CLIENT.player;
            if (player == null) {
                return;
            }

            if (event.phase == TickEvent.Phase.END) {
                if (playingMusic != null && !CLIENT.getSoundHandler().isPlaying(playingMusic)) {
                    playingMusic = null;
                }

                if (MidnightUtil.isMidnightDimension(player.world)) {
                    spawnAmbientParticles(player);
                    playAmbientSounds(player);
                }

                SENSITIVITY_HOOK.apply(player.isPotionActive(MidnightEffects.STUNNED));
            }
        }
    }

    private static void cancelSleep(ClientPlayerEntity player) {
        ClientPlayNetHandler handler = player.connection;
        handler.sendPacket(new CEntityActionPacket(player, CEntityActionPacket.Action.STOP_SLEEPING));
    }

    private static void playAmbientSounds(PlayerEntity player) {
        Random rand = player.world.rand;
        long worldTime = player.world.getGameTime();

        if (worldTime - lastAmbientSoundTime > AMBIENT_SOUND_INTERVAL && rand.nextInt(AMBIENT_SOUND_CHANCE) == 0) {
            ResourceLocation ambientSound = MidnightSounds.AMBIENT.getName();

            float volume = rand.nextFloat() * 0.4F + 0.8F;
            float pitch = rand.nextFloat() * 0.6F + 0.7F;

            float x = (float) (player.posX + rand.nextFloat() - 0.5F);
            float y = (float) (player.posY + rand.nextFloat() - 0.5F);
            float z = (float) (player.posZ + rand.nextFloat() - 0.5F);

            ISound sound = new SimpleSound(ambientSound, SoundCategory.AMBIENT, volume, pitch, false, 0, ISound.AttenuationType.NONE, x, y, z, false);
            CLIENT.getSoundHandler().play(sound);

            lastAmbientSoundTime = worldTime;
        }
    }

    @SubscribeEvent
    public static void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        Entity entity = event.getInfo().getRenderViewEntity();

        if (MidnightUtil.isMidnightDimension(entity.world)) {
            float farDistance = event.getFarPlaneDistance();

            float fogStart = (float) MidnightAtmosphereController.INSTANCE.getFogStart();
            float fogEnd = (float) MidnightAtmosphereController.INSTANCE.getFogEnd();

            GlStateManager.fogStart(Math.min(fogStart, farDistance));
            GlStateManager.fogEnd(Math.min(fogEnd, farDistance));
        }
    }

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        if (!FluidImmersionRenderer.immersedFluid.isEmpty()) {
            return;
        }

        LivingEntity entity = event.getInfo().getRenderViewEntity() instanceof LivingEntity ? (LivingEntity) event.getInfo().getRenderViewEntity() : null;
        if (entity != null && !entity.isPotionActive(Effects.BLINDNESS)) {
            if (entity.isPotionActive(MidnightEffects.DARKNESS)) {
                GlStateManager.fogMode(GlStateManager.FogMode.EXP);
                event.setCanceled(true);
                event.setDensity(0.15f);
            } else if (entity.isPotionActive(MidnightEffects.STUNNED)) {
                GlStateManager.fogMode(GlStateManager.FogMode.EXP);
                event.setCanceled(true);
                event.setDensity(0.15f);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSetupFogColor(EntityViewRenderEvent.FogColors event) {
        if (event.getInfo().getRenderViewEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getInfo().getRenderViewEntity();
            if (entity.isPotionActive(MidnightEffects.STUNNED)) {
                event.setRed(0.1F);
                event.setGreen(0.1F);
                event.setBlue(0.1F);
            } else if (entity.isPotionActive(MidnightEffects.DARKNESS)) {
                event.setRed(0f);
                event.setGreen(0f);
                event.setBlue(0f);
            }
        }
    }

    private static void spawnAmbientParticles(PlayerEntity player) {
        Random random = player.world.rand;
        double originX = player.posX;
        double originY = player.posY;
        double originZ = player.posZ;
        for (int i = 0; i < 6; i++) {
            double particleX = originX + (random.nextInt(24) - random.nextInt(24));
            double particleY = originY + (random.nextInt(24) - random.nextInt(24));
            double particleZ = originZ + (random.nextInt(24) - random.nextInt(24));
            double velocityX = (random.nextDouble() - 0.5) * 0.04;
            double velocityY = (random.nextDouble() - 0.5) * 0.04;
            double velocityZ = (random.nextDouble() - 0.5) * 0.04;
            player.world.addParticle(MidnightParticleTypes.AMBIENT_SPORE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }

    public static void onApplyRotations(LivingEntity entity) {
        boolean captured = RifterCapturable.isCaptured(entity);
        if (captured) {
            entity.limbSwing = 0.0F;
            entity.limbSwingAmount = entity.prevLimbSwingAmount = 0.0F;

            EntityUtil.Stance stance = EntityUtil.getStance(entity);
            if (stance == EntityUtil.Stance.QUADRUPEDAL) {
                GlStateManager.translatef(0.0F, entity.getHeight(), 0.0F);
                GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
            } else {
                GlStateManager.translatef(0.0F, entity.getWidth() / 2.0F, 0.0F);
                GlStateManager.translatef(0.0F, 0.0F, entity.getHeight() / 2.0F);
                GlStateManager.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (!isMusicSound()) {
            return;
        }

        if (CLIENT.player != null && MidnightUtil.isMidnightDimension(CLIENT.player.world)) {
            SoundEvent sound = getMusicSound(CLIENT.player);
            if (sound == null || playingMusic != null) {
                event.setResultSound(null);
                return;
            }

            playingMusic = SimpleSound.music(sound);

            event.setResultSound(playingMusic);
        }
    }

    @Nullable
    private static SoundEvent getMusicSound(PlayerEntity player) {
        Biome biome = player.world.getBiome(player.getPosition());
        if (biome == MidnightSurfaceBiomes.CRYSTAL_SPIRES) {
            return MidnightSounds.MUSIC_CRYSTAL;
        } else if (biome == MidnightSurfaceBiomes.DECEITFUL_BOG) {
            return MidnightSounds.MUSIC_DARK_WILLOW;
        }
        return MidnightSounds.MUSIC_GENERIC;
    }

    private static boolean isMusicSound() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return Arrays.stream(stackTrace).anyMatch(e -> e.getClassName().equals(MusicTicker.class.getName()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderVignetteOverLay(RenderGameOverlayEvent.Pre event) {
        if (MidnightConfig.client.hideVignetteEffect.get() && event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE) {
            if (MidnightUtil.isMidnightDimension(CLIENT.world)) {
                WorldBorder worldborder = CLIENT.world.getWorldBorder();
                float distWarn = Math.max(worldborder.getWarningDistance(), (float) Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000d, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter())));
                if (worldborder.getClosestDistance(CLIENT.player) >= distWarn) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
