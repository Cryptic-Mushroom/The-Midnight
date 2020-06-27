package midnight;

public final class MidnightInfo {
    public static final String MODID = "midnight";
    public static final String NAME = "The Midnight";

    public static final boolean IDE = isRunningFromIDE();
    public static final boolean TEST_SERVER = isTestServer();

    @DynamicConstant("version")
    public static final String VERSION = "NOT.A.VERSION";

    @DynamicConstant("version_name")
    public static final String VERSION_NAME = "Not A Version";

    @DynamicConstant("sha1")
    public static final String SHA1 = "SHA1";

    @DynamicConstant("build_time")
    public static final String BUILD_DATE = "2038-01-19T03:14:08Z"; // https://en.wikipedia.org/wiki/Year_2038_problem

    private MidnightInfo() {
    }

    private static boolean isRunningFromIDE() {
        String p = System.getProperty("midnight.iside");
        return Boolean.parseBoolean(p);
    }

    private static boolean isTestServer() {
        String p = System.getProperty("midnight.istestserver");
        return Boolean.parseBoolean(p);
    }
}
