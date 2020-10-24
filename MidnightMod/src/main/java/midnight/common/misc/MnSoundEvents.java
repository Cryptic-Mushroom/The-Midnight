/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.common.misc;

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
    public static final SoundEvent BLOCK_MUD_PLACE = make("block.mud.place");
    public static final SoundEvent BLOCK_MUD_HIT = make("block.mud.hit");
    public static final SoundEvent BLOCK_MUD_FALL = make("block.mud.fall");

    public static final SoundEvent BLOCK_PEAT_BREAK = make("block.peat.break");
    public static final SoundEvent BLOCK_PEAT_STEP = make("block.peat.step");
    public static final SoundEvent BLOCK_PEAT_PLACE = make("block.peat.place");
    public static final SoundEvent BLOCK_PEAT_HIT = make("block.peat.hit");
    public static final SoundEvent BLOCK_PEAT_FALL = make("block.peat.fall");

    public static final SoundEvent BLOCK_CRYSTAL_BREAK = make("block.crystal.break");
    public static final SoundEvent BLOCK_CRYSTAL_STEP = make("block.crystal.step");
    public static final SoundEvent BLOCK_CRYSTAL_PLACE = make("block.crystal.place");
    public static final SoundEvent BLOCK_CRYSTAL_HIT = make("block.crystal.hit");
    public static final SoundEvent BLOCK_CRYSTAL_FALL = make("block.crystal.fall");

    public static final SoundEvent BLOCK_JEWEL_BREAK = make("block.jewel.break");
    public static final SoundEvent BLOCK_JEWEL_STEP = make("block.jewel.step");
    public static final SoundEvent BLOCK_JEWEL_PLACE = make("block.jewel.place");
    public static final SoundEvent BLOCK_JEWEL_HIT = make("block.jewel.hit");
    public static final SoundEvent BLOCK_JEWEL_FALL = make("block.jewel.fall");

    public static final SoundEvent ENTITY_GEODE_BREAK = make("entity.geode.break");
    public static final SoundEvent ENTITY_GEODE_THROW = make("entity.geode.throw");

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
