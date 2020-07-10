package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ActionOpenLink implements Action {
    private String link;
    public ActionOpenLink(String link) {
        this.link = link;
    }

    @Override
    public void execute() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_OPEN_LINK_IN_BROWSER;
    }
}
