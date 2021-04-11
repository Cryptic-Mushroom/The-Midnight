/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.api;

/**
 * Holds information about the Midnight build.
 *
 * @author Shadew
 * @since 0.6.0
 */
public interface IMidnightInfo {
    /**
     * Returns the mod-id/resources namespace of the Midnight, which is {@code midnight}.
     */
    String modid();

    /**
     * Returns the mod name of the Midnight, which is 'The Midnight'.
     */
    String name();

    /**
     * Returns the build version number of the Midnight, in the following format:<br>
     * <code><i>major</i>.<i>minor</i>.<i>patch</i><i>[</i>-<i>hotfix]</i></code><br>
     */
    String version();

    /**
     * Returns the build version name of the Midnight.
     */
    String versionName();

    /**
     * Returns the build date of the available Midnight build, in RFC-3339 format.
     */
    String buildDate();

    /**
     * Returns whether the Midnight is running from the IDE or not. When this returns true, the Midnight provides
     * additinonal tools and features intended for debugging. To enable these features, set the {@code midnight.iside}
     * system property to {@code true}.
     */
    boolean ide();

    /**
     * Returns whether the Midnight is running as a data generator.
     */
    boolean data();

    /**
     * Returns whether the Midnight is running from GitHub Actions.
     */
    boolean testServer();

    /**
     * Returns whether the Midnight should disable its music system.
     */
    boolean musicDisabled();
}
