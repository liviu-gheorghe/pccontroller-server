package util;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardManager {

    public static String getSystemClipboardContent() {
        try {
            return Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
        }
        catch(IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
            return null;
        }
    }
}
