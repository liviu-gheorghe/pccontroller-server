package util;

import javafx.util.Pair;

import java.awt.*;
import java.awt.event.InputEvent;

public class SystemInputController extends Robot {

    private static SystemInputController INSTANCE;
    public static final int LEFT_CLICK = 1;
    public static final int MIDDLE_CLICK = 2;
    public static final int RIGHT_CLICK = 3;
    public static final int DOUBLE_CLICK = 4;


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
            case MIDDLE_CLICK:
                mask = InputEvent.BUTTON2_DOWN_MASK;
                break;
            case RIGHT_CLICK:
                mask = InputEvent.BUTTON3_DOWN_MASK;
                break;
            default:
                mask = InputEvent.BUTTON1_DOWN_MASK;
        }
        this.mousePress(mask);
        this.mouseRelease(mask);
    }


    public void doubleClick() {
        int mask = InputEvent.BUTTON1_DOWN_MASK;
        for(int i=0;i<2;i++) {
            this.mousePress(mask);
            this.mouseRelease(mask);
        }
    }


    public Point getPointerCoordinates() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public void moveMouseWithOffset(int deltaX,int deltaY) {
        Point p = getPointerCoordinates();
        double posX = p.getX() + deltaX;
        double posY = p.getY() + deltaY;
        mouseMove((int) posX,(int) posY);
    }

    public void sendKeystroke(int keycode) {
        this.keyPress(keycode);
        this.keyRelease(keycode);
        this.delay(100);
    }

    public void sendKeystroke(int keycode,int delay) {
        this.keyPress(keycode);
        this.keyRelease(keycode);
        this.delay(delay);
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
