package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.client.sound.IdleRiftSound;
import com.mushroom.midnight.common.capability.RifterCapturedCapability;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModBiomes;
import com.mushroom.midnight.common.registry.ModEffects;
import com.mushroom.midnight.common.registry.ModSounds;
import com.mushroom.midnight.common.util.EntityUtil;
import com.mushroom.midnight.common.util.ResetHookHandler;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class ClientEventHandler {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final float HOOK_SENSITIVITY = 0.2F;
    private static final ResetHookHandler<Float> SENSITIVITY_HOOK = new ResetHookHandler<>(HOOK_SENSITIVITY)
            .getValue(() -> MC.gameSettings.mouseSensitivity)
            .setValue(sensitivity -> MC.gameSettings.mouseSensitivity = sensitivity);

    private static final long AMBIENT_SOUND_INTERVAL = 140;
    private static final int AMBIENT_SOUND_CHANCE = 120;

    private static long lastAmbientSoundTime;

    private static ISound playingMusic;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!MC.isGamePaused()) {
            EntityPlayerSP player = MC.player;
            if (player == null) {
                return;
            }

            if (event.phase == TickEvent.Phase.END) {
                if (playingMusic != null && !MC.getSoundHandler().isSoundPlaying(playingMusic)) {
                    playingMusic = null;
                }

                if (Helper.isMidnightDimension(player.world)) {
                    spawnAmbientParticles(player);
                    playAmbientSounds(player);
                } else {
                    pullSelfPlayer(player);
                }

                SENSITIVITY_HOOK.apply(player.isPotionActive(ModEffects.STUNNED));
            } else if (event.phase == TickEvent.Phase.START) {
                GlobalBridgeManager.getClient().update();
            }
        }
    }

    private static void pullSelfPlayer(EntityPlayerSP player) {
        AxisAlignedBB pullBounds = player.getEntityBoundingBox().grow(EntityRift.PULL_RADIUS);
        List<EntityRift> rifts = player.world.getEntitiesWithinAABB(EntityRift.class, pullBounds);
        for (EntityRift rift : rifts) {
            if (!rift.wasUsed()) {
                double pullIntensity = rift.getPullIntensity();
                if (pullIntensity > 0.0 && player.isPlayerSleeping()) {
                    cancelSleep(player);
                }
                rift.pullEntity(pullIntensity, player);
            }
        }
    }

    private static void cancelSleep(EntityPlayerSP player) {
        NetHandlerPlayClient handler = player.connection;
        handler.sendPacket(new CPacketEntityAction(player, CPacketEntityAction.Action.STOP_SLEEPING));
    }

    private static void playAmbientSounds(EntityPlayer player) {
        if (player.posY < 62 && !player.world.canSeeSky(player.getPosition())) {
            return;
        }

        Random rand = player.world.rand;
        long worldTime = player.world.getTotalWorldTime();

        if (worldTime - lastAmbientSoundTime > AMBIENT_SOUND_INTERVAL && rand.nextInt(AMBIENT_SOUND_CHANCE) == 0) {
            ResourceLocation ambientSound = ModSounds.AMBIENT.getSoundName();

            float volume = rand.nextFloat() * 0.4F + 0.8F;
            float pitch = rand.nextFloat() * 0.6F + 0.7F;

            float x = (float) (player.posX + rand.nextFloat() - 0.5F);
            float y = (float) (player.posY + rand.nextFloat() - 0.5F);
            float z = (float) (player.posZ + rand.nextFloat() - 0.5F);

            ISound sound = new PositionedSoundRecord(ambientSound, SoundCategory.AMBIENT, volume, pitch, false, 0, ISound.AttenuationType.NONE, x, y, z);
            MC.getSoundHandler().playSound(sound);

            lastAmbientSoundTime = worldTime;
        }
    }

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        Entity entity = event.getEntity();
        if (FluidImmersionRenderer.immersedState.getBlock() != Blocks.AIR) {
            return;
        }
        if (Helper.isMidnightDimension(entity.world)) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setCanceled(true);
            event.setDensity(0.015F);
        } else if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPotionActive(ModEffects.STUNNED)) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setCanceled(true);
            event.setDensity(0.15F);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSetupFogColor(EntityViewRenderEvent.FogColors event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPotionActive(ModEffects.STUNNED)) {
            event.setRed(0.1F);
            event.setGreen(0.1F);
            event.setBlue(0.1F);
        }
    }

    private static void spawnAmbientParticles(EntityPlayer player) {
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
            MidnightParticles.SPORE.spawn(player.world, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }

    public static void onApplyRotations(EntityLivingBase entity) {
        boolean captured = RifterCapturedCapability.isCaptured(entity);
        if (captured) {
            entity.limbSwing = 0.0F;
            entity.limbSwingAmount = entity.prevLimbSwingAmount = 0.0F;

            EntityUtil.Stance stance = EntityUtil.getStance(entity);
            if (stance == EntityUtil.Stance.QUADRUPEDAL) {
                GlStateManager.translate(0.0F, entity.height, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            } else {
                GlStateManager.translate(0.0F, entity.width / 2.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, entity.height / 2.0F);
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (!isMusicSound()) {
            return;
        }

        if (MC.player != null && Helper.isMidnightDimension(MC.player.world)) {
            SoundEvent sound = getMusicSound(MC.player);
            if (sound == null || playingMusic != null) {
                event.setResultSound(null);
                return;
            }

            playingMusic = PositionedSoundRecord.getMusicRecord(sound);

            event.setResultSound(playingMusic);
        }
    }

    @SubscribeEvent
    public static void onSpawnEntity(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityRift && event.getWorld().isRemote) {
            MC.getSoundHandler().playSound(new IdleRiftSound((EntityRift) entity));
        }
    }

    @Nullable
    private static SoundEvent getMusicSound(EntityPlayer player) {
        Biome biome = player.world.getBiome(player.getPosition());
        if (biome == ModBiomes.CRYSTAL_SPIRES) {
            return ModSounds.MUSIC_CRYSTAL;
        }
        return ModSounds.MUSIC_GENERIC;
    }

    private static boolean isMusicSound() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return Arrays.stream(stackTrace).anyMatch(e -> e.getClassName().equals(MusicTicker.class.getName()));
    }
}
