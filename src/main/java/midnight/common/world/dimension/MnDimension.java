/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

/**
 * This class registers and stores the list of Midnight dimensions. Go figure.
 *
 * @author Shadew
 * @since 0.6.0
 */
public enum MnDimension {
    THE_MIDNIGHT(TheMidnightDimension.ID, TheMidnightDimension.KEY, TheMidnightDimension.TYPE);
    // TODO funny for 0.7.0 ;)

    private final ResourceLocation id;
    private final RegistryKey<World> key;
    private final DimensionType type;

    MnDimension(ResourceLocation id, RegistryKey<World> key, DimensionType type) {
        this.id = id;
        this.key = key;
        this.type = type;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RegistryKey<World> getKey() {
        return this.key;
    }

    public DimensionType getType() {
        return this.type;
    }
}
