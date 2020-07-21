package util;

import java.awt.*;
import java.awt.event.InputEvent;

public class SystemInputController extends Robot {

    private static SystemInputController INSTANCE;

    private SystemInputController() throws AWTException {
    }

    public static SystemInputController getInstance() throws AWTException {
        if (INSTANCE == null)
            INSTANCE = new SystemInputController();
        return INSTANCE;


    }



    public void mouseClick(int button) {


        int mask;
        switch(button) {
            case 2:
                mask = InputEvent.BUTTON2_DOWN_MASK;
                break;
            case 3:
                mask = InputEvent.BUTTON3_DOWN_MASK;
                break;
            default:
                mask = InputEvent.BUTTON1_DOWN_MASK;
        }

        this.mousePress(mask);
        this.mouseRelease(mask);
    }

    public void sendKeystroke(int keycode) {
        this.keyPress(keycode);
        this.keyRelease(keycode);
        this.delay(100);
    }

    public void sendKeyCombination(int[] keys) {
        for (int i=0;i<=keys.length-1;i++) {
            this.keyPress(keys[i]);
            this.delay(20);
        }
        for (int i=keys.length-1;i>=0;i--) {
            this.keyRelease(keys[i]);
            this.delay(20);
        }
    }
}
