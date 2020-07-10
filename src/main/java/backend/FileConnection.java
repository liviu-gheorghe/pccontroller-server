package backend;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileConnection {
    public FileConnection(Socket fileSocket) {
        try {
            DataInputStream inputStream = new DataInputStream(fileSocket.getInputStream());
            Action action = ActionFactory.createAction(9,inputStream);
            action.execute();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
