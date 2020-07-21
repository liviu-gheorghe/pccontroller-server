package backend;

import pccontroller.App;
import pccontroller.MainController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

    private final Socket socket;
    private final MainController mainController = MainController.getInstance();
    private DataInputStream inputStream;
    private Connection.ActionHandler actionHandler;
    private Connection.ActionDispatcher actionDispatcher;


    public Connection(Socket socket) {
        onCreate();
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            actionHandler = new ActionHandler();
            new Thread(() -> listenForInput(inputStream)).start();
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
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

    public void onConnectionAccepted(String deviceName) {
        new Thread(
                () -> {
                    try {
                        mainController.getDashboardPaneController().getConnectedDeviceInfo().setText(deviceName);
                        mainController.getDashboardPaneController().getCloseConnectionButton().setVisible(true);
                        mainController.getDashboardPaneController().getConnectionInstructions().setText("");
                    }
                    catch(NullPointerException e) {
                        e.printStackTrace();
                    }
                    mainController.getDevicePaneController().getDeviceFullName().setText(deviceName);
                    mainController.showPaneButton(1);
                    mainController.getDashboardPaneController().setPhoneImage();
                    App.CONNECTED_DEVICE_NAME = deviceName;
                }
        ).start();
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
        App.CONNECTION_ACCEPTED = false;
        onDestroy();
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Server.getInstance().clearConnection();
    }


    private static class ActionHandler {

        public void handleAction(int type, String content) {
            Action action = ActionFactory.createAction(type,content);
            if(action == null) return;
            action.execute();
        }
    }

    private class ActionDispatcher {

        private final DataOutputStream outputStream;

        private ActionDispatcher(DataOutputStream dOut) {
            outputStream = dOut;
        }

        private void dispatchAction(int type,String content) {
            try {
                outputStream.writeByte(type);
                outputStream.writeUTF(content);
                outputStream.flush();
            } catch (IOException e) {
                closeConnection();
            }
        }
    }
}
