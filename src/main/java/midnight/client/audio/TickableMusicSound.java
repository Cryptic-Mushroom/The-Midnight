/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.client.audio;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

/**
 * An extension of tickable sound that can be defined as repeating or not repeating. You also have the ability to set
 * the initial volume with an optional constructor parameter.
 *
 * @author Jonathing
 * @author KingPhygieBoo
 * @since 0.6.0
 */
public class TickableMusicSound extends TickableSound {
    private SoundEvent soundEvent;

    /**
     * This constructor registers the {@link TickableSound} and whethor or not we want it to repeat or not.
     *
     * @param soundEvent    The sound event to play.
     * @param soundCategory The category for the sound to play in.
     * @param repeat        If we want the music to repeat itself or not.
     */
    public TickableMusicSound(SoundEvent soundEvent, SoundCategory soundCategory, boolean repeat) {
        super(soundEvent, soundCategory);
        this.soundEvent = soundEvent;
        this.looping = repeat;
    }

    /**
     * Calls the main {@link TickableMusicSound} constructor but with an additional parameter that sets the initia.
     * volume.
     *
     * @param soundEvent    The sound event to play.
     * @param soundCategory The category for the sound to play in.
     * @param repeat        If we want the music to repeat itself or not.
     * @param volume        The initial volume for our sound to start with. Default is {@code 1.0F}.
     */
    public TickableMusicSound(SoundEvent soundEvent, SoundCategory soundCategory, boolean repeat, float volume) {
        this(soundEvent, soundCategory, repeat);
        this.volume = volume;
    }

    /**
     * Required override method for the {@link TickableSound} constructor. It usually does nothing, so we won't add to
     * it unless we need to later.
     */
    @Override
    public void tick() {

    }

    @Override
    public boolean isStopped() {
        return super.isStopped();
    }

    /**
     * Public method for setting the volume just in case we need to later.
     *
     * @param volume The new volume for our music sound to use. Default is {@code 1.0F}.
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Public method for getting the sound event. We might use this to determine what ambience effects to play, when to
     * play them, or for checking for a specific sound.
     *
     * @return The sound event used in the {@link TickableMusicSound} instance.
     */
    public SoundEvent getSoundEvent() {
        return soundEvent;
    }
}
