package util;

import java.util.regex.Pattern;

public class IpAddressValidator {

    private static final String zeroTo255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private static final String IP_REGEXP = zeroTo255+"\\."+zeroTo255+"\\."+zeroTo255+"\\."+zeroTo255;
    private static final Pattern IP_PATTERN = Pattern.compile(IP_REGEXP);
    private static final String LOCAL_IP_REGEXP = "(^127\\.0\\.0\\.1)|(^10\\.)|(^172\\.1[6-9]\\.)|(^172\\.2[0-9]\\.)|(^172\\.3[0-1]\\.)|(^192\\.168\\.)";
    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile(LOCAL_IP_REGEXP);

    public static boolean isIpAddress(String input) {
        return IP_PATTERN.matcher(input).matches();
    }

    public static boolean isLocalIpAddress(String input) {
        return isIpAddress(input);
    }
}
