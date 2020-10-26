/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

import midnight.core.biome.colors.ColorMap;
import midnight.core.biome.colors.ColorProfile;
import midnight.core.biome.colors.IColorProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ColormapColorProvider implements IColorProvider {
    private final ColorMap map;
    private final float x;
    private final float y;
    private final IColorProvider fallback;

    public ColormapColorProvider(ResourceLocation loc, float x, float y, IColorProvider fallback) {
        this.x = x;
        this.y = y;
        this.fallback = fallback;

        map = ColorProfile.getColorMap(loc);
    }


    @Override
    public int getColor(IWorld world, BlockPos pos) {
        if (map.isLoaded()) {
            return map.get(x, y);
        }
        return fallback.getColor(world, pos);
    }

    @Override
    public void initForSeed(long seed) {
        fallback.initForSeed(seed);
    }
}
