package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.sound.MidnightCaveSound;
import com.mushroom.midnight.client.sound.MidnightIdleSound;
import com.mushroom.midnight.common.entity.util.ToggleAnimation;
import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class IdleSoundController {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final ISound IDLE_SOUND = new MidnightIdleSound();
    private static final ISound CAVE_IDLE_SOUND = new MidnightCaveSound();

    public static final ToggleAnimation CAVE_ANIMATION = new ToggleAnimation(20);

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!MC.isGamePaused()) {
            EntityPlayer player = MC.player;
            if (player == null || event.phase == TickEvent.Phase.START) {
                return;
            }

            if (Helper.isMidnightDimension(player.world)) {
                CAVE_ANIMATION.set(player.posY < 62 && !player.world.canSeeSky(player.getPosition()));
                CAVE_ANIMATION.update();

                retainIdleSound(IDLE_SOUND);
                retainIdleSound(CAVE_IDLE_SOUND);
            }
        }
    }

    private static void retainIdleSound(ISound sound) {
        SoundHandler soundHandler = MC.getSoundHandler();
        if (!soundHandler.isSoundPlaying(sound)) {
            // Fix very odd bug where playSound would complain that the sound is already playing
            soundHandler.stopSound(sound);
            try {
                soundHandler.playSound(sound);
            } catch (IllegalArgumentException e) {
                // Ignore SoundHandler complaints
            }
        }
    }
}
