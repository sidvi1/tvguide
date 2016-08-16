package ru.sidvi.tvguide.handlers.parameters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class ValidatorTest {

    private Validator validator;

    private String[] params;

    @Before
    public void setUp() throws Exception {
        validator = new Validator();
    }

    @Test
    public void onlyFourParameters() throws Exception {
        //given
        params = new String[]{
                "first",
                "on",
                "third",
                "today"
        };
        //when
        boolean actual = validator.validate(params);
        //then
        assertEquals(true, actual);
    }

    @Test
    public void shouldWork() throws Exception {
        //given
        params = new String[]{
                "first",
                "on",
                "third",
                "today",
                "fifth"
        };
        //when
        boolean actual = validator.validate(params);
        //then
        assertEquals(true, actual);
    }

    @Test
    public void testTomorrowParameter() throws Exception {
        //given
        params = new String[]{
                "first",
                "on",
                "third",
                "tomorrow",
                "fifth"
        };
        //when
        boolean actual = validator.validate(params);
        //then
        assertEquals(true, actual);
    }

    @Test
    public void tooManyParams() throws Exception {
        //given
        params = new String[]{
                "first",
                "on",
                "third",
                "tomorrow",
                "fifth",
                "sixth"
        };
        //when
        boolean actual = validator.validate(params);
        //then
        assertEquals(false, actual);
    }

    @Test
    public void tooLowParams() throws Exception {
        //given
        params = new String[]{
                "first",
                "on",
                "third"
        };
        //when
        boolean actual = validator.validate(params);
        //then
        assertEquals(false, actual);
    }

    @Test
    public void secondMustBeOn() throws Exception {
        //given
        params = new String[]{
                "first",
                "who",
                "third",
                "today"
        };
        //when
        boolean actual = validator.validate(params);
        //then
        assertEquals(false, actual);
    }

    @Test
    public void fourthMustBeTodayOrTomorrow() throws Exception {
        //given
        params = new String[]{
                "first",
                "on",
                "third",
                "fourth"
        };
        //when
        boolean actual = validator.validate(params);
        //then
        assertEquals(true, actual);
    }

}