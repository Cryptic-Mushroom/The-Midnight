/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 18
 */

package midnight.common.world.dimension;

import midnight.common.Midnight;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullLazy;

/**
 * This enum contains all of our dimensions along with getters for their ids,
 * registry keys, and dimension types.
 *
 * @author Jonathing
 * @since 0.6.0
 */
public enum MnDimension {
    THE_MIDNIGHT("the_midnight", () -> TheMidnightDimension.TYPE);
    // TODO funny for 0.7.0 ;)

    private final ResourceLocation id;
    private final NonNullLazy<RegistryKey<World>> key;
    private final NonNullLazy<RegistryKey<Dimension>> dimKey;
    private final NonNullLazy<DimensionType> type;

    MnDimension(String name, NonNullLazy<DimensionType> type) {
        this.id = Midnight.id(name);
        this.key = () -> RegistryKey.create(Registry.DIMENSION_REGISTRY, this.id);
        this.dimKey = () -> RegistryKey.create(Registry.LEVEL_STEM_REGISTRY, this.id);
        this.type = type;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RegistryKey<World> getKey() {
        return this.key.get();
    }

    public RegistryKey<Dimension> getDimKey() {
        return this.dimKey.get();
    }

    public DimensionType getType() {
        return this.type.get();
    }
}
