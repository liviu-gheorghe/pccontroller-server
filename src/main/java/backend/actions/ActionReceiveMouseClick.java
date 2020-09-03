package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import util.SystemInputController;

import java.awt.*;

public class ActionReceiveMouseClick implements Action {
    private final int clickType;
    public ActionReceiveMouseClick(String content) {
        clickType = Integer.parseInt(content);
    }

    @Override
    public void execute() {
        try {
            if(clickType == SystemInputController.DOUBLE_CLICK) {
                SystemInputController.getInstance().doubleClick();
            }
            else {
                SystemInputController.getInstance().mouseClick(clickType);
            }
        } catch (AWTException e) {
        }
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_MOUSE_CLICK;
    }
}
