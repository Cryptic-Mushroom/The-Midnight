/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.core.util;

import midnight.api.IMidnight;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Map;

/**
 * Wrapper around a certain registry, used in both Fabric and Forge to make parity easier.
 */
@FunctionalInterface
public interface IRegistry<T> {
    void register(ResourceLocation id, T t);

    default void register(String id, T t) {
        register(IMidnight.id(id), t);
    }

    default void registerAll(Map<ResourceLocation, ? extends T> ts) {
        for (Map.Entry<ResourceLocation, ? extends T> t : ts.entrySet())
            register(t.getKey(), t.getValue());
    }

    static <T extends IForgeRegistryEntry<T>> IRegistry<T> forge(IForgeRegistry<T> reg) {
        return (id, t) -> {
            if (t.getRegistryName() == null)
                t.setRegistryName(id);
            reg.register(t);
        };
    }

    static <T> IRegistry<T> vanilla(Registry<T> reg) {
        return (id, t) -> Registry.register(reg, id, t);
    }

    static <T> IRegistry<T> map(Map<ResourceLocation, T> reg) {
        return reg::put;
    }
}
