package pccontroller;

import backend.DispatchedActionsCodes;
import backend.Server;
import backend.actions.ActionReceiveConnectionRequest;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
        Server.getInstance().getConnection().dispatchAction(DispatchedActionsCodes.RING_DEVICE,"vb");
    }

    @FXML
    public void sendFileButtonClick() {
        //new Thread(() -> Server.getInstance().getConnection().dispatchAction(DispatchedActionsCodes.SEND_FILE,"")).start();
    }

    @FXML private void sendPingButtonClick() {
        Server.getInstance().getConnection().dispatchAction(DispatchedActionsCodes.SEND_PING,"");
    }
}