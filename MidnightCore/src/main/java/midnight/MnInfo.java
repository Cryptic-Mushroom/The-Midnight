/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight;

import midnight.api.IMidnightInfo;

/**
 * Contains various important pieces of information about the instance of The Midnight.
 *
 * @author Shadew
 * @author Jonathing
 * @since 0.6.0
 */
public final class MnInfo implements IMidnightInfo {
    public static final MnInfo INSTANCE = new MnInfo();

    /**
     * The Mod ID of the Midnight, which is fixed to {@code midnight}.
     */
    public static final String MODID = "midnight";

    /**
     * The Mod Name of the Midnight, which is fixed to 'The Midnight'.
     */
    public static final String NAME = "The Midnight";

    /**
     * This constant is true when the system property {@code midnight.iside} is {@code "true"}. This property is set in
     * all run configurations gradle.
     */
    public static final boolean IDE = isRunningFromIDE();

    /**
     * This constant is true when the system property {@code midnight.datagen} is {@code "true"}. This property is set
     * in the {@code data} run configration (for the {@code runData} task).
     */
    public static final boolean DATAGEN = isRunningDatagen();

    /**
     * This constant is true when the system property {@code midnight.istestserver} is {@code "true"}. This property is
     * set in the {@code testserver} run configration (for the {@code runTestServer} task).
     */
    public static final boolean TESTSERVER = isRunningTestServer();

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
     * The expected SHA256 fingerprint of the Midnight, for verification of the signature. This is dynamically injected
     * on build. Defaults to an empty string.
     */
    @DynamicConstant("expected_sha256")
    public static final String EXPECTED_SHA256 = "";

    /**
     * The build time of the Midnight, which is dynamically injected on build. Defaults to {@code 2038-01-19T03:14:08Z},
     * referring to the <a href="https://en.wikipedia.org/wiki/Year_2038_problem">2038 Problem</a>.
     */
    @DynamicConstant("build_time")
    public static final String BUILD_DATE = "2038-01-19T03:14:08Z";

    private MnInfo() {
    }

    private static boolean isRunningFromIDE() {
        String p = System.getProperty("midnight.iside");
        return Boolean.parseBoolean(p);
    }

    private static boolean isRunningDatagen() {
        String p = System.getProperty("midnight.datagen");
        return Boolean.parseBoolean(p);
    }

    private static boolean isRunningTestServer() {
        String p = System.getProperty("midnight.istestserver");
        return Boolean.parseBoolean(p);
    }

    private static boolean forceDisableMidnightMusic() {
        String p = System.getProperty("midnight.disablemusic");
        return Boolean.parseBoolean(p);
    }

    @Override
    public String modid() {
        return MODID;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String version() {
        return VERSION;
    }

    @Override
    public String versionName() {
        return VERSION_NAME;
    }

    @Override
    public String expectedSHA256() {
        return EXPECTED_SHA256;
    }

    @Override
    public String buildDate() {
        return BUILD_DATE;
    }

    @Override
    public boolean ide() {
        return IDE;
    }

    @Override
    public boolean data() {
        return DATAGEN;
    }

    @Override
    public boolean testServer() {
        return TESTSERVER;
    }

    @Override
    public boolean musicDisabled() {
        return MUSIC_DISABLED;
    }
}
