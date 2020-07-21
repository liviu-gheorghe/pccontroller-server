package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import pccontroller.MainController;

public class ActionReceiveDeviceVolumeLevel implements Action {

    private MainController mainController = MainController.getInstance();
    private String volumeLevel;
    public ActionReceiveDeviceVolumeLevel(String volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    @Override
    public void execute() {
        new Thread(
                () -> {
                    try {
                        mainController.getDevicePaneController().getVolumeLevel().setText(volumeLevel);
                    }
                    catch (NullPointerException e) {

                    }
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_DEVICE_VOLUME_LEVEL;
    }
}
