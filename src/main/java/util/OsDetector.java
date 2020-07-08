package util;

public class OsDetector {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS_NAME.contains("win");
    }

    public static boolean isUnix() {
        return OS_NAME.contains("nux") || OS_NAME.contains("nix");
    }

    public static boolean isMac() {
        return OS_NAME.contains("mac");
    }
}