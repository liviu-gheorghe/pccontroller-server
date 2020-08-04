package backend.actions;

import backend.Action;
import backend.DispatchedActionsCodes;
import backend.ReceivedActionsCodes;
import util.SystemInputController;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ActionReceiveKeyboardInput implements Action {
    private String content;
    private String charToWrite;
    private boolean backspace = false;
    public ActionReceiveKeyboardInput(String content) {
        this.content = content;
    }

    @Override
    public void execute() {
        if(content == null || content.length() == 0)
            return;
        int type = content.charAt(0);
        if(type == 'a') {
            // action
            try {
                SystemInputController.getInstance().sendKeystroke(KeyEvent.VK_BACK_SPACE,0);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else if(type == 'c') {
            //char
            try {
                for(int i=1;i<content.length();i++) {
                    int keycode = KeyEvent.getExtendedKeyCodeForChar(content.charAt(i));
                    SystemInputController.getInstance().sendKeystroke(keycode,0);
                }

            } catch(AWTException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_RECEIVE_KEYBOARD_INPUT;
    }
}
