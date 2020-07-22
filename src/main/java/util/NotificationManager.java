package util;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class NotificationManager {
    public static void showNotification(String message) throws AWTException, IOException {
        if(SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            String imagePath = System.getProperty("user.home")+"\\"+"smartphone.png";
            System.out.println("Image path is : "+ imagePath);
            Image iconImage = ImageIO.read(NotificationManager.class.getResource("/images/smartphone.png"));
            PopupMenu popup = new PopupMenu();
            TrayIcon trayIcon = new TrayIcon(iconImage,"PC Controller",popup);
            trayIcon.setToolTip("Tooltip");
            trayIcon.setImageAutoSize(true);
            systemTray.add(trayIcon);
        }
        else System.out.println("System tray is not supported");
    }
}

class Driver {
    public static void main(String[] args) {
        System.out.println("Showing notification");
        try {
            NotificationManager.showNotification("Hello there");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

