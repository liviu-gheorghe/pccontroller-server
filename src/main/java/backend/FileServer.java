package backend;

import util.NetworkManager;
import java.net.*;
import java.io.*;

public class FileServer {

    private static FileServer INSTANCE = null;
    private ServerSocket fileServerSocket;
    private FileConnection fileConnection = null;

    public void clearConnection() {
        fileConnection = null;
        new Thread(this::listen).start();
    }

    private FileServer() {
        try {
            int port = NetworkManager.PortRange.getFirstAvailablePort();
            fileServerSocket = new ServerSocket(port);
        }
        catch(IOException | NetworkManager.NoPortsAvailableException e) {
            e.printStackTrace();
        }
    }

    public FileConnection getConnection() {
        return fileConnection;
    }

    public static FileServer getInstance() {
        return INSTANCE;
    }

    public static void setInstance() {
        INSTANCE = new FileServer();
    }

    public void startServer() {
        listen();
    }

    private void listen() {
        try {
            Socket socket = fileServerSocket.accept();
            System.out.println("File server connection accepted");
            fileConnection = new FileConnection(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            fileServerSocket.close();
        }
        catch(IOException e) {
            //TODO
        }
    }
}