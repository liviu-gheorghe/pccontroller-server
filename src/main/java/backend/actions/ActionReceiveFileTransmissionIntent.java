package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;

public class ActionReceiveFileTransmissionIntent implements Action {

    private String data;
    public ActionReceiveFileTransmissionIntent(String data) {
        this.data = data;
    }

    @Override
    public void execute() {

    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_FILE_TRANSMISSION_INTENT;
    }
}
