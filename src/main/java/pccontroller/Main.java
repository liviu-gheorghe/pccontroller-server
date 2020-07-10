package pccontroller;

import backend.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.NetworkManager;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        App.onCreate();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/app.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("PC Controller");
        Scene scene = new Scene(root,1200,800);
       scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        Server.setInstance();
        new Thread(() -> Server.getInstance().startServer()).start();
        Text hostIpAddressText = (Text) scene.lookup("#hostIpAddress");
        if(hostIpAddressText != null) {
            hostIpAddressText.setText(NetworkManager.getLanIpAddress());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}