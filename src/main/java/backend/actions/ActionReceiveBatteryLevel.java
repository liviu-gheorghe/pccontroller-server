package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import pccontroller.MainController;

public class ActionReceiveBatteryLevel implements Action {
    private String batteryLevel;
    private MainController mainController = MainController.getInstance();
    public ActionReceiveBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public void execute() {
        new Thread(
                () -> {
                    try {
                        mainController.getDevicePaneController().getBatteryLevel().setText(batteryLevel);
                    }
                    catch (NullPointerException e) {

                    }
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_DEVICE_BATTERY_LEVEL;
    }
}
