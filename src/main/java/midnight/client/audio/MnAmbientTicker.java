/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.client.audio;

import midnight.MidnightMod;
import midnight.common.misc.MnSoundEvents;
import midnight.common.world.dimension.MnDimensions;
import midnight.core.dimension.DimensionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Random;

/**
 * This class is the ambiance ticker for The Midnight
 */
@OnlyIn(Dist.CLIENT)
public class MnAmbientTicker {
    private static final Marker MARKER = MarkerManager.getMarker("Ambient Ticker");
    private final Random rand = new Random();
    private final Minecraft mc;
    private int delay = 140;

    /**
     * This constructor is used to feed the current instance of {@link Minecraft} into the {@link MnAmbientTicker}
     * instance.
     *
     * @param mc The current {@link Minecraft} instance given.
     */
    public MnAmbientTicker(Minecraft mc) {
        this.mc = mc;
    }

    public void tick(MnMusicTicker musicTicker) {
        if (this.mc.player != null) {
            if (DimensionUtil.isInDimension(this.mc.player.level, MnDimensions.THE_MIDNIGHT) && this.mc.player.isAlive() && this.delay <= 0) {
                float ambientRandom = this.mc.player.level.random.nextFloat();

                if (ambientRandom < (musicTicker.isPlayingTrueMusic() ? 0.001F : 0.01F)) {
                    this.delay = 140;
                    float volume = musicTicker.isPlayingTrueMusic() ? 0.55F : 0.85F;
                    float pitch = (this.mc.player.level.random.nextFloat() - this.mc.player.level.random.nextFloat()) * 0.2F + 1.0F;
                    MidnightMod.LOGGER.debug(MARKER, "Playing a short ambient sound with a pitch of " + pitch + " and a volume of " + volume + ".");
                    this.playAmbience(MnSoundEvents.AMBIENT_MIDNIGHT_SHORT, volume, pitch);
                }
            } else {
                this.delay--;
            }
        }
    }

    public void playAmbience(SoundEvent soundEvent) {
        this.playAmbience(soundEvent, 0.7F);
    }

    public void playAmbience(SoundEvent soundEvent, float volume) {
        this.playAmbience(soundEvent, 0.7F, 1.0F);
    }

    public void playAmbience(SoundEvent soundEvent, float volume, float pitch) {
        this.mc.getSoundManager().play(new AmbientSound(this.mc.player, soundEvent, volume, pitch));
    }
}
