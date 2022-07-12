package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringManager {

    public static String generateStringDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss z");

        return formatter.format(date);
    }
}
