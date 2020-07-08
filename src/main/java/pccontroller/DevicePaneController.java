package pccontroller;

import backend.ActionDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DevicePaneController {


    @FXML public Button sendFileButton;
    @FXML public Button ringDeviceButton;
    @FXML protected Text deviceFullName;
    @FXML protected Text batteryLevel;
    @FXML protected Text volumeLevel;
    @FXML protected Text osVersion;

    public Text getDeviceFullName() {
        return deviceFullName;
    }

    public Text getBatteryLevel() {
        return batteryLevel;
    }

    public Text getVolumeLevel() {
        return volumeLevel;
    }

    public Text getOsVersion() {
        return osVersion;
    }

    @FXML
    public void ringDeviceButtonClick() {
        ActionDispatcher.getInstance().dispatchAction(1,"vb");
    }

    @FXML
    public void sendFileButtonClick() {
        new Thread(() -> ActionDispatcher.getInstance().dispatchAction(8,"")).start();
    }

    @FXML private void sendPingButtonClick() {
        ActionDispatcher.getInstance().dispatchAction(2,"");
    }
}
