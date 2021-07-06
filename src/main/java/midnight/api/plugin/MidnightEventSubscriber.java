/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.api.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Subscribes a class to the Midnight event bus when the Midnight is being loaded. Make sure you don't load this class
 * yourself as it might result in random {@link NoClassDefFoundError}s and {@link ClassNotFoundException}s when the
 * Midnight is not installed.
 *
 * @author Shadew
 * @since 0.6.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MidnightEventSubscriber {
    /**
     * @return The {@link Side} that this event handler is exclusive to.
     */
    Side side() default Side.COMMON;
}
