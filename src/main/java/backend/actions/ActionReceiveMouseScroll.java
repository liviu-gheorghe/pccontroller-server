package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import util.SystemInputController;

import java.awt.*;

public class ActionReceiveMouseScroll implements Action {
    private int distanceToScroll;
    public ActionReceiveMouseScroll(String content) {
        distanceToScroll = Integer.parseInt(content);
    }

    @Override
    public void execute() {
        new Thread(
                () -> {
                    try {
                        SystemInputController.getInstance().mouseWheel(distanceToScroll);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_MOUSE_SCROLL;
    }
}
