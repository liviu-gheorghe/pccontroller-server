package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;

import java.io.DataInputStream;
import java.io.InputStream;

public class ActionReceiveFile implements Action {

    private DataInputStream dataInputStream;

    public ActionReceiveFile(InputStream inputStream) {
        dataInputStream = new DataInputStream(inputStream);
    }

    @Override
    public void execute() {
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_FILE;
    }
}
