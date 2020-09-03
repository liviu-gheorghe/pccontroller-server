package backend.actions;

import backend.Action;
import backend.FileServer;
import backend.ReceivedActionsCodes;
import javafx.application.Platform;
import pccontroller.App;
import util.NotificationManager;
import java.awt.*;
import java.io.*;

public class ActionReceiveFile implements Action {

    private final DataInputStream dataInputStream;
    private final int BUFFER_SIZE = 1024*40;
    private String PATH;
    private String FILE_NAME;

    public ActionReceiveFile(InputStream inputStream) {
        dataInputStream = new DataInputStream(inputStream);
        try {
            PATH = System.getProperty("user.home")+"/Documents/";
        }
        catch(Exception e) {
            NotificationManager.showNotification(
                    "File transfer error",
                    "The directory where the file should be saved does not exist or PC Controller is not allowed to access it",
                    event -> {}
            );
        }
    }

    @Override
    public void execute() {
        if(PATH == null) return;
        new Thread(
                () -> {
                    int totalNumberOfBytes = 0;
                    long t1 = System.currentTimeMillis();
                    try {
                        int filenameSize = dataInputStream.readInt();

                        byte[] filenameBytes = dataInputStream.readNBytes(filenameSize);
                        StringBuilder fileNameStringBuilder = new StringBuilder();
                        for (byte filenameByte : filenameBytes) fileNameStringBuilder.append((char)filenameByte);

                        FILE_NAME = fileNameStringBuilder.toString();
                        File newFile = new File(PATH+FILE_NAME);
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int numberOfBytes;
                        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                        while((numberOfBytes = dataInputStream.read(buffer,0,BUFFER_SIZE)) != -1) {
                            if(numberOfBytes > 0) {
                                totalNumberOfBytes += numberOfBytes;
                                fileOutputStream.write(buffer,0,numberOfBytes);
                            }
                        }
                        fileOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    finally {
                        FileServer.getInstance().clearConnection();
                    }
                    long t2 = System.currentTimeMillis();
                    double seconds = (t2-t1)/1000.0;
                    double transferSpeed = (totalNumberOfBytes/(1024*1024.0))/seconds;
                    boolean isFileOpeningSupported = Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN);
                    NotificationManager.showNotification(String.format(
                            "Received file %s from %s.%s",
                            FILE_NAME,
                            App.CONNECTED_DEVICE_NAME,
                            (isFileOpeningSupported ?  "Tap to open containing directory":"")
                            ), "File saved in " + PATH,
                            event -> {
                                File targetDirectory =  new File(PATH);
                                try {
                                    if(isFileOpeningSupported) {
                                        try {
                                            new Thread(()-> {
                                                try {
                                                    Desktop.getDesktop().open(targetDirectory);
                                                } catch (IOException exception) {
                                                    exception.printStackTrace();
                                                }
                                            }).start();
                                        } catch(Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_FILE;
    }
}
