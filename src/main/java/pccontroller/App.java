package pccontroller;

import util.OsDetector;
import util.XMLUserDataLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static int OS_ID;
    public static String HOSTNAME = "";
    public static boolean CONNECTION_ALIVE = false;
    public static String CONNECTED_DEVICE_NAME = "";
    public static String CONNECTED_DEVICE_IP = "";
    public static boolean CONNECTION_ACCEPTED = false;

    public static void onCreate() {
        try {
            OS_ID = OsDetector.getMachineOsId();
        }
        catch(OsDetector.UnrecognizedOsException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        XMLUserDataLoader.loadDefinedCommands();
        getMachineHostname();
    }

    private static void getMachineHostname() {

        StringBuilder sb = new StringBuilder();
        new Thread(
                () -> {
                    try {
                        Process p = Runtime.getRuntime().exec("hostname");
                        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null)
                            sb.append(line);
                        HOSTNAME = sb.toString();
                        p.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}