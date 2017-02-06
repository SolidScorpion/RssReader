package com.example.antony.rssreader.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class DateUtils {
    private static final String SOURCE_PATTERN = "EEE, d MMM yyyy HH:mm:ss z";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(SOURCE_PATTERN);
    public static int compareDates(String dateOne, String dateTwo) {
        try {
            Date fist = DATE_FORMAT.parse(dateOne);
            Date second = DATE_FORMAT.parse(dateTwo);
            return fist.compareTo(second);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
