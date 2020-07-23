package backend;

import pccontroller.MainController;
import util.NetworkManager;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private static Server INSTANCE = null;
    private ServerSocket serverSocket;
    //private Connection connection = null;
    //private final ArrayList<Connection> connections = new ArrayList<>();
    private final HashMap<String,Connection> connections = new HashMap<>();
    private String lastConnectionID;

    public int getConnectionsCount() {
        return connections.size();
    }

    public void clearConnection(String key) throws IndexOutOfBoundsException {
        connections.remove(key);
        new Thread(this::listen).start();
    }

    private Server() {
        try {
            int port = NetworkManager.PortRange.getFirstAvailablePort();
            serverSocket = new ServerSocket(port);
        }
        catch(IOException | NetworkManager.NoPortsAvailableException e) {
            System.out.println("Line 26 in Server.java ");
            e.printStackTrace();
        }
    }

    public Connection getConnection(String key) throws IndexOutOfBoundsException {
        return connections.get(key);
    }

    public Connection getLastConnection() {
        return connections.get(lastConnectionID);
    }

    public static Server getInstance() {
        return INSTANCE;
    }

    public static void setInstance() {
        INSTANCE = new Server();
    }

    public void startServer() {
        listen();
    }

    private void listen() {
        try {
                new Thread(
                        () -> {
                            try {
                                Socket socket = serverSocket.accept();
                                System.out.println("Connection accepted");
                                String deviceAddress = socket.getInetAddress().getHostAddress();
                                connections.put(deviceAddress,new Connection(socket,deviceAddress));
                                lastConnectionID = deviceAddress;
                                listen();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                ).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            serverSocket.close();
        }
        catch(IOException e) {
            //TODO
        }
    }
}