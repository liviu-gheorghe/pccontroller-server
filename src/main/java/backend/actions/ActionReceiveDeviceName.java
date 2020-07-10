package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import pccontroller.App;
import pccontroller.MainController;

public class ActionReceiveDeviceName implements Action {
    private String deviceName;
    private MainController mainController = MainController.getInstance();
    public ActionReceiveDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public void execute() {
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

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_DEVICE_NAME;
    }
}
