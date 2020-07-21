package pccontroller;

import backend.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML protected DashboardPaneController dashboardPaneController;
    @FXML protected  DevicePaneController devicePaneController;
    @FXML protected SplitPane splitPane;
    @FXML protected AnchorPane menuPane;
    @FXML public VBox mainPane;
    @FXML public Button dashboardButton;
    @FXML public Button deviceButton;
    @FXML public Button settingsButton;
    @FXML public Button aboutButton;
    public Button[] menuButtons = new Button[4];
    private int current_pane = -1;
    private static MainController INSTANCE = null;

    public MainController() {
        INSTANCE = this;
    }

    public static MainController getInstance() {
        return INSTANCE;
    }

    public DashboardPaneController getDashboardPaneController() {
        return this.dashboardPaneController;
    }

    public  DevicePaneController getDevicePaneController() {
        return this.devicePaneController;
    }

    public void showPane(int index) {
        if(current_pane == -1) {
            for(int i=0;i<4;i++) {
                mainPane.getChildren().get(i).setScaleY(0);
                mainPane.getChildren().get(i).setManaged(false);
            }
        }
        if(current_pane != -1) {
            mainPane.getChildren().get(current_pane).setScaleY(0);
            mainPane.getChildren().get(current_pane).setManaged(false);
            menuButtons[current_pane].setStyle("-fx-background-color:#df4a16");
        }
        menuButtons[index].setStyle("-fx-background-color:#661f8f");
        mainPane.getChildren().get(index).setScaleY(1);
        mainPane.getChildren().get(index).setManaged(true);
        current_pane = index;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        SplitPane.setResizableWithParent(splitPane.getItems().get(0),false);
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
