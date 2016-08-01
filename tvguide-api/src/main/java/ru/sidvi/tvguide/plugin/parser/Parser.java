package ru.sidvi.tvguide.plugin.parser;

import ru.sidvi.tvguide.plugin.Event;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 26.12.2015.
 */
public interface Parser {
    List<Event> parse(InputStream is);
}
