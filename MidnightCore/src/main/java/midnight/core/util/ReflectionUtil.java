/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.core.util;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

public final class ReflectionUtil {
    private ReflectionUtil() {
    }

    public static Method findMethod(Class<?> cls, String obfName, String deobfName, Class<?>... params) {
        try {
            return ObfuscationReflectionHelper.findMethod(cls, obfName, params);
        } catch (Exception e) {
            try {
                return cls.getDeclaredMethod(deobfName, params);
            } catch (NoSuchMethodException exc) {
                throw new RuntimeException(exc);
            }
        }
    }
}
