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
