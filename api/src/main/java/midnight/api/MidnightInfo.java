/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 26
 */

package midnight.api;

/**
 * Accessor for the Midnight build info
 */
public interface MidnightInfo {
    /**
     * The Mod ID of the Midnight
     */
    String modid();

    /**
     * The Mod Name of the Midnight
     */
    String name();

    /**
     * The version of the Midnight
     */
    String version();

    /**
     * The version name of the Midnight
     */
    String versionName();

    /**
     * The build date of this Midnight build, in RFC-3339 format
     */
    String buildDate();
}
