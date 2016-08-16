package ru.sidvi.tvguide.comparator;

import org.junit.Test;
import ru.sidvi.tvguide.Utils;
import ru.sidvi.tvguide.plugin.Event;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class EventTimeComparatorTest {

    private EventTimeComparator tested;

    @Test
    public void testCompare() throws Exception {
        tested = new EventTimeComparator();
        Event e1 = new Event();
        Event e2 = new Event();
        e1.setDate(Utils.convert("12:30"));
        e2.setDate(Utils.convert("13:00"));

        int actual = tested.compare(e1, e2);

        assertEquals(-1, actual);
    }
}