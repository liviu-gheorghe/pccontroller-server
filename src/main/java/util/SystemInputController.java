package util;

import java.awt.*;

public class SystemInputController extends Robot {

    private static SystemInputController INSTANCE;

    private SystemInputController() throws AWTException {
    }

    public static SystemInputController getInstance() throws AWTException {
        if (INSTANCE == null)
            INSTANCE = new SystemInputController();
        return INSTANCE;

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
