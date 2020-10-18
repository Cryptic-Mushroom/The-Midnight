/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.api.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes annotated with {@link MidnightPlugin} specify a generic plugin for the Midnight. Plugins are intended to
 * interfere with the Midnight through the Midnight API, and are supposed to be only loaded by the Midnight itself. Do
 * not try to load your plugins yourself as it might result in random {@link NoClassDefFoundError}s and {@link
 * ClassNotFoundException}s when the Midnight is not installed. Do not make a plugin only to subscribe to the Midnight
 * event bus. Use {@link MidnightEventSubscriber} instead.
 *
 * @author Shadew
 * @since 0.6.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MidnightPlugin {
    /**
     * The {@link Side} this plugin is exclusive to.
     */
    Side side() default Side.COMMON;
}
