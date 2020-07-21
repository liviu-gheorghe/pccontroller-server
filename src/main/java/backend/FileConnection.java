package backend;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileConnection {
    public FileConnection(Socket fileSocket) {
        try {
            DataInputStream inputStream = new DataInputStream(fileSocket.getInputStream());
            Action action = ActionFactory.createAction(ReceivedActionsCodes.ACTION_RECEIVE_FILE,inputStream);
            if(action == null) return;
            action.execute();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
