/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.core.mixin;

import midnight.core.dimension.DimensionUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.Dimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.LinkedHashSet;

/**
 * This mixin class shadows in the {@link LinkedHashSet} containing the dimensions,
 * then and we inject our dimensions into it.
 *
 * @author Shadew
 */
@Mixin(Dimension.class)
public abstract class DimensionMixin {
    /**
     * The {@link LinkedHashSet} containing every dimension registry key.
     */
    @Shadow
    @Final
    private static LinkedHashSet<RegistryKey<Dimension>> BASE_DIMENSIONS;

    static {
        BASE_DIMENSIONS.addAll(DimensionUtil.DIMENSIONS);
    }
}
