package ru.sidvi.tvguide.plugin.filters;

import org.junit.Before;
import org.junit.Test;
import ru.sidvi.tvguide.plugin.Event;

import static org.junit.Assert.*;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class NamePredicateTest {

    private NamePredicate tested;

    @Before
    public void setUp(){
        tested = new NamePredicate("movie");
    }

    @Test
    public void shouldTrue() throws Exception {
        Event event = new Event();
        event.setName("Best movie");

        boolean actual = tested.evaluate(event);

        assertEquals(true, actual);
    }

    @Test
    public void shouldFalse() throws Exception {
        Event event = new Event();
        event.setName("Film name");

        boolean actual = tested.evaluate(event);

        assertEquals(false, actual);
    }

    @Test
    public void testAny(){
        tested = new NamePredicate("#");
        Event event = new Event();
        event.setName("Film name");

        boolean actual = tested.evaluate(event);

        assertEquals(true, actual);
    }
}