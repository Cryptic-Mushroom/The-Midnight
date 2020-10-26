/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.provider;

import midnight.core.biome.colors.ColorProfileManager;
import midnight.core.biome.colors.IColorProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class ReferencedColorProvider implements IColorProvider {
    private final IColorProvider colorProvider;

    public ReferencedColorProvider(ResourceLocation loc, ColorProfileManager mgr) {
        this.colorProvider = mgr.load(loc).getProvider();
    }

    @Override
    public int getColor(@Nullable IWorld world, BlockPos pos) {
        return colorProvider.getColor(world, pos);
    }

    @Override
    public void initForSeed(long seed) {
        colorProvider.initForSeed(seed);
    }
}
