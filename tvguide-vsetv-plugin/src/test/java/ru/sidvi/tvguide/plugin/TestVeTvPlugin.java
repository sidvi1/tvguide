package ru.sidvi.tvguide.plugin;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import ru.sidvi.tvguide.plugin.parser.VseTvParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by sidvi on 13.12.2015.
 */
public class TestVeTvPlugin {

    @Test
    public void testVseTVParser() throws IOException, ParseException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("vsetv_in.html");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream os = new PrintStream(bos);
        List<Event> events = new VseTvParser().parse(is);
        printVseTv(events, os);
        String actual = bos.toString("windows-1251");

        String expected = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("vsetv_out.txt"));

        is.close();
        os.close();

        assertEquals(expected, actual);
    }


    private static void printVseTv(List<Event> list, PrintStream os) {
        for (Event event : list) {
            os.println(event.getChannel() + " --- " + format(event.getDate()) + ": " + event.getName());
        }
    }

    private static String format(Calendar time) {
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        return format1.format(time.getTime());
    }

}
