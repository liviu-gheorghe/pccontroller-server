package util;

import pccontroller.App;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;


public class NetworkManager {

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
            return datagramSocket.getLocalAddress().getHostAddress();
        }
        catch(UnknownHostException e) {
            return null;
        }
    }

    public final static class PortRange {
        public final static int MIN_PORT_NUMBER = 8560;
        public final static int MAX_PORT_NUMBER = 8580;
        public static ArrayList<Integer> getAvailablePorts() {
            ArrayList<Integer> ports = new ArrayList<>();
            for(int port = PortRange.MIN_PORT_NUMBER; port<=PortRange.MAX_PORT_NUMBER;port++)
                if(isPortAvailable(port))
                    ports.add(port);
                return ports;
        }
        public static int getFirstAvailablePort() throws NoPortsAvailableException {
            try {
                return PortRange.getAvailablePorts().get(0);
            }
            catch(IndexOutOfBoundsException e) {
                throw new NoPortsAvailableException();
            }
        }
    }


    public static class NoPortsAvailableException extends Exception {
    }

    public static boolean isPortAvailable(int port) {

        if(port < PortRange.MIN_PORT_NUMBER || port > PortRange.MAX_PORT_NUMBER) throw new IllegalArgumentException("Port range is invalid");
        ServerSocket serverSocket = null;
        DatagramSocket datagramSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            datagramSocket = new DatagramSocket(port);
            datagramSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }

            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        for(int port = PortRange.MIN_PORT_NUMBER; port<=PortRange.MAX_PORT_NUMBER;port++)
            System.out.println("Availability for port " + port + " " + isPortAvailable(port));
    }
}
