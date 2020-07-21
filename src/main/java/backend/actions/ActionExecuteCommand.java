package backend.actions;

import backend.Action;
import backend.ReceivedActionsCodes;
import pccontroller.App;
import util.SystemInputController;
import util.XMLUserDataLoader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActionExecuteCommand implements Action {

    private String command;
    public ActionExecuteCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        new Thread(
                () -> {
                    String actionString = XMLUserDataLoader.getAction(command);
                    if(actionString == null) return;
                    String[] commandArgs = actionString.split("\\^");
                    if (commandArgs[0].equals("KEYPRESS")) {
                        try {
                            for (int i = 1; i < commandArgs.length; i++) {
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
                }
        ).start();
    }

    @Override
    public int getActionType() {
        return ReceivedActionsCodes.ACTION_EXECUTE_COMMAND;
    }
}
