package pccontroller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML protected DashboardPaneController dashboardPaneController;
    @FXML protected  DevicePaneController devicePaneController;
    @FXML protected SplitPane splitPane;
    @FXML protected AnchorPane menuPane;
    @FXML public AnchorPane mainPane;
    @FXML public Button dashboardButton;
    @FXML public Button deviceButton;
    @FXML public Button settingsButton;
    @FXML public Button aboutButton;

    private AnchorPane dashboardPaneConnected;
    private VBox dashboardPaneDisconnected;
    private DashboardPaneConnectedController dashboardPaneConnectedController;
    private boolean connectedModeEnabled = false;

    public Button[] menuButtons = new Button[4];
    private int currentPane = -1;
    private static MainController INSTANCE = null;

    public MainController() {
        INSTANCE = this;
        try {
            System.out.println("Initializing Main Controller");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dashboard_scene_connected.fxml"));
            dashboardPaneConnected = loader.load();
            dashboardPaneConnectedController = loader.getController();
            assert(dashboardPaneConnectedController != null);
            System.out.println("dashboardPaneConnectedController : "+ dashboardPaneConnectedController.toString());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static MainController getInstance() {
        return INSTANCE;
    }

    public DashboardPaneController getDashboardPaneController() {
        return this.dashboardPaneController;
    }

    public DashboardPaneConnectedController getDashboardPaneConnectedController() {
        return this.dashboardPaneConnectedController;
    }

    public  DevicePaneController getDevicePaneController() {
        return this.devicePaneController;
    }

    public void showPane(int index) {
        if(currentPane == -1) {
            for(int i=0;i<4;i++) {
                hidePane(i);
            }
        }
        if(currentPane != -1) {
            mainPane.getChildren().get(currentPane).setScaleY(0);
            mainPane.getChildren().get(currentPane).setManaged(false);
            menuButtons[currentPane].setStyle("-fx-background-color:#df4a16");
        }
        menuButtons[index].setStyle("-fx-background-color:#661f8f");
        mainPane.getChildren().get(index).setScaleY(1);
        mainPane.getChildren().get(index).setManaged(true);
        currentPane = index;
    }

    private void hidePane(int index) {
        mainPane.getChildren().get(index).setScaleY(0);
        mainPane.getChildren().get(index).setManaged(false);
    }


    public void hidePaneButton(int paneIndex,int ...args) {
        int paneToSwitch = (args.length > 0)? args[0] : 0;
        showPane(paneToSwitch);
        try {
            menuButtons[paneIndex].setScaleY(0);
            menuButtons[paneIndex].setManaged(false);
        }
        catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void showPaneButton(int paneIndex , int ...args) {
        try {
            menuButtons[paneIndex].setScaleY(1);
            menuButtons[paneIndex].setManaged(true);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void switchToConnectedModeLayout() {
        if(!connectedModeEnabled)
        Platform.runLater(
            () -> {
                connectedModeEnabled = true;
                mainPane.getChildren().remove(0);
                mainPane.getChildren().add(0,dashboardPaneConnected);
                if(currentPane != 0)
                    hidePane(0);
            }
        );
    }

    public void switchToDisconnectedModeLayout() {
        if(connectedModeEnabled)
            Platform.runLater(
                    () -> {
                        mainPane.getChildren().remove(0);
                        mainPane.getChildren().add(0,dashboardPaneDisconnected);
                        if(currentPane != 0)
                            hidePane(0);
                    }
            );
        connectedModeEnabled = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SplitPane.setResizableWithParent(splitPane.getItems().get(0),false);
        dashboardPaneDisconnected = (VBox) mainPane.getChildren().get(0);
        menuButtons[0] = dashboardButton;
        menuButtons[1] = deviceButton;
        menuButtons[2] = settingsButton;
        menuButtons[3] = aboutButton;
        showPane(0);
        hidePaneButton(1);
        for(int i=0;i<4;i++) {
            int finalI = i;
            menuButtons[i].setOnMouseClicked(e -> {
                showPane(finalI);
            });
        };
    }
}


