package backend;

import javafx.application.Platform;
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
    private final String connectionID;


    public Connection(Socket socket,String connectionID) {
        this.socket = socket;
        this.connectionID = connectionID;
        onCreate();
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
        System.out.println("Connection created , connection id is : "+connectionID);
    }

    public void onConnectionAccepted(String deviceName) {

        Device.DeviceBuilder deviceBuilder = new Device.DeviceBuilder();
        Device device = deviceBuilder.setConnectionID(connectionID)
                        .setFullName(deviceName)
                        .build();
        new Thread(
                () -> {
                    /**
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
                     **/

                    Platform.runLater(
                            () -> {
                                mainController.showPaneButton(1);
                                mainController.switchToConnectedModeLayout();
                                mainController.getDashboardPaneConnectedController().addDevice(device);
                            }
                    );
                }
        ).start();
    }

    private void onDestroy() {
        /**
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
         **/
    }

    private void listenForInput(DataInputStream dIn) {
        try {
            while (true) {
                byte type = dIn.readByte();
                String content = dIn.readUTF();

                System.out.println("Received : "+type + " "+ content);
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
        MainController.getInstance().getDashboardPaneConnectedController().removeDevice(connectionID);
        Server.getInstance().clearConnection(connectionID);
    }


    private class ActionHandler {

        public void handleAction(int type, String content) {
            Action action = ActionFactory.createAction(type,content,connectionID);
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









