package backend.actions;

import backend.Action;
import backend.DispatchedActionsCodes;
import backend.ReceivedActionsCodes;
import backend.Server;
import util.NetworkManager;

public class ActionReceiveFileTransmissionIntent implements Action {

    private String data;
    public ActionReceiveFileTransmissionIntent(String data) {
        this.data = data;
    }

    @Override
    public void execute() {
        try {
            int port = NetworkManager.PortRange.getFirstAvailablePort();
            Server.getInstance().getConnection().dispatchAction(DispatchedActionsCodes.SEND_PORT_NUMBER_FOR_FILE_TRANSMISSION,String.valueOf(port));
        } catch (NetworkManager.NoPortsAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_FILE_TRANSMISSION_INTENT;
    }
}
