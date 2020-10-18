/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.core.util;

import midnight.MidnightInfo;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * General utility class for the Midnight.
 *
 * @author Shadew
 * @version 0.6.0
 * @since 0.6.0
 */
public final class MnUtil {
    private MnUtil() {
    }

    /**
     * Returns null and deliberately violates its contract of returning a non-null value. Used for fields that get
     * injected by ASM.
     */
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T injected() {
        return null;
    }

    /**
     * Gets an instance specifically for data generation, or another instance if we're not running the data generator.
     */
    public static <T> T callForDatagen(Supplier<Callable<T>> datagen, Supplier<Callable<T>> production) {
        try {
            if (MidnightInfo.DATAGEN) {
                return datagen.get().call();
            } else {
                return production.get().call();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
