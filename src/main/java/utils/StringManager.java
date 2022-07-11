package utils;

import java.sql.Timestamp;

public class StringManager {
    public static String getTimeStamp() {
        return String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
    }
}
