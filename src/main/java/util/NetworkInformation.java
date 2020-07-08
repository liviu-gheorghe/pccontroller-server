package util;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;


public class NetworkInformation {

    public static void getNetworkInterfaces() throws SocketException {
        ArrayList<NetworkInterface> nets = Collections.list(NetworkInterface.getNetworkInterfaces());
        System.out.println(String.format("Found %d network interfaces on this machine",nets.size()));
        int count = 0;
        for (NetworkInterface iface : nets) {
            System.out.println(String.format("Info for network interface no %d",count++));
            System.out.println(String.format("Name : %s ",iface.getName()));

            System.out.println("Inet Addresses : ");
            Enumeration<InetAddress> inetAddresses = iface.getInetAddresses();
            for(InetAddress inetAddress : Collections.list(inetAddresses)) {
                System.out.println(String.format("Inet Address : %s",inetAddress));
            }
            System.out.println("\n");
        }
    }

    public static String getLanIpAddress() throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket();
        try {
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"),10002);
            System.out.println(datagramSocket.getLocalAddress().isAnyLocalAddress());
            return datagramSocket.getLocalAddress().getHostAddress();
        }
        catch(UnknownHostException e) {
            System.err.println("Unknown host 8.8.8.8");
            return null;
        }
    }
}
