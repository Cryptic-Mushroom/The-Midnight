/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight;

/**
 * Contains various important pieces of information about the instance of The Midnight.
 *
 * @author Shadew
 * @author Jonathing
 * @since 0.6.0
 */
public final class MnInfo {
    /**
     * The Mod ID of the Midnight, which is fixed to {@code midnight}.
     */
    @DynamicConstant("mod_id")
    public static final String MOD_ID = "midnight";

    /**
     * The Mod Name of the Midnight, which is fixed to 'The Midnight'.
     */
    @DynamicConstant("mod_name")
    public static final String NAME = "The Midnight";

    /**
     * This constant is true when the system property {@code midnight.iside} is {@code "true"}. This property is set in
     * all run configurations gradle.
     */
    public static final boolean IDE = checkIDE();

    /**
     * This constant is true when the system property {@code midnight.datagen} is {@code "true"}. This property is set
     * in the {@code data} run configration (for the {@code runData} task).
     */
    public static final boolean DATAGEN = checkDatagen();

    /**
     * This constant is true when the system property {@code midnight.istestserver} is {@code "true"}. This property is
     * set in the {@code testserver} run configration (for the {@code runTestServer} task).
     */
    public static final boolean TESTSERVER = checkTestServer();

    /**
     * This constant is true when the system property {@code midnight.disablemusic} is {@code "true"}.
     */
    public static final boolean MUSIC_DISABLED = forceDisableMidnightMusic();

    /**
     * The version of the Midnight (ex. {@code 0.6.0}), which is dynamically injected on build. Defaults to {@code
     * NOT.A.VERSION}.
     */
    @DynamicConstant("version")
    public static final String VERSION = "NOT.A.VERSION";

    /**
     * The version name of the Midnight (ex. 'The Midnight: Rewritten'), which is dynamically injected on build.
     * Defaults to 'Not A Version'.
     */
    @DynamicConstant("version_name")
    public static final String VERSION_NAME = "Not A Version";

    /**
     * The build time of the Midnight, which is dynamically injected on build. Defaults to {@code 2038-01-19T03:14:08Z},
     * referring to the <a href="https://en.wikipedia.org/wiki/Year_2038_problem">2038 Problem</a>.
     */
    @DynamicConstant("build_time")
    public static final String BUILD_DATE = "2038-01-19T03:14:08Z";

    private MnInfo() {
    }

    private static boolean checkIDE() {
        String p = System.getProperty("midnight.iside");
        return Boolean.parseBoolean(p);
    }

    private static boolean checkDatagen() {
        String p = System.getProperty("midnight.datagen");
        return Boolean.parseBoolean(p);
    }

    private static boolean checkTestServer() {
        String p = System.getProperty("minecraftdev.ci.istestserver");
        return Boolean.parseBoolean(p);
    }

    private static boolean forceDisableMidnightMusic() {
        String p = System.getProperty("midnight.disablemusic");
        return Boolean.parseBoolean(p);
    }
}
