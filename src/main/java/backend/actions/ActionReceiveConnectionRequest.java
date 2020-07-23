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
import java.util.concurrent.atomic.AtomicBoolean;


public class ActionReceiveConnectionRequest implements Action {

    String deviceName;
    String connectionID;
    public ActionReceiveConnectionRequest(String deviceName,String connectionID) {
        this.deviceName = deviceName;
        this.connectionID = connectionID;
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

                AtomicBoolean connectionAccepted = new AtomicBoolean(false);

                stage.setOnHiding(
                        event -> {
                            System.out.println("Dialog box exited , connection accepted : " + connectionAccepted);
                            if(!connectionAccepted.get())
                                Server.getInstance().getConnection(connectionID).closeConnection();
                        }
                );
                Text text = (Text) scene.lookup("#deviceRequestingConnectionName");
                text.setText(deviceName);
                Button accept = (Button) scene.lookup("#acceptConnectionButton");
                Button decline = (Button) scene.lookup("#declineConnectionButton");
                accept.setOnMouseClicked(
                        event -> {
                            connectionAccepted.set(true);
                            try {
                                System.out.println("Sending connection acceptance message for connection id "+ connectionID);
                                Server.getInstance().getConnection(connectionID).dispatchAction(DispatchedActionsCodes.ACCEPT_CONNECTION,"");
                                Server.getInstance().getConnection(connectionID).onConnectionAccepted(deviceName);
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                            finally {
                                stage.close();
                            }
                        }
                );
                decline.setOnMouseClicked(
                        event -> {
                            connectionAccepted.set(false);
                            try {
                                Server.getInstance().getConnection(connectionID).dispatchAction(DispatchedActionsCodes.DECLINE_CONNECTION,"");
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                            finally {
                                stage.close();
                            }
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