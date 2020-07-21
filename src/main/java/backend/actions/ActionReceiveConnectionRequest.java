package backend.actions;

import backend.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pccontroller.App;
import pccontroller.Main;
import java.io.IOException;


public class ActionReceiveConnectionRequest implements Action {
    String deviceName;
    public ActionReceiveConnectionRequest(String deviceName) {
        this.deviceName = deviceName;
    }
    
    @Override
    public void execute() {

        Platform.runLater(
            () -> {
                FXMLLoader loader = new FXMLLoader();
                Parent root;
                loader.setLocation(Main.class.getResource("/fxml/accept_connection.fxml"));
                try {
                    root = loader.load();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    return;
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root,600,400);
                stage.setScene(scene);
                stage.setTitle("A device is trying to connect");
                stage.setAlwaysOnTop(true);
                stage.setResizable(false);
                stage.setOnHiding(
                        event -> {
                            System.out.println("Dialog box exited , connection accepted : " + App.CONNECTION_ACCEPTED);
                            if(!App.CONNECTION_ACCEPTED)
                                Server.getInstance().getConnection().closeConnection();
                        }
                );
                Text text = (Text) scene.lookup("#deviceRequestingConnectionName");
                text.setText(deviceName);
                Button accept = (Button) scene.lookup("#acceptConnectionButton");
                Button decline = (Button) scene.lookup("#declineConnectionButton");
                accept.setOnMouseClicked(
                        event -> {
                            App.CONNECTION_ACCEPTED = true;
                            App.CONNECTED_DEVICE_NAME = deviceName;
                            stage.close();
                            try {
                                Server.getInstance().getConnection().dispatchAction(DispatchedActionsCodes.ACCEPT_CONNECTION,"");
                                Server.getInstance().getConnection().onConnectionAccepted(deviceName);
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
                decline.setOnMouseClicked(
                        event -> {
                            App.CONNECTION_ACCEPTED = false;
                            try {
                                Server.getInstance().getConnection().dispatchAction(DispatchedActionsCodes.DECLINE_CONNECTION,"");
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                            stage.close();
                        }
                );
                stage.show();
            }
        );
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_CONNECTION_REQUEST;
    }
}