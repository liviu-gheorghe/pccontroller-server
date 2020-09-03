package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import util.SystemInputController;

import java.awt.*;

public class ActionReceiveMouseMove implements Action {
    int deltaX;
    int deltaY;
    public ActionReceiveMouseMove(String content) {
        String[] args = content.split(",");
        deltaX = Integer.parseInt(args[0]);
        deltaY = Integer.parseInt(args[1]);
    }

    @Override
    public void execute() {
        try {
            SystemInputController.getInstance().moveMouseWithOffset(deltaX,deltaY);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_MOUSE_MOVE;
    }
}
