package ru.sidvi.tvguide.printer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.sidvi.tvguide.Utils;
import ru.sidvi.tvguide.plugin.Event;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.sidvi.tvguide.TestUtils.fromResource;
import static ru.sidvi.tvguide.TestUtils.removeReturn;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class TablePrinterTest {

    private TablePrinter printer;

    private PrintStream out;
    private ByteArrayOutputStream actual;

    @Before
    public void setUp() throws Exception {
        List<Event> list = new ArrayList<Event>();
        Event e = new Event(Utils.convert("05:05"), "name1", "descr1", "genre1", "channel1");
        Event e1 = new Event(Utils.convert("15:30"), "name2", "descr2 len", "genre2 len", "channel12");
        list.add(e);
        list.add(e1);

        actual = new ByteArrayOutputStream();
        out = new PrintStream(actual);
        printer = new TablePrinter(list, out);
    }

    @Test
    public void testDraw() throws Exception {
        //given

        //when
        printer.draw();
        out.flush();

        //then
        assertEquals(removeReturn(fromResource("table_printer.txt")), removeReturn(actual.toString("UTF-8")));
    }
}