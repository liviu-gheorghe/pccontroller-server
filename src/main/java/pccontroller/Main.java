package pccontroller;

import backend.FileServer;
import backend.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
        Stage notificationStage = createNotificationStage();
        notificationStage.show();
        App.NOTIFICATION_STAGE = notificationStage;
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/ic_launcher.png")));
        Server.setInstance();
        FileServer.setInstance();
        new Thread(() -> Server.getInstance().startServer()).start();
        new Thread(() -> FileServer.getInstance().startServer()).start();
        Text hostIpAddressText = (Text) scene.lookup("#hostIpAddress");
        if(hostIpAddressText != null) {
            hostIpAddressText.setText(NetworkManager.getLanIpAddress());
        }
    }

    public Stage createNotificationStage() {
        float stageDimension = 1.0f;
        Stage notificationStage = new Stage();
        notificationStage.initModality(Modality.NONE);
        notificationStage.initStyle(StageStyle.UTILITY);
        notificationStage.setOpacity(0);
        notificationStage.setWidth(stageDimension);
        notificationStage.setHeight(stageDimension);
        notificationStage.setMaxHeight(stageDimension);
        notificationStage.setMaxWidth(stageDimension);
        final Screen screen = Screen.getPrimary();
        final Rectangle2D bounds = screen.getVisualBounds();
        notificationStage.setY(bounds.getMaxY());
        notificationStage.setX(bounds.getMaxX()/2);
        final Group root = new Group();
        Scene scene = new Scene(root,stageDimension,stageDimension, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        notificationStage.setScene(scene);
        notificationStage.setResizable(false);
        return notificationStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}