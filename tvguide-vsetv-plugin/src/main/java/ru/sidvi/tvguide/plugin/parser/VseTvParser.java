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

//        String eventChannel = null;
        EventExtractor extractor = new EventExtractor();
        Elements channelsEl = document.select(".main");
        for (Element e : channelsEl) {
            for (Element inE : e.getAllElements()) {

                if (TagUtils.isTag(inE, "div")) {
                    extractor.extractEventChannel(inE);

                    if (TagUtils.hasId(inE, "schedule_container")) {

                        Elements children = inE.getAllElements();
                        for (Element child : children) {

                            extractor.extractEventDate(child);
                            extractor.extractEventName(child);
                            list.addAll(extractor.createEventIfNeed());
                        }
                    }
                }
            }
        }
        return list;
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
        private boolean createEvent = false;
        private String eventChannel;

        public void reset() {
            eventDate = null;
            eventName = null;
            createEvent = false;
        }

        protected boolean isCreateEvent() {
            return createEvent;
        }

        protected void shouldCreateEvent() {
            createEvent = true;
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

        public Event buildEvent() {
            return buildEvent(eventChannel, eventDate, eventName);
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

        private void extractEventDate(Element child) {
            if (TagUtils.hasClass(child, "pasttime") || TagUtils.hasClass(child, "time") || TagUtils.hasClass(child, "onair")) {
                setEventDate(child.text().trim());
            }
        }

        private void extractEventName(Element child) {
            if (TagUtils.hasClass(child, "pastprname2") || TagUtils.hasClass(child, "prname2")) {
                setEventName(child.text().trim());
                shouldCreateEvent();
            }
        }

        private ArrayList<Event> createEventIfNeed() {
            ArrayList<Event> list = new ArrayList<Event>();
            if (isCreateEvent()) {
                list.add(buildEvent());
                reset();
            }
            return list;
        }

        private void extractEventChannel(Element inE) {
            if (TagUtils.hasClass(inE, "chname")) {
                setEventChannel(inE.select(".channeltitle").text().trim());
            }
        }
    }

}
