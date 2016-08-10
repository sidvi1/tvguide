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
import java.util.Date;
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

        String eventChannel = null;
        Elements channelsEl = document.select(".main");
        for (Element e : channelsEl) {
            for (Element inE : e.getAllElements()) {
                if (inE.tagName().equals("div")) {


                    if (inE.hasClass("chname")) {
                        eventChannel = inE.select(".channeltitle").text().trim();
                    }

                    if (inE.id().equals("schedule_container")) {
                        Elements children = inE.getAllElements();

                        EventExtractor extractor = new EventExtractor();
                        extractor.setEventChannel(eventChannel);
                        for (Element child : children) {

                            if (child.hasClass("pasttime") || child.hasClass("time") || child.hasClass("onair")) {
                                extractor.setEventDate(child.text().trim());
                            }
                            if (child.hasClass("pastprname2") || child.hasClass("prname2")) {
                                extractor.setEventName(child.text().trim());
                                extractor.shouldCreateEvent();
                            }

                            if (extractor.isCreateEvent()) {
                                list.add(extractor.buildEvent());
                                extractor.reset();
                            }
                        }
                    }
                }
            }
        }
        return list;
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

        public boolean isCreateEvent() {
            return createEvent;
        }

        public void shouldCreateEvent() {
            createEvent = true;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public void setEventChannel(String eventChannel) {
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
    }

}
