/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.api;

/**
 * Thrown in {@link IMidnight#get()} when the Midnight is being accessed while not installed. This is scientifically the
 * best exception in the game. - Jonathing
 *
 * @author Shadew
 * @since 0.6.0
 */
public class MidnightNotInstalledException extends RuntimeException {
    public MidnightNotInstalledException(String message) {
        super(message);
    }
}
