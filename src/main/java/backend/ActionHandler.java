package backend;

import org.xml.sax.SAXException;
import pccontroller.App;
import pccontroller.Controller;
import util.SystemInputController;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class ActionHandler {

    private static final Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static ActionHandler INSTANCE = null;
    private Document actionsDocument = null;
    private final Controller controller;

    private ActionHandler(Controller controller) {
        loadXMLActionsDocument();
        this.controller = controller;
    }

    private void loadXMLActionsDocument() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            actionsDocument = documentBuilder.parse(getClass().getResource("/data/actions.xml").toExternalForm());
            actionsDocument.getDocumentElement().normalize();
        }
        catch(ParserConfigurationException | SAXException | IOException exception) {
            exception.printStackTrace();
        }
    }

    private String getAction(String actionName) {
        NodeList matchingTagActionGroups = actionsDocument.getElementsByTagName(actionName);
        if(matchingTagActionGroups.getLength()==0) return null;
        NodeList actionsList = matchingTagActionGroups.item(0).getChildNodes();
        int actionNodeCount = 0;
        for(int i=0;i<actionsList.getLength();i++) {
            if(actionsList.item(i).getNodeName().equals("action"))
                actionNodeCount++;
            if(actionNodeCount-1 == App.OS_ID) return actionsList.item(i).getTextContent();
        }
        return null;
    }


    public static void setInstance (Controller controller) {
        INSTANCE = new ActionHandler(controller);
    }

    public static ActionHandler getInstance() {
        return INSTANCE;
    }

    public String getSystemClipboardContent() {
        try {
            return Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

    public void handleAction(int actionType, String action) {
        System.out.println(actionType + " " + action);
        switch (actionType) {
            case 0:
                try {
                    controller.getDashboardPaneController().getConnectedDeviceInfo().setText(action);
                    controller.getDashboardPaneController().getCloseConnectionButton().setVisible(true);
                    controller.getDashboardPaneController().getConnectionInstructions().setText("");
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                }
                controller.getDevicePaneController().getDeviceFullName().setText(action);
                controller.showPaneButton(1);
                controller.getDashboardPaneController().setPhoneImage();
                App.CONNECTED_DEVICE_NAME = action;
                break;
            case 1:
                //Execute action

                String actionString = getAction(action);
                if(actionString == null) return;
                String[] commandArgs = actionString.split("\\^");
                if (commandArgs[0].equals("KEYPRESS")) {
                    try {
                        for (int i = 1; i < commandArgs.length; i++) {
                            System.out.println("Sending keypress : " + commandArgs[i]);
                            SystemInputController.getInstance().sendKeystroke(Integer.parseInt(commandArgs[i]));
                        }
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                } else if (commandArgs[0].equals("KEYCOMBINATION")) {
                    try {
                        int[] keys = new int[commandArgs.length - 1];
                        for (int i = 1; i < commandArgs.length; i++) keys[i - 1] = Integer.parseInt(commandArgs[i]);
                        SystemInputController.getInstance().sendKeyCombination(keys);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(App.OS_ID == 0) {
                        try {
                            Process p = Runtime.getRuntime().exec(commandArgs);
                            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                            String line;
                            while((line = br.readLine()) != null) {
                                System.out.println(line);
                            }
                            br.close();
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        ProcessBuilder pb = new ProcessBuilder(commandArgs);
                        try {
                            pb.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 2:
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI(action));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (!getSystemClipboardContent().equals(action)) {
                    StringSelection stringSelection = new StringSelection(action);
                    systemClipboard.setContents(stringSelection, null);
                }
                break;
            case 4:
                break;
            case 5:
                try {
                    controller.getDevicePaneController().getBatteryLevel().setText(action);
                }
                catch (NullPointerException e) {
                    //TODO
                }
                break;
            case 6:
                try {
                    controller.getDevicePaneController().getOsVersion().setText(action);
                }
                catch (NullPointerException e) {
                    //TODO
                }
                break;
            case 7:
                try {
                    controller.getDevicePaneController().getVolumeLevel().setText(action);
                }
                catch (NullPointerException e) {
                    //TODO
                }
                break;
            case 8:
                //TODO
            default:
                break;
        }
    }
}