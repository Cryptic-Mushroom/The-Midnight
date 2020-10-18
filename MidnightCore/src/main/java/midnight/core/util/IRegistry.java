/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.core.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

@FunctionalInterface
public interface IRegistry<T extends IForgeRegistryEntry<T>> {
    void register(T t);
    default void register(String id, T t) {
        t.setRegistryName(new ResourceLocation(id));
        register(t);
    }
    default void registerAll(T... ts) {
        for(T t : ts) register(t);
    }

    static <T extends IForgeRegistryEntry<T>> IRegistry<T> forge(IForgeRegistry<T> reg) {
        return reg::register;
    }

    static <T extends IForgeRegistryEntry<T>> IRegistry<T> vanilla(Registry<T> reg) {
        return t -> Registry.register(reg, Objects.requireNonNull(t.getRegistryName()), t);
    }
}
