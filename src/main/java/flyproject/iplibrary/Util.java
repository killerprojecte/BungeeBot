package flyproject.iplibrary;

import flyproject.iplibrary.database.IPData;
import flyproject.iplibrary.database.SQLData;

public class Util {
    public static long getIPLong(String ip) {
        String[] items = ip.split("\\.");
        return Long.parseLong(items[0]) << 24 | Long.parseLong(items[1]) << 16 | Long.parseLong(items[2]) << 8 | Long.parseLong(items[3]);
    }

    public static String getIPString(long ip) {
        return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
    }
}
