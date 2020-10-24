/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 24
 */

package midnight.common.block;

import midnight.common.misc.MnSoundEvents;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvents;

/**
 * This class holds all the different sound types for various blocks, entities, the dimension, etc.
 *
 * @author Shadew
 * @author Jonathing
 * @version 0.6.0
 * @since 0.6.0
 */
public abstract class MnSoundTypes {
    /*
     * Here are the parameters for adding new SoundTypes.
     * 1. Volume (default: 1.0F)
     * 2. Pitch (default: 1.0F)
     * 3. Break Sound
     * 4. Step Sound
     * 5. Place Sound
     * 6. Hit Sound
     * 7. Fall Sound
     */

    public static final SoundType MUD = new SoundType(
        1.0F, 1.0F,
        MnSoundEvents.BLOCK_MUD_BREAK,
        MnSoundEvents.BLOCK_MUD_STEP,
        MnSoundEvents.BLOCK_MUD_PLACE,
        MnSoundEvents.BLOCK_MUD_HIT,
        MnSoundEvents.BLOCK_MUD_FALL
    );

    public static final SoundType PEAT = new SoundType(
        1.0F, 1.0F,
        MnSoundEvents.BLOCK_PEAT_BREAK,
        MnSoundEvents.BLOCK_PEAT_STEP,
        MnSoundEvents.BLOCK_PEAT_PLACE,
        MnSoundEvents.BLOCK_PEAT_HIT,
        MnSoundEvents.BLOCK_PEAT_FALL
    );

    public static final SoundType CRYSTAL = new SoundType(
        1.0F, 1.0F,
        MnSoundEvents.BLOCK_CRYSTAL_BREAK,
        MnSoundEvents.BLOCK_CRYSTAL_STEP,
        MnSoundEvents.BLOCK_CRYSTAL_PLACE,
        MnSoundEvents.BLOCK_CRYSTAL_HIT,
        MnSoundEvents.BLOCK_CRYSTAL_FALL
    );

    public static final SoundType BRITTLE_METAL = new SoundType(
        1.0F, 1.5F,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_STEP,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_PLACE,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_HIT,
        SoundEvents.BLOCK_ANCIENT_DEBRIS_FALL
    );

    public static final SoundType JEWEL = new SoundType(
        1.0F, 1.0F,
        MnSoundEvents.BLOCK_JEWEL_BREAK,
        MnSoundEvents.BLOCK_JEWEL_STEP,
        MnSoundEvents.BLOCK_JEWEL_PLACE,
        MnSoundEvents.BLOCK_JEWEL_HIT,
        MnSoundEvents.BLOCK_JEWEL_FALL
    );

    private MnSoundTypes() {
    }
}
