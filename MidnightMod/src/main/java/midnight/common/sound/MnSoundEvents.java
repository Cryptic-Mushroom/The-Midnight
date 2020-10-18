/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.sound;

import midnight.common.Midnight;
import midnight.core.util.IRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers and stores the list of Midnight sound events.
 *
 * @author Shadew
 * @version 0.6.0
 * @since 0.6.0
 */
public final class MnSoundEvents {
    private static final List<SoundEvent> SOUNDS = new ArrayList<>();

    public static final SoundEvent BLOCK_MUD_BREAK = make("block.mud.break");
    public static final SoundEvent BLOCK_MUD_STEP = make("block.mud.step");

    public static void registerSoundEvents(IRegistry<SoundEvent> registry) {
        SOUNDS.forEach(registry::register);
    }

    private MnSoundEvents() {
    }

    /**
     * Grabs the sounds from a group in sounds.json to make into a SoundEvent.
     *
     * @param type The sound group from sounds.json to use.
     * @return The SoundEvent to be used in game.
     */
    private static SoundEvent make(String type) {
        ResourceLocation id = Midnight.resLoc(type);
        SoundEvent event = new SoundEvent(id).setRegistryName(id);
        SOUNDS.add(event);
        return event;
    }
}
