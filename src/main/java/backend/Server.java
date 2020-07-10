package backend;

import pccontroller.App;
import pccontroller.MainController;
import util.ClipboardManager;
import util.NetworkManager;

import java.awt.*;
import java.net.*;
import java.io.*;

public class Server {

    private static Server INSTANCE = null;
    private MainController mainController = MainController.getInstance();
    private ServerSocket serverSocket;
    private Connection connection = null;

    public void clearConnection() {
        connection = null;
        new Thread(this::listen).start();
    }

    private Server() {
        try {
            int port = NetworkManager.PortRange.getFirstAvailablePort();
            serverSocket = new ServerSocket(port);
        }
        catch(IOException | NetworkManager.NoPortsAvailableException e) {
            System.out.println("Line 27 in Server.java ");
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
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
            Socket socket = serverSocket.accept();
            System.out.println("Connection accepted");
            connection = new Connection(socket);
        } catch (IOException e) {
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