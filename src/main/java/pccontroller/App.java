package pccontroller;

import util.OsDetector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static final int ACTION_SHARE_FILE = 8;
    public static int OS_ID = -1;
    public static String HOSTNAME = "";
    public static boolean CONNECTION_ALIVE = false;
    public static String CONNECTED_DEVICE_NAME = "";
    public static String CONNECTED_DEVICE_IP = "";

    public static void onCreate() {
        getMachineOs();
        getMachineHostname();
    }

    private static void getMachineOs() {
        if (OsDetector.isWindows()) OS_ID = 0;
        else if (OsDetector.isUnix()) OS_ID = 1;
        else if (OsDetector.isMac()) OS_ID = 2;
    }

    private static void getMachineHostname() {
        new Thread(
                () -> {
                    try {
                        Process p = Runtime.getRuntime().exec("hostname");
                        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null)
                            HOSTNAME += line;
                        p.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}