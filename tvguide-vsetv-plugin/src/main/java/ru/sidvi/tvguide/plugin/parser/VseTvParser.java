package ru.sidvi.tvguide.plugin.parser;

import ru.sidvi.tvguide.Utils;
import ru.sidvi.tvguide.plugin.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sidvi on 14.12.2015.
 */
public class VseTvParser implements Parser {
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
            Document document = Jsoup.parse(is, "windows-1251", "http://www.vsetvcom/");

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

                            String eventDate = null;
                            String eventName = null;
                            boolean shouldCreateEvent = false;
                            for (Element child : children) {

                                if (child.hasClass("pasttime") || child.hasClass("time") || child.hasClass("onair")) {
                                    eventDate = child.text().trim();
                                }
                                if (child.hasClass("pastprname2") || child.hasClass("prname2")) {
                                    eventName = child.text().trim();
                                    shouldCreateEvent = true;
                                }

                                if (shouldCreateEvent) {
                                    Event event = new Event();
                                    event.setChannel(eventChannel);
                                    event.setDate(Utils.convert(eventDate));
                                    event.setName(eventName);
                                    list.add(event);
                                    shouldCreateEvent = false;

                                    eventDate = null;
                                    eventName = null;
                                }
                            }
                        }


                    }
                }

            }
        } catch (Exception e) {
            parseSuccess = false;
            exception = e;
        }
        return list;
    }

}
