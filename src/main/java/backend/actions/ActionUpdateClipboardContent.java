package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ActionUpdateClipboardContent implements Action {

    private static final Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private String clipboardContent;

    public ActionUpdateClipboardContent(String clipboardContent) {
        this.clipboardContent = clipboardContent;
    }

    private String getSystemClipboardContent() {
        try {
            return Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

    @Override
    public void execute() {
        new Thread(
                () -> {
                    if (!getSystemClipboardContent().equals(clipboardContent)) {
                        StringSelection stringSelection = new StringSelection(clipboardContent);
                        systemClipboard.setContents(stringSelection, null);
                    }
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_UPDATE_CLIPBOARD_CONTENT;
    }
}
