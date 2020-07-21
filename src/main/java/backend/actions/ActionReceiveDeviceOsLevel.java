package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import pccontroller.MainController;

public class ActionReceiveDeviceOsLevel implements Action {

    private MainController mainController = MainController.getInstance();
    private String osLevel;
    public ActionReceiveDeviceOsLevel(String osLevel) {
        this.osLevel = osLevel;
    }

    @Override
    public void execute() {
        new Thread(
                () -> {
                    try {
                        mainController.getDevicePaneController().getOsVersion().setText(osLevel);
                    }
                    catch (NullPointerException e) {

                    }
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_DEVICE_OS_LEVEL;
    }
}
