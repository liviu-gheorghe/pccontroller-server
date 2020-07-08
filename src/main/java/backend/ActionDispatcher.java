package backend;

import java.io.*;

public class ActionDispatcher {

    private static ActionDispatcher INSTANCE = null;
    private static  DataOutputStream dispatcherOutputStream = null;

    private ActionDispatcher(DataOutputStream dOut) {
        dispatcherOutputStream = dOut;
    }

    public static ActionDispatcher getInstance() {
        return INSTANCE;
    }

    public static void setInstance(DataOutputStream dOut) {
        if(INSTANCE == null)
            INSTANCE = new ActionDispatcher(dOut);
        else
            dispatcherOutputStream = dOut;
    }

    public void dispatchAction(int actionType,String actionContent) {
            try {
                dispatcherOutputStream.writeByte(actionType);
                dispatcherOutputStream.writeUTF(actionContent);
                dispatcherOutputStream.flush();
            } catch (IOException e) {
                backend.ControllerServer.getInstance().closeConnection(true);
            }
    }
}