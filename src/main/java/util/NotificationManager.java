package util;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import pccontroller.App;

public class NotificationManager {
    public static void showNotification(String title, String text, EventHandler<ActionEvent> eventHandler) {
        if(!App.NOTIFICATION_STAGE.isShowing()) App.NOTIFICATION_STAGE.show();

        Notifications notification = Notifications.create()
                .title(title)
                .owner(App.NOTIFICATION_STAGE)
                .text(text)
                .position(Pos.BOTTOM_CENTER)
                .hideAfter(Duration.INDEFINITE)
                .onAction(eventHandler);

        if(!Thread.currentThread().getName().equals(App.FX_THREAD_NAME)) {
            Platform.runLater(
                    notification::show
            );
        }
        else notification.show();
    }
}
