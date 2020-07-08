package backend;

import pccontroller.App;
import pccontroller.Controller;
import java.awt.*;
import java.awt.datatransfer.*;
import java.net.*;
import java.io.*;

public class ControllerServer {

    private static ControllerServer INSTANCE = null;
    private final Controller controller;
    private DataInputStream socketInputStream;
    private DataOutputStream socketOutputStream;
    private ServerSocket serverSocket;
    private Socket socket;
    private int PORT;

    private ControllerServer(int port, Controller controller) {
        this.PORT = port;
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        this.controller = controller;
        ActionHandler.setInstance(this.controller);
    }

    public static ControllerServer getInstance() {
        return INSTANCE;
    }

    public void startServer() {
        //listen for incoming connections
        listen();
    }

    public static void setInstance(int port,Controller controller) {
        INSTANCE = new ControllerServer(port,controller);
    }

    private void listen() {
        try {
            socket = serverSocket.accept();
            System.out.println("Connection accepted");
            socketInputStream = new DataInputStream(socket.getInputStream());
            new Thread(() -> listenForInput(socketInputStream)).start();
            socketOutputStream = new DataOutputStream(socket.getOutputStream());
            ActionDispatcher.setInstance(socketOutputStream);
            ActionDispatcher.getInstance().dispatchAction(0, App.HOSTNAME);
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

    private void listenForInput(DataInputStream dIn) {
        try {
            while (true) {
                byte messageType = dIn.readByte();
                String receivedAction = dIn.readUTF();
                new Thread(() -> ActionHandler.getInstance().handleAction(messageType, receivedAction)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection(true);
        }
    }

    public void closeConnection(boolean restart_connection) {
        controller.hidePaneButton(1);
        controller.getDashboardPaneController().getCloseConnectionButton().setVisible(false);
        controller.getDashboardPaneController().setQrCodeImage();
        restart_connection = true;
        socketInputStream = null;
        socketOutputStream = null;
        try {
            controller.getDashboardPaneController().getConnectedDeviceInfo().setText("No device connected");
            controller.getDashboardPaneController().getConnectionInstructions().setText("Connect your smartphone using your PC's Local IP Address or scan the QR code above");
            socket.close();
        }
        catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
        }
        if(restart_connection)
            new Thread(this::listen).start();
    }

    private String getSystemClipboardContent() {
        try {
            return Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
        }
        catch(IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void listenForClipboardChanges() {
        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(event -> {
            try {
                String clipboardContent = getSystemClipboardContent();
                ActionDispatcher.getInstance().dispatchAction(3,clipboardContent);
            }
            catch(Exception e) {
                System.out.println("Some exception occurred while trying to get the clipboard contents");
                e.printStackTrace();
            }
        });
    }
}