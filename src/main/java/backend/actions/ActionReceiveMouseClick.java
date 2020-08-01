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
        new Thread(
                () -> {
                    try {
                        if(clickType == SystemInputController.DOUBLE_CLICK) {
                            System.out.println("Executing double click");
                            SystemInputController.getInstance().doubleClick();
                        }
                        else {
                            System.out.printf("Executing click on button %d\n", clickType);
                            SystemInputController.getInstance().mouseClick(clickType);
                        }
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_MOUSE_CLICK;
    }
}
