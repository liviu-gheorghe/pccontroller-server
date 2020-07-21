package backend.actions;

import backend.Action;
import backend.FileServer;
import backend.ReceivedActionsCodes;

import java.io.*;

public class ActionReceiveFile implements Action {

    private final DataInputStream dataInputStream;
    private final int BUFFER_SIZE = 1024*40;



    public ActionReceiveFile(InputStream inputStream) {
        dataInputStream = new DataInputStream(inputStream);
    }

    @Override
    public void execute() {
        new Thread(
                () -> {
                    int totalNumberOfBytes = 0;
                    long t1 = System.currentTimeMillis();
                    try {
                        int filenameSize = dataInputStream.readInt();
                        
                        byte[] filenameBytes = dataInputStream.readNBytes(filenameSize);
                        StringBuilder fileNameStringBuilder = new StringBuilder();
                        for (byte filenameByte : filenameBytes) fileNameStringBuilder.append((char)filenameByte);

                        System.out.println("Received file name : " + fileNameStringBuilder.toString());
                        File newFile = new File(System.getProperty("user.home")+"/Documents/"+fileNameStringBuilder.toString());
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
                    System.out.printf("Transferred %d bytes successfully , time = %fs\n",totalNumberOfBytes,seconds);
                    double transferSpeed = (totalNumberOfBytes/(1024*1024.0))/seconds;
                    System.out.printf("Average transfer speed is : %f MB/s",transferSpeed);
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_FILE;
    }
}
