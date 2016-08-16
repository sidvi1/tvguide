package ru.sidvi.tvguide.plugin.filters;

import org.junit.Before;
import org.junit.Test;
import ru.sidvi.tvguide.plugin.Event;

import static org.junit.Assert.*;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class ChannelPredicateTest {

    private ChannelPredicate tested;

    @Before
    public void setUp(){
        tested = new ChannelPredicate("tv");
    }

    @Test
    public void shouldTrue() throws Exception {
        Event event = new Event();
        event.setChannel("MTV Russia");

        boolean actual = tested.evaluate(event);

        assertEquals(true, actual);
    }

    @Test
    public void shouldFalse() throws Exception {
        Event event = new Event();
        event.setChannel("CNN");

        boolean actual = tested.evaluate(event);

        assertEquals(false, actual);
    }
}