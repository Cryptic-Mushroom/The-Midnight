/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.api.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

/**
 * Blocks implementing this interface indicate that Night Grass should adapt to a certain color when this block is
 * placed nearby, such as fingered grass causing night grass to turn blue.
 *
 * @author Shadew
 * @since 0.6.0
 */
@FunctionalInterface
public interface INightGrassColorModifying {
    /**
     * Get a color that nearby night grass should adapt to. Grass adapts to the nearest grass modifier block's color.
     *
     * @param world The world where this block is placed
     * @param pos   The location of the block
     * @param state The block state
     * @return The color that nearby grass should adapt to.
     */
    int getColorModifier(IWorld world, BlockPos pos, BlockState state);
}
