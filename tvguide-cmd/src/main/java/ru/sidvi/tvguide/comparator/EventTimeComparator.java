package ru.sidvi.tvguide.comparator;

import ru.sidvi.tvguide.plugin.Event;

import java.util.Comparator;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 14.12.2015.
 */
public class EventTimeComparator implements Comparator<Event> {

    @Override
    public int compare(Event event, Event t1) {
        return event.getDate().compareTo(t1.getDate());
    }
}
