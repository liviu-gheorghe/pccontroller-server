package backend;

import backend.actions.*;

import java.io.InputStream;

import static backend.ReceivedActionsCodes.*;

public class ActionFactory {
    public static Action createAction(int type, String content) {
        if(type==ACTION_RECEIVE_DEVICE_NAME) return new ActionReceiveDeviceName(content);
        if(type==ACTION_EXECUTE_COMMAND) return new ActionExecuteCommand(content);
        if(type==ACTION_OPEN_LINK_IN_BROWSER) return new ActionOpenLink(content);
        if(type==ACTION_UPDATE_CLIPBOARD_CONTENT) return new ActionUpdateClipboardContent(content);
        if(type == ACTION_RECEIVE_CONNECTION_REQUEST) return new ActionReceiveConnectionRequest(content);
        if(type==ACTION_RECEIVE_DEVICE_BATTERY_LEVEL) return new ActionReceiveBatteryLevel(content);
        if(type==ACTION_RECEIVE_DEVICE_OS_LEVEL) return new ActionReceiveDeviceOsLevel(content);
        if(type==ACTION_RECEIVE_DEVICE_VOLUME_LEVEL) return new ActionReceiveDeviceVolumeLevel(content);
        if(type==ACTION_RECEIVE_FILE_TRANSMISSION_INTENT) return new ActionReceiveFileTransmissionIntent(content);
        return null;
    }
    public static Action createAction(int type,InputStream inputStream) {
        if(type == ACTION_RECEIVE_FILE) return new ActionReceiveFile(inputStream);
        return null;
    }
}
