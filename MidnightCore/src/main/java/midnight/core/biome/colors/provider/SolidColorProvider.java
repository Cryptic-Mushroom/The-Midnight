/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

import midnight.core.biome.colors.IColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SolidColorProvider implements IColorProvider {
    private final int color;

    public SolidColorProvider(int color) {
        this.color = color;
    }

    @Override
    public int getColor(IWorld world, BlockPos pos) {
        return color;
    }
}
