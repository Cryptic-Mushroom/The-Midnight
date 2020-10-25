/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.client.audio.music;

import midnight.client.handler.MnMusicHandler;
import midnight.common.misc.MnSoundEvents;
import midnight.common.world.biome.MnBiomes;
import midnight.common.world.dimension.MnDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * This class is the music ticker for. I have based my code off of Bailey's music ticker and from Blue Skies, but have
 * given it more of my sense of code style and what I think the music should do.
 *
 * @author Jonathing
 * @author KingPhygieBoo
 * @since 0.6.0
 */
@OnlyIn(Dist.CLIENT)
public class MnMusicTicker {
    /**
     * A {@link Random} instance just in case we might need it later.
     */
    private final Random rand = new Random();
    /**
     * The current {@link Minecraft} instance given by the constructor.
     */
    private final Minecraft mc;
    /**
     * {@link ISound} variables that are used to feed into {@link Minecraft}'s {@code getSoundHandler().play()} method.
     */
    public ISound ambientMusic, trueMusic, playingRecord;
    private int timeUntilNextMusic = 0;

    /**
     * This constructor is used to feed the current instance of {@link Minecraft} into the {@link MnMusicTicker}
     * instance.
     *
     * @param mc The current {@link Minecraft} instance given.
     */
    public MnMusicTicker(Minecraft mc) {
        this.mc = mc;
    }

    /**
     * This method is called on every client tick by the {@link MnMusicHandler} event handler.
     */
    public void tick() {
        if (this.mc.player != null && !this.mc.getSoundHandler().isPlaying(this.playingRecord)) {
            DimensionType playerDimension = this.mc.player.world.getDimension();

            if (playerDimension.equals(MnDimensions.MIDNIGHT)) {
                System.out.println("IN THE MIDNIGHT!");
                // TODO actually program the music ticker. All I've done so far is set up the base.
            }
        }
    }

    public boolean playingMusic() {
        return this.trueMusic != null;
    }

    public boolean playingAmbientMusic() {
        return this.ambientMusic != null;
    }

    public boolean playingRecord() {
        return this.playingRecord != null;
    }

    /**
     * This method gets the music track based on what biome the {@link ClientPlayerEntity} is currently in.
     *
     * @return The {@link TrackType} of the music track found in the biome from this method.
     */
    @Nullable
    public MnMusicTicker.TrackType getMusicTrack() {
        World world = this.mc.player.world;
        RegistryKey<Biome> biome = MnBiomes.getKeyFromBiome(world, world.getBiome(this.mc.player.getBlockPos()));
        if (biome == null) return null;
        else if (biome == MnBiomes.VIGILANT_FOREST) return TrackType.DARK_WILLOW;
        else if (biome == MnBiomes.DECEITFUL_BOG) return TrackType.CRYSTALS;
        else return null;
    }

    /**
     * Gets a random ambient music track based off of a random integer.
     *
     * @return The {@link TrackType} of the random ambient music track.
     */
    public MnMusicTicker.TrackType getAmbientMusicTrack() {
        int num = this.rand.nextInt(2);

        // Don't replace this switch statement with an if statement.
        // This is here for when we add more ambient music tracks.
        switch (num) {
            case 0:
                return TrackType.STEGANO;
            default:
                return TrackType.ULTRAVIOLET;
        }
    }

    /**
     * Play the music track into {@link Minecraft}'s music sound category and give the variable {@code trueMusic} what
     * it is currently playing.
     *
     * @param requestedTrackType The {@link TrackType} of the music track to play.
     */
    public void playMusic(TrackType requestedTrackType) {
        this.trueMusic = new TickableMusicSound(requestedTrackType.getMusicLocation(), SoundCategory.MUSIC, false);

        this.mc.getSoundHandler().play(this.trueMusic);
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }

    /**
     * Play the music track into {@link Minecraft}'s music sound category and give the variable {@code ambientMusic}
     * what it is currently playing.
     *
     * @param requestedTrackType The {@link TrackType} of the music track to play.
     */
    public void playAmbientMusic(TrackType requestedTrackType) {
        this.ambientMusic = new TickableMusicSound(requestedTrackType.getMusicLocation(), SoundCategory.MUSIC, false);

        this.mc.getSoundHandler().play(this.ambientMusic);
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }

    public void stopMusic() {
        if (this.trueMusic != null) {
            this.timeUntilNextMusic = 0;
        }
    }

    public void stopAmbientMusic() {
        if (this.ambientMusic != null) {
            this.timeUntilNextMusic = 0;
        }
    }

    /**
     * An enum containing a list of tracks, each with a {@link SoundEvent}, {@code minDelay}, and {@code maxDelay}
     */
    public enum TrackType {
        // True music
        CRYSTALS(MnSoundEvents.MUSIC_CRYSTALS, 0, 0),
        DARK_WILLOW(MnSoundEvents.MUSIC_DARKWILLOW, 0, 0),

        // Ambient-like music
        STEGANO(MnSoundEvents.MUSIC_AMBIENT_STEGANO, 0, 0),
        ULTRAVIOLET(MnSoundEvents.MUSIC_AMBIENT_ULTRAVIOLET, 0, 0);

        private final SoundEvent musicLocation;
        private final int minDelay;
        private final int maxDelay;

        TrackType(SoundEvent musicLocation, int minDelay, int maxDelay) {
            this.musicLocation = musicLocation;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
        }

        public SoundEvent getMusicLocation() {
            return this.musicLocation;
        }

        public int getMinDelay() {
            return minDelay;
        }

        public int getMaxDelay() {
            return maxDelay;
        }
    }
}
