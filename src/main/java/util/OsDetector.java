package util;

public class OsDetector {


    public static class UnrecognizedOsException extends Exception {

    }

    public static int getMachineOsId() throws UnrecognizedOsException {
        if (OsDetector.isWindows()) return 0;
        else if (OsDetector.isUnix()) return 1;
        else if (OsDetector.isMac()) return 2;
        throw new UnrecognizedOsException();
    }

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