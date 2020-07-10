package backend;

import java.net.*;
import java.io.*;

public class FileServer {

    private ServerSocket fileServerSocket;
    private FileConnection fileConnection = null;

    public void clearConnection() {
        fileConnection = null;
    }

    public FileServer(int port) {
        try {
            fileServerSocket = new ServerSocket(port);
        }
        catch(IOException e) {
            System.out.println("Line 27 in Server.java ");
            e.printStackTrace();
        }
    }
    public FileConnection getFileConnection() {
        return fileConnection;
    }

    public void startFileServer() {
        listen();
    }

    private void listen() {
        try {
            Socket socket = fileServerSocket.accept();
            System.out.println("File connection accepted");
            fileConnection = new FileConnection(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopFileServer() {
        try {
            fileServerSocket.close();
        }
        catch(IOException e) {
            
        }
    }
}