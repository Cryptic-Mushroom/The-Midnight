/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 2 - 6
 */

package midnight.common.block;

import midnight.common.misc.MnSoundEvents;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

/**
 * This class holds all the different sound types for various blocks, entities, the dimension, etc.
 *
 * @author Shadew
 * @author Jonathing
 * @since 0.6.0
 */
public abstract class MnBlockSoundGroups {
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

    public static final BlockSoundGroup MUD = sound(
        "mud",
        1.0, 1.0,
        MnSoundEvents.BLOCK_MUD_BREAK,
        MnSoundEvents.BLOCK_MUD_STEP,
        MnSoundEvents.BLOCK_MUD_PLACE,
        MnSoundEvents.BLOCK_MUD_HIT,
        MnSoundEvents.BLOCK_MUD_FALL
    );

    public static final BlockSoundGroup PEAT = sound(
        "peat",
        1.0, 1.0,
        MnSoundEvents.BLOCK_PEAT_BREAK,
        MnSoundEvents.BLOCK_PEAT_STEP,
        MnSoundEvents.BLOCK_PEAT_PLACE,
        MnSoundEvents.BLOCK_PEAT_HIT,
        MnSoundEvents.BLOCK_PEAT_FALL
    );

    public static final BlockSoundGroup CRYSTAL = sound(
        "crystal",
        1.0, 1.0,
        MnSoundEvents.BLOCK_CRYSTAL_BREAK,
        MnSoundEvents.BLOCK_CRYSTAL_STEP,
        MnSoundEvents.BLOCK_CRYSTAL_PLACE,
        MnSoundEvents.BLOCK_CRYSTAL_HIT,
        MnSoundEvents.BLOCK_CRYSTAL_FALL
    );

    public static final BlockSoundGroup BRITTLE_METAL = sound(
        "brittle_metal",
        1.0, 1.5,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_STEP,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_PLACE,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_HIT,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_FALL
    );

    public static final BlockSoundGroup JEWEL = sound(
        "jewel",
        1.0, 1.0,
        MnSoundEvents.BLOCK_JEWEL_BREAK,
        MnSoundEvents.BLOCK_JEWEL_STEP,
        MnSoundEvents.BLOCK_JEWEL_PLACE,
        MnSoundEvents.BLOCK_JEWEL_HIT,
        MnSoundEvents.BLOCK_JEWEL_FALL
    );

    private static BlockSoundGroup sound(String id, double volume, double pitch, SoundEvent destroy, SoundEvent step, SoundEvent place, SoundEvent hit, SoundEvent fall) {
        BlockSoundGroup type = new BlockSoundGroup(
            (float) volume, (float) pitch,
            destroy,
            step,
            place,
            hit,
            fall
        );
        return type;
    }

    private MnBlockSoundGroups() {
    }
}
