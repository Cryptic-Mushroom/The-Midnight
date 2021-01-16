/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.core.util;

import midnight.common.Midnight;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

@FunctionalInterface
public interface WrappingRegistry<T> {
    void register(Identifier id, T t);

    default void register(String id, T t) {
        register(Midnight.id(id), t);
    }

    default void registerAll(Map<Identifier, ? extends T> ts) {
        for (Map.Entry<Identifier, ? extends T> t : ts.entrySet())
            register(t.getKey(), t.getValue());
    }

    static <T> WrappingRegistry<T> vanilla(Registry<T> reg) {
        return (id, t) -> Registry.register(reg, id, t);
    }

    static <T> WrappingRegistry<T> map(Map<Identifier, T> reg) {
        return reg::put;
    }
}
