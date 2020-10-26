/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class AmbientSound extends TickableSound {
    private final ClientPlayerEntity player;

    public AmbientSound(ClientPlayerEntity player, SoundEvent soundEvent) {
        this(player, soundEvent, 0.7F, 1.0F);
    }

    public AmbientSound(ClientPlayerEntity player, SoundEvent soundEvent, float volume) {
        this(player, soundEvent, volume, 1.0F);
    }

    public AmbientSound(ClientPlayerEntity player, SoundEvent soundEvent, float volume, float pitch) {
        super(soundEvent, SoundCategory.AMBIENT);
        this.pitch = pitch;
        this.player = player;
        this.repeat = false;
        this.repeatDelay = 0;
        this.volume = volume;
        this.priority = true;
        this.global = true;
    }

    @Override
    public void tick() {
        if (!this.player.isAlive() || Minecraft.getInstance().isGamePaused()) {
            this.setDone();
        }
    }
}
