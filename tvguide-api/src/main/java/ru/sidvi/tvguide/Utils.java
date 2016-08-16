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

    public static final StringBuilder ERROR_MESSAGE = new StringBuilder();

    static {
        Utils.ERROR_MESSAGE.append("TvGuide expects 4 or 5 parameters:\n\n");
        Utils.ERROR_MESSAGE.append("tvguide WHAT on WHERE WHEN GENRE\n\n");
        Utils.ERROR_MESSAGE.append("tvguide # on MTV today music \n\n");
        Utils.ERROR_MESSAGE.append("When searching for sentences, you must escape the ");
        Utils.ERROR_MESSAGE.append("parameters in double quotes, e.g.,\n\n");
        Utils.ERROR_MESSAGE.append("You may also search using wildcards (#), e.g.,\n\n");
    }

    /**
     *
     * @param val - only hours and minutes in format HH:mm
     * @return
     * @throws ParseException
     */
    public static Calendar convert(String val) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        val = df.format(new Date()) + " " + val;

        Calendar dt = new GregorianCalendar();
        dt.setTime(dfFull.parse(val));
        return dt;
    }

    public static String rightPadUntil(int numberOfSpaces, String data) {
        return String.format("%1$-" + numberOfSpaces + "s", data);
    }

    public static void printErrorMessage(String s) {
        System.out.println(s + "\n\n" + ERROR_MESSAGE.toString());
    }
}
