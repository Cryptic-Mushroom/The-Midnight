/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.IBiomeMagnifier;

import java.util.OptionalLong;

public class MnDimensionType extends DimensionType {
    public MnDimensionType(OptionalLong fixedTime, boolean skylight, boolean ceiling, boolean ultrawarm, boolean natural, double coordsScale, boolean dragon, boolean piglins, boolean bed, boolean anchor, boolean raid, int height, IBiomeMagnifier magnifier, ResourceLocation infiniburn, ResourceLocation sky, float lighting) {
        super(fixedTime, skylight, ceiling, ultrawarm, natural, coordsScale, dragon, piglins, bed, anchor, raid, height, magnifier, infiniburn, sky, lighting);
    }
}
