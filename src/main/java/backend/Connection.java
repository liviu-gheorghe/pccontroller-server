package backend;

import pccontroller.App;
import pccontroller.MainController;
import util.XMLUserDataLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

public class Connection {

    private Socket socket;
    private MainController mainController = MainController.getInstance();
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Connection.ActionHandler actionHandler;
    private Connection.ActionDispatcher actionDispatcher;


    public Connection(Socket socket) {
        onCreate();
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            actionHandler = new ActionHandler();
            new Thread(() -> listenForInput(inputStream)).start();
            outputStream = new DataOutputStream(socket.getOutputStream());
            actionDispatcher = new Connection.ActionDispatcher(outputStream);
            actionDispatcher.dispatchAction(0, App.HOSTNAME);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void dispatchAction(int type,String content) {
        actionDispatcher.dispatchAction(type,content);
    }

    private void onCreate() {

    }

    private void onDestroy() {
        mainController.hidePaneButton(1);
        mainController.getDashboardPaneController().getCloseConnectionButton().setVisible(false);
        mainController.getDashboardPaneController().setQrCodeImage();
        try {
            mainController.getDashboardPaneController().getConnectedDeviceInfo().setText("No device connected");
            mainController.getDashboardPaneController().getConnectionInstructions().setText("Connect your smartphone using your PC's Local IP Address or scan the QR code above");
            socket.close();
        }
        catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    private void listenForInput(DataInputStream dIn) {
        try {
            while (true) {
                byte type = dIn.readByte();
                String content = dIn.readUTF();
                new Thread(() -> actionHandler.handleAction(type, content)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    public void closeConnection() {
        System.out.println("Closing connection");
        onDestroy();
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Server.getInstance().clearConnection();
    }


    private class ActionHandler {

        public void handleAction(int type, String content) {
            Action action = ActionFactory.createAction(type,content);
            action.execute();
        }
        public void handleAction(int type, InputStream inputStream) {
            Action action = ActionFactory.createAction(type,inputStream);
            action.execute();
        }
    }

    private class ActionDispatcher {

        private DataOutputStream dispatcherOutputStream = null;

        private ActionDispatcher(DataOutputStream dOut) {
            dispatcherOutputStream = dOut;
        }

        private void dispatchAction(int type,String content) {
            try {
                dispatcherOutputStream.writeByte(type);
                dispatcherOutputStream.writeUTF(content);
                dispatcherOutputStream.flush();
            } catch (IOException e) {
                closeConnection();
            }
        }
    }
}
