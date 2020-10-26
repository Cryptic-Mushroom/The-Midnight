/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.client.audio;

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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * A {@link Logger} specifically for the {@link MnMusicTicker}. I think this is necessary so we can keep track of
     * what the music system is doing. When the ticker is near completion, I will make all output go to the debug log
     * instead of the info log.
     */
    private static final Logger LOGGER = LogManager.getLogger("Midnight Music System");
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
                if (this.trueMusic != null && this.trueMusic.getVolume() <= 0) {
                    this.trueMusic = null;
                    LOGGER.info("Setting this.trueMusic to null since its volume is below 0...");
                }

                if (this.ambientMusic != null && this.ambientMusic.getVolume() <= 0) {
                    this.ambientMusic = null;
                    LOGGER.info("Setting this.ambientMusic to null since its volume is below 0...");
                }

                TrackType musicTrackType = this.getMusicTrack();
                TrackType ambientTrackType = this.getAmbientMusicTrack();

                if (this.ambientMusic != null) {
                    if (!this.mc.getSoundHandler().isPlaying(this.ambientMusic)) {
                        this.ambientMusic = null;
                        LOGGER.info("Setting this.ambientMusic to null since it's not playing or has finished...");
                        if (musicTrackType != null && this.trueMusic == null) {
                            this.timeUntilNextMusic = Math.min(MathHelper.nextInt(this.rand, musicTrackType.getMinDelay(), musicTrackType.getMaxDelay()), this.timeUntilNextMusic);
                            System.out.println("this.timeUntilNextMusic = " + this.timeUntilNextMusic);
                        }
                    }
                }

                if (this.trueMusic != null) {
                    if (!this.mc.getSoundHandler().isPlaying(this.trueMusic)) {
                        this.trueMusic = null;
                        LOGGER.info("Set this.trueMusic to null since it's not playing or has finished...");
                        if (this.ambientMusic == null) {
                            this.timeUntilNextMusic = Math.min(MathHelper.nextInt(this.rand, ambientTrackType.getMinDelay(), ambientTrackType.getMaxDelay()), this.timeUntilNextMusic);
                            LOGGER.info(String.format("Approximate time until next track is played: %d seconds.", this.timeUntilNextMusic / 20));
                        }
                    }
                }

                if (musicTrackType != null) {
                    this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, musicTrackType.getMaxDelay());
                } else {
                    this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, ambientTrackType.getMaxDelay());
                }

//                // Uncomment this if you need to keep track of the ticks counted.
//                if (this.timeUntilNextMusic % 20 == 0) {
//                    System.out.println("this.timeUntilNextMusic = " + this.timeUntilNextMusic);
//                }

                if (musicTrackType != null) {
                    if (this.trueMusic == null && this.ambientMusic == null && this.timeUntilNextMusic-- <= 0) {
                        this.playMusic(musicTrackType);
                        LOGGER.info(String.format("Now playing music track: %s", musicTrackType.toString()));
                    }
                } else {
                    if (this.trueMusic == null && this.ambientMusic == null && this.timeUntilNextMusic-- <= 0) {
                        this.playAmbientMusic(ambientTrackType);
                        LOGGER.info(String.format("Now playing ambient-like music track: %s", ambientTrackType.toString()));
                    }
                }
            }
        } else {
            this.stopTrueMusic();
            this.stopAmbientMusic();
        }
    }

    /**
     * Public method that returns if the {@link MnMusicTicker} is currently playing music at all.
     *
     * @return True if the {@link MnMusicTicker} is playing any music.
     */
    public boolean isPlayingMusic() {
        return isPlayingTrueMusic() || isPlayingAmbientMusic() || isPlayingMusicDisk();
    }

    /**
     * Public method that returns if the {@link MnMusicTicker} is currently playing true music.
     *
     * @return True if the {@link MnMusicTicker} is playing true music.
     */
    public boolean isPlayingTrueMusic() {
        return this.trueMusic != null;
    }

    /**
     * Public method that returns if the {@link MnMusicTicker} is currently playing ambient-like music.
     *
     * @return True if the {@link MnMusicTicker} is playing ambient-like music.
     */
    public boolean isPlayingAmbientMusic() {
        return this.ambientMusic != null;
    }

    /**
     * Public method that returns if the {@link MnMusicTicker} is currently playing a music disk. (UNUSED FOR NOW)
     *
     * @return True if the {@link MnMusicTicker} is playing a music disk.
     */
    public boolean isPlayingMusicDisk() {
        return this.playingRecord != null;
    }

    /**
     * Public method that returns the {@link SoundEvent} the {@link MnMusicTicker} is currently playing.
     *
     * @return The {@link SoundEvent} the {@link MnMusicTicker} is currently playing.
     */
    @Nullable
    public SoundEvent getCurrentMusic() {
        if (this.trueMusic == null && this.ambientMusic == null) return null;
        return this.trueMusic != null ? ((TickableMusicSound) this.trueMusic).getSoundEvent() : ((TickableMusicSound) this.ambientMusic).getSoundEvent();
    }

    /**
     * Protected method that gets the music track based on what biome the {@link ClientPlayerEntity} is currently in.
     *
     * @return The {@link TrackType} of the music track found in the biome from this method.
     */
    @Nullable
    protected MnMusicTicker.TrackType getMusicTrack() {
        if (this.mc.player == null) return null; // null check in case something goes horrible wrong.
        World world = this.mc.player.world;
        RegistryKey<Biome> biome = MnBiomes.getKeyFromBiome(world, world.getBiome(this.mc.player.getBlockPos()));
        if (biome == null) return null;
        else if (biome == MnBiomes.VIGILANT_FOREST) return TrackType.DARK_WILLOW;
        else if (biome == MnBiomes.DECEITFUL_BOG) return TrackType.CRYSTALS;
        else return null;
    }

    /**
     * Protected method that gets a random ambient music track based off of a random integer.
     *
     * @return The {@link TrackType} of the random ambient music track.
     */
    protected MnMusicTicker.TrackType getAmbientMusicTrack() {
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

    /**
     * Stops music, if any, and resets the counter.
     */
    public void stopTrueMusic() {
        if (this.trueMusic != null) {
            this.timeUntilNextMusic = 60;
        }
    }

    /**
     * Stops ambient-like music, if any, and resets the counter.
     */
    public void stopAmbientMusic() {
        if (this.ambientMusic != null) {
            this.timeUntilNextMusic = 60;
        }
    }

    /**
     * An enum containing a list of tracks, each with a {@link SoundEvent}, {@code minDelay}, and {@code maxDelay}
     */
    protected enum TrackType {
        // True music
        CRYSTALS(MnSoundEvents.MUSIC_CRYSTALS, 1200, 1500),
        DARK_WILLOW(MnSoundEvents.MUSIC_DARKWILLOW, 1200, 1500),

        // Ambient-like music
        STEGANO(MnSoundEvents.MUSIC_AMBIENT_STEGANO, 1200, 1500),
        ULTRAVIOLET(MnSoundEvents.MUSIC_AMBIENT_ULTRAVIOLET, 1200, 1500);

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

        @Override
        public String toString() {
            return musicLocation.getName().toString();
        }
    }
}
