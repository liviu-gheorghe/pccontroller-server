package pccontroller;

import backend.Server;
import backend.QRCodeGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import util.NetworkManager;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPaneController implements Initializable {

    @FXML public ImageView dashboardSceneImage;
    @FXML protected Text connectedDeviceInfo;
    @FXML protected  Text connectionInstructions;
    @FXML protected Button closeConnectionButton;

    public Text getConnectedDeviceInfo() {
        return this.connectedDeviceInfo;
    }

    public Text getConnectionInstructions() {
        return this.connectionInstructions;
    }

    public Button getCloseConnectionButton() {
        return this.closeConnectionButton;
    }


    public void setQrCodeImage() {
        try {
            BufferedImage qrCodeBufferedImage = QRCodeGenerator.generateQRCodeImage(NetworkManager.getLanIpAddress()+","+App.HOSTNAME);
            Image qrCodeImage = SwingFXUtils.toFXImage(qrCodeBufferedImage,null);
            this.dashboardSceneImage.setFitHeight(300);
            this.dashboardSceneImage.setFitWidth(300);
            dashboardSceneImage.setImage(qrCodeImage);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void setPhoneImage() {
        try {
            BufferedImage phoneBufferedImage = ImageIO.read(getClass().getResource("/images/phone.png"));
            Image phoneImage = SwingFXUtils.toFXImage(phoneBufferedImage,null);
            this.dashboardSceneImage.setFitHeight(150);
            this.dashboardSceneImage.setFitWidth(150);
            this.dashboardSceneImage.setImage(phoneImage);
        }
        catch (Exception e) {
            //TODO
        }
    }

    @FXML
    public void closeConnectionButtonClick() {
        Server.getInstance().getConnection().closeConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQrCodeImage();
        closeConnectionButton.setVisible(false);
    }
}
