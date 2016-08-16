package ru.sidvi.tvguide.plugin.filters;

import org.junit.Before;
import org.junit.Test;
import ru.sidvi.tvguide.plugin.Event;

import static org.junit.Assert.*;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class GenrePredicateTest {

    private GenrePredicate tested;

    @Before
    public void setUp(){
        tested = new GenrePredicate("mus");
    }

    @Test
    public void shouldTrue() throws Exception {
        Event event = new Event();
        event.setGenre("Music");

        boolean actual = tested.evaluate(event);

        assertEquals(true, actual);
    }

    @Test
    public void shouldFalse() throws Exception {
        Event event = new Event();
        event.setGenre("Film");

        boolean actual = tested.evaluate(event);

        assertEquals(false, actual);
    }

    @Test
    public void testAny(){
        tested = new GenrePredicate("#");
        Event event = new Event();
        event.setGenre("Film name");

        boolean actual = tested.evaluate(event);

        assertEquals(true, actual);
    }
}