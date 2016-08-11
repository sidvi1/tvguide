package ru.sidvi.tvguide.plugin;

import org.junit.Test;
import ru.sidvi.tvguide.plugin.parser.VseTvParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static ru.sidvi.tvguide.TestUtils.fromResource;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 13.12.2015.
 */
public class TestVeTvPlugin {

    private static void printVseTv(List<Event> list, PrintWriter os) {
        for (Event event : list) {
            os.println(event.getChannel() + " --- " + format(event.getDate()) + ": " + event.getName());
        }
    }

    private static String format(Calendar time) {
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        return format1.format(time.getTime());
    }

    @Test
    public void testVseTVParser() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        PrintWriter os = new PrintWriter(writer);

        InputStream is = getClass().getClassLoader().getResourceAsStream("vsetv_in.html");
        List<Event> events = new VseTvParser().parse(is);

        printVseTv(events, os);
        String actual = writer.getBuffer().toString();

        String expected = fromResource("vsetv_out.txt");

        is.close();
        os.close();

        assertEquals(expected, actual);
    }

}
