package pccontroller;

import backend.Device;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DashboardPaneConnectedController {

    @FXML public FlowPane connectedDevicesContainer;
    private final HashMap<String,HBox> deviceContainersMap = new HashMap<>();

    public void addDevice(Device device) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/connected_device_fragment.fxml"));
        try {
            HBox deviceContainer = loader.load();
            ConnectedDeviceFragmentController controller = loader.getController();
            controller.init(device);

            deviceContainersMap.put(device.connectionID,deviceContainer);
            connectedDevicesContainer.getChildren().add(deviceContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeDevice(String connectionID) {
        Platform.runLater(
                () -> {
                    try {
                        connectedDevicesContainer.getChildren().remove(deviceContainersMap.get(connectionID));
                        deviceContainersMap.remove(connectionID);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

    }

    public void removeAllDevices() {
        //TODO
    }
}