package ru.sidvi.tvguide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 14.12.2015.
 */
public class Utils {
    /**
     * Convert the parameter to string and add a zero if necessary.
     * @param value The integer value.
     * @return A string containing the integer and a zero, if necessary.
     */
    public static String addZeroes(int value) {

        // convert string
        String newValue = String.valueOf(value);

        // check if a zero is necessary
        if ((value >= 0) && (value <= 9)) {
            // add a zero
            newValue = "0" + newValue;
        }

        // return string
        return newValue;
    }

    public static Calendar convert(String text) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfFull=new SimpleDateFormat("yyyy-MM-dd HH:mm");

        text = df.format(new Date()) + " " + text;

        Calendar dt = new GregorianCalendar();
        dt.setTime(dfFull.parse(text));
        return dt;
    }

    public static String formatColumn(int numberOfSpaces, String data) {
        return String.format("%1$-" + numberOfSpaces + "s", data);
    }
}
