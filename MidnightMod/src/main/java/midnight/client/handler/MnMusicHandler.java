/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.client.handler;

import midnight.MnInfo;
import midnight.client.MidnightClient;
import midnight.client.audio.MnMusicTicker;
import midnight.common.world.dimension.MnDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * This class is the handler for music events in The Midnight. I have based my code off of Bailey's music handler from
 * Blue Skies, but have given it more of my sense of code style and what I think the music should do (i.e. how it checks
 * for the biome, what music it should play and why, etc.).
 *
 * @author Jonathing
 * @author KingPhygieBoo
 * @since 0.6.0
 */
@Mod.EventBusSubscriber(modid = "midnight", value = Dist.CLIENT)
public class MnMusicHandler {
    /**
     * The current {@link Minecraft} instance.
     */
    private static Minecraft mc = Minecraft.getInstance();
    /**
     * The {@link MnMusicTicker} from the current {@link MidnightClient} instance. This ensures that we don't
     * accidentally create more than one instance of the {@link MnMusicTicker} which could lead to all sorts of
     * unpleasent problems.
     */
    private static MnMusicTicker musicTicker = MidnightClient.get().getMusicTicker();

    /**
     * This method runs on every client tick. It essentially makes sure that the music ticker runs on tick except for
     * when the game is paused. We may add to this later.
     *
     * @param event The {@link TickEvent.ClientTickEvent} event.
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        TickEvent.Phase phase = event.phase;
        TickEvent.Type type = event.type;

        if (phase == TickEvent.Phase.END) {
            if (type == TickEvent.Type.CLIENT) {
                if (!mc.isGamePaused()) {
                    musicTicker.tick();
                }
            }
        }
    }

    /**
     * This method runs whenever the game tries to take control of the music. Since we have our own music ticker, we're
     * telling the game no and that we'll be taking care of the music in our own dimension.
     *
     * @param event The {@link TickEvent.ClientTickEvent} event.
     */
    @SubscribeEvent
    public static void onMusicControl(PlaySoundEvent event) {
        ISound sound = event.getSound();
        SoundCategory category = sound.getCategory();

        if (category == SoundCategory.MUSIC) {
            if (mc.player != null && mc.player.world.getDimension() == MnDimensions.MIDNIGHT) {
                if (!sound.getSoundLocation().toString().contains(MnInfo.MODID) && (musicTicker.isPlayingTrueMusic() || !musicTicker.isPlayingTrueMusic())) {
                    event.setResultSound(null);
                }
            }
        }
    }
}
