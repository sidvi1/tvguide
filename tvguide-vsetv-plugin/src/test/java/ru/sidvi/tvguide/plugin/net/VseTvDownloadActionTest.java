package ru.sidvi.tvguide.plugin.net;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sidvi.tvguide.plugin.Event;
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
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class VseTvDownloadActionTest {

    private VseTvDownloadAction tested;

    private InputStream is;
    private StringWriter writer;

    @Before
    public void setUp() throws Exception {
        tested = new VseTvDownloadAction(new VseTvParser());
        writer = new StringWriter();
    }

    @Test
    public void testVseTVParser() throws IOException, ParseException {
        is = getResourceAsStream("vsetv_in.html");

        List<Event> events = tested.download(is);
        printVseTv(events, writer);
        String actual = writer.getBuffer().toString();

        String expected = fromResource("vsetv_out.txt");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws IOException {
        writer.close();
        is.close();
    }

    private InputStream getResourceAsStream(String file) {
        return getClass().getClassLoader().getResourceAsStream(file);
    }

    private static void printVseTv(List<Event> list, StringWriter writer) {
        for (Event event : list) {
            writer.append(event.getChannel())
                    .append(" --- ")
                    .append(format(event.getDate()))
                    .append(": ")
                    .append(event.getName())
                    .append(System.lineSeparator());
        }
    }

    private static String format(Calendar time) {
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        return format1.format(time.getTime());
    }
}