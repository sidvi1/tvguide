package ru.sidvi.tvguide.handlers;

import org.junit.Test;
import ru.sidvi.tvguide.plugin.Day;
import ru.sidvi.tvguide.plugin.Query;

import static org.junit.Assert.assertEquals;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class QueryFactoryTest {

    private QueryFactory tested;

    @Test
    public void testToday() throws Exception {
        Query expected = new Query("name", "Channel", Day.TODAY);
        String[] params = {"name", "on", "Channel", "today"};
        tested = new QueryFactory(params);

        Query actual = tested.create();

        assertEquals(expected, actual);
        assertEquals(Query.DEFAULT_VALUE, actual.getGenre());
    }

    @Test
    public void testTomorrow() throws Exception {
        Query expected = new Query("name", "Channel", Day.TOMORROW);
        String[] params = {"name", "on", "Channel", "tomorrow"};
        tested = new QueryFactory(params);

        Query actual = tested.create();

        assertEquals(expected, actual);
        assertEquals(Query.DEFAULT_VALUE, actual.getGenre());
    }

    @Test
    public void testTodayLong() throws Exception {
        Query expected = new Query("name", "Channel", Day.TODAY, "Music");
        String[] params = {"name", "on", "Channel", "today", "Music"};
        tested = new QueryFactory(params);

        Query actual = tested.create();

        assertEquals(expected, actual);
    }

    @Test
    public void testTomorrowLong() throws Exception {
        Query expected = new Query("name", "Channel", Day.TOMORROW, "Music");
        String[] params = {"name", "on", "Channel", "tomorrow", "Music"};
        tested = new QueryFactory(params);

        Query actual = tested.create();

        assertEquals(expected, actual);
    }


}