package pccontroller;

import backend.DispatchedActionsCodes;
import backend.Server;
import backend.actions.ActionReceiveConnectionRequest;
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

    private int connectionID;

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
        Server.getInstance().getLastConnection().dispatchAction(DispatchedActionsCodes.RING_DEVICE,"vb");
    }

    @FXML
    public void sendFileButtonClick() {
        //new Thread(() -> Server.getInstance().getLastConnection().dispatchAction(DispatchedActionsCodes.SEND_FILE,"")).start();
        MainController.getInstance().switchToConnectedModeLayout();
    }

    @FXML private void sendPingButtonClick() {
        Server.getInstance().getLastConnection().dispatchAction(DispatchedActionsCodes.SEND_PING,"");
    }
}