package pccontroller;

import backend.Device;
import backend.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ConnectedDeviceFragmentController {

    @FXML public Button closeConnectionButton;
    @FXML public Text deviceFullName;

    private Device device;
    public void init(Device device) {
        this.device = device;
        deviceFullName.setText(device.fullName);
    }

    public void clickCloseConnectionButton() {
        try {
            MainController.getInstance().getDashboardPaneConnectedController().removeDevice(device.connectionID);
            Server.getInstance().getConnection(device.connectionID).closeConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
