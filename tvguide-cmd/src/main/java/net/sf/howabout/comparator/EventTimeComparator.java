package net.sf.howabout.comparator;

import net.sf.howabout.plugin.Event;

import java.util.Comparator;

/**
 * Created by sidvi on 14.12.2015.
 */
public class EventTimeComparator implements Comparator<Event> {
    public int compare(Event event, Event t1) {
        return event.getDate().compareTo(t1.getDate());
    }
}
