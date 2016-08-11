package ru.sidvi.tvguide.plugin.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sidvi.tvguide.Utils;
import ru.sidvi.tvguide.plugin.Event;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 14.12.2015.
 */
public class VseTvParser implements Parser {
    public static final String SITE_CHARSET = "windows-1251";
    public static final String SITE_URL = "http://www.vsetvcom/";
    private Exception exception;

    public boolean isParseSuccess() {
        return parseSuccess;
    }

    public Exception getException() {
        return exception;
    }

    private boolean parseSuccess = true;

    public ArrayList<Event> parse(InputStream is) {
        ArrayList<Event> list = new ArrayList<Event>();

        try {
            list.addAll(tryToParse(is));
        } catch (Exception e) {
            parseSuccess = false;
            exception = e;
            e.printStackTrace();
        }
        return list;
    }

    private ArrayList<Event> tryToParse(InputStream is) throws IOException, ParseException {
        ArrayList<Event> list = new ArrayList<Event>();
        Document document = Jsoup.parse(is, SITE_CHARSET, SITE_URL);

        EventExtractor extractor = new EventExtractor();
        LevelCounter counter = new LevelCounter();

        Elements channelsEl = document.select(".main");
        walkElements(extractor, channelsEl, counter);
        list.addAll(extractor.getEvents());
        return list;
    }

    private void walkElements(EventExtractor extractor, Elements elements, LevelCounter counter) {
        counter.inc();
        for (Element e : elements) {
            if (counter.getLevel() == 1) {
                walkElements(extractor, e.getAllElements(), counter);
            }

            if (counter.getLevel() == 2 && TagUtils.isTag(e, "div") && TagUtils.hasClass(e, "chname")) {
                extractor.setEventChannel(e.select(".channeltitle").text().trim());
            }

            if (counter.getLevel() == 2 && TagUtils.isTag(e, "div") && TagUtils.hasId(e, "schedule_container")) {
                walkElements(extractor, e.getAllElements(), counter);
            }

            if (counter.getLevel() == 3 && (TagUtils.hasClass(e, "pasttime") || TagUtils.hasClass(e, "time") || TagUtils.hasClass(e, "onair"))) {
                extractor.setEventDate(e.text().trim());
            }

            if (counter.getLevel() == 3 && (TagUtils.hasClass(e, "pastprname2") || TagUtils.hasClass(e, "prname2"))) {
                extractor.setEventName(e.text().trim());
                extractor.createEvent();
            }
        }
        counter.decr();
    }


    private class LevelCounter {
        private int level = 0;

        private void inc() {
            level++;
        }

        private void decr() {
            level--;
        }

        public int getLevel() {
            return level;
        }
    }

    private static class TagUtils {

        private static boolean hasId(Element inE, String value) {
            return inE.id().equals(value);
        }

        private static boolean hasClass(Element inE, String chname) {
            return inE.hasClass(chname);
        }

        private static boolean isTag(Element inE, String div) {
            return inE.tagName().equals(div);
        }
    }

    private static class EventExtractor {
        private String eventDate = null;
        private String eventName = null;
        private String eventChannel;
        private List<Event> events = new ArrayList<Event>();

//        public int getLevel() {
//            return level;
//        }
//
//        private void increaseLevel(){
//            level ++;
//        }

        public void reset() {
            eventDate = null;
            eventName = null;
        }

        public void createEvent() {
            events.add(buildEvent(eventChannel, eventDate, eventName));
            reset();
        }

        protected void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        protected void setEventName(String eventName) {
            this.eventName = eventName;
        }

        protected void setEventChannel(String eventChannel) {
            this.eventChannel = eventChannel;
        }

        private Event buildEvent(String eventChannel, String eventDate, String eventName) {
            Calendar date = getDefaultDateIfNotParsed(eventDate);

            Event event = new Event();
            event.setChannel(eventChannel);
            event.setDate(date);
            event.setName(eventName);
            return event;
        }

        private Calendar getDefaultDateIfNotParsed(String eventDate) {
            Calendar date = null;
            try {
                date = Utils.convert(eventDate);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new GregorianCalendar();
            }
            return date;
        }

        public List<Event> getEvents() {
            return events;
        }
    }
}
