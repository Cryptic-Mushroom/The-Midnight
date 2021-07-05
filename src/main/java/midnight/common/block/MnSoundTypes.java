/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.misc.MnSoundEvents;
import midnight.core.util.MnObjects;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;

import java.util.function.Supplier;

/**
 * This class holds all the different sound types for various blocks, entities, the dimension, etc.
 *
 * @author Shadew
 * @author Jonathing
 * @since 0.6.0
 */
public abstract class MnSoundTypes {
    /*
     * Here are the parameters for adding new SoundTypes.
     * 1. Volume (default: 1.0)
     * 2. Pitch (default: 1.0)
     * 3. Break Sound
     * 4. Step Sound
     * 5. Place Sound
     * 6. Hit Sound
     * 7. Fall Sound
     */

    public static final SoundType MUD = sound(
        "mud",
        1.0, 1.0,
        MnSoundEvents.BLOCK_MUD_BREAK,
        MnSoundEvents.BLOCK_MUD_STEP,
        MnSoundEvents.BLOCK_MUD_PLACE,
        MnSoundEvents.BLOCK_MUD_HIT,
        MnSoundEvents.BLOCK_MUD_FALL
    );

    public static final SoundType PEAT = sound(
        "peat",
        1.0, 1.0,
        MnSoundEvents.BLOCK_PEAT_BREAK,
        MnSoundEvents.BLOCK_PEAT_STEP,
        MnSoundEvents.BLOCK_PEAT_PLACE,
        MnSoundEvents.BLOCK_PEAT_HIT,
        MnSoundEvents.BLOCK_PEAT_FALL
    );

    public static final SoundType CRYSTAL = sound(
        "crystal",
        1.0, 1.0,
        MnSoundEvents.BLOCK_CRYSTAL_BREAK,
        MnSoundEvents.BLOCK_CRYSTAL_STEP,
        MnSoundEvents.BLOCK_CRYSTAL_PLACE,
        MnSoundEvents.BLOCK_CRYSTAL_HIT,
        MnSoundEvents.BLOCK_CRYSTAL_FALL
    );

    public static final SoundType BRITTLE_METAL = sound(
        "brittle_metal",
        1.0, 1.5,
        SoundEvents.ANCIENT_DEBRIS_BREAK,
        SoundEvents.ANCIENT_DEBRIS_STEP,
        SoundEvents.ANCIENT_DEBRIS_PLACE,
        SoundEvents.ANCIENT_DEBRIS_HIT,
        SoundEvents.ANCIENT_DEBRIS_FALL
    );

    public static final SoundType JEWEL = sound(
        "jewel",
        1.0, 1.0,
        MnSoundEvents.BLOCK_JEWEL_BREAK,
        MnSoundEvents.BLOCK_JEWEL_STEP,
        MnSoundEvents.BLOCK_JEWEL_PLACE,
        MnSoundEvents.BLOCK_JEWEL_HIT,
        MnSoundEvents.BLOCK_JEWEL_FALL
    );

    private static SoundType sound(String id, double volume, double pitch, SoundEvent destroy, SoundEvent step, SoundEvent place, SoundEvent hit, SoundEvent fall) {
        SoundType type = new ForgeSoundType(
            (float) volume, (float) pitch,
            () -> destroy,
            () -> step,
            () -> place,
            () -> hit,
            () -> fall
        );
        MnObjects.addSoundType(id, type);
        return type;
    }

    private MnSoundTypes() {
    }
}
