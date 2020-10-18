/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.data;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Hacky tool to inject object holder values during fast data generation, where Forge is not present
 */
public final class ObjectHolderHacks {
    private static final Field MODIFIERS; // Field java.lang.Class -> modifiers

    static {
        try {
            MODIFIERS = Field.class.getDeclaredField("modifiers");
            MODIFIERS.setAccessible(true);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectHolderHacks() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends IForgeRegistryEntry<T>> void hackObjectHolder(Class<?> cls, Registry<T> registry, Class<?> typeClassRaw) {
        try {
            Class<T> typeClass = (Class<T>) typeClassRaw;

            String ns = "minecraft";
            if(cls.isAnnotationPresent(ObjectHolder.class)) {
                ObjectHolder oh = cls.getAnnotation(ObjectHolder.class);
                ns = oh.value();
            }

            for(Field field : cls.getFields()) {
                String path = field.getName().toLowerCase();
                if(field.isAnnotationPresent(ObjectHolder.class)) {
                    path = cls.getAnnotation(ObjectHolder.class).value();
                }

                String lns = ns;
                if(path.contains(":")) {
                    lns = path.split(":")[0];
                }

                Class<?> type = field.getType();
                if(Modifier.isStatic(field.getModifiers()) && typeClass.isAssignableFrom(type)) {
                    ResourceLocation id = loc(lns, path);
                    T obj = registry.getOrEmpty(id).orElse(null);
                    hack(field);
                    field.set(null, obj);
                }
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ResourceLocation loc(String ns, String path) {
        if(ns == null) ns = "minecraft";
        return new ResourceLocation(ns, path);
    }

    private static void hack(Field field) throws Exception {
        field.setAccessible(true);

        // Hacky reflection to change field modifiers
        int modifiers = (int) MODIFIERS.get(field);
        modifiers &= ~Modifier.FINAL;
        MODIFIERS.set(field, modifiers);
    }
}
