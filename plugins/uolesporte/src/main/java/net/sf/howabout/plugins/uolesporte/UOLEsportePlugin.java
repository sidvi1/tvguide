/**
 * \cond LICENSE
 * ********************************************************************
 * This is a conditional block for preventing the DoxyGen documentation
 * tool to include this license header within the description of each
 * source code file. If you want to include this block, please define
 * the LICENSE parameter into the provided DoxyFile.
 * ********************************************************************
 *
 * HowAbout - An online TV guide searcher
 * Copyright (c) 2011, Paulo Roberto Massa Cereda
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. Neither the name of the project's author nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ********************************************************************
 * End of the LICENSE conditional block
 * ********************************************************************
 * \endcond
 *
 * <b>UOLEsportePlugin.java</b>: provides methods for retrieving data from
 * UOL Esportes.
 */
// package definition
package net.sf.howabout.plugins.uolesporte;

// needed imports
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import net.sf.howabout.plugin.Event;
import net.sf.howabout.plugin.Query;
import net.sf.howabout.plugin.api.HowAboutPlugin;
import net.sf.howabout.plugin.Day;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Provides methods for retrieving data from UOL Esportes. It basically
 * implements the <code>net.sf.howabout.plugin.api.HowAboutPlugin</code>
 * interface.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class UOLEsportePlugin implements HowAboutPlugin {

    public List<Event> getEvents(Query query) {

        // get the current date
        GregorianCalendar date = new GregorianCalendar();

        // lookup string
        String lookup = "";

        // if search must be performed on the day next,
        // one day will be added to the current date
        if (query.getDay() == Day.TOMORROW) {

            // add one day to the current date
            date.add(Calendar.DATE, 1);
        }

        // this is a URL rewritting as required from UOL Esportes
        lookup = String.valueOf(date.get(Calendar.YEAR)) + "/" + addZeroes(date.get(Calendar.MONTH) + 1) + "/" + addZeroes(date.get(Calendar.DATE)) + "/";

        // events list
        ArrayList<Event> list = new ArrayList<Event>();

        // create a new event object
        Event event = new Event();

        try {

            // try to connect to the URL and get the page
            Document document = Jsoup.connect("http://esporte.uol.com.br/programacao-de-tv/data/" + lookup).get();

            // get all elements according to the provided tag
            Elements elements = document.getElementsByTag("tbody");

            // iterate elements
            for (Element element : elements) {

                // get the table row
                Elements items = element.getElementsByTag("tr");

                // iterate table
                for (Element item : items) {

                    // get the columns
                    Elements tabular = item.getElementsByTag("td");

                    // create a new event
                    event = new Event();

                    // set date
                    event.setDate(getDateFromString(date, tabular.get(0).text()));

                    // set genre
                    event.setGenre(tabular.get(1).text());

                    // set name
                    event.setName(tabular.get(2).text());

                    // set description
                    event.setDescription(tabular.get(2).text());

                    // set channel
                    event.setChannel(tabular.get(3).text());

                    // check if channel is not a wildcard
                    if (!query.getChannel().equals("#")) {

                        // check if genre is not a wildcard
                        if (!query.getGenre().equals("#")) {

                            // check if channel matches with query
                            if (event.getChannel().toLowerCase().equals(query.getChannel().toLowerCase())) {

                                // check if genre matches with query
                                if (event.getGenre().toLowerCase().equals(query.getGenre().toLowerCase())) {

                                    // add event to the list
                                    list.add(event);
                                }
                            }
                        } else {

                            // check if channel matches with query
                            if (event.getChannel().toLowerCase().equals(query.getChannel().toLowerCase())) {

                                // add event to the list
                                list.add(event);
                            }
                        }
                    } else {

                        // if genre is not a wildcard
                        if (!query.getGenre().equals("#")) {

                            // check if genre matches with query
                            if (event.getGenre().toLowerCase().equals(query.getGenre().toLowerCase())) {

                                // add event to the list
                                list.add(event);
                            }
                        } else {

                            // add event to the list
                            list.add(event);
                        }
                    }

                }
            }
        } catch (Exception e) {

            // if something happened, just clear everything
            list.clear();
        }

        //  return the list
        return list;

    }

    public String getPluginName() {
        return "HowAbout UOL Esporte Plugin";
    }

    public String getPluginVersion() {
        return "1.0";
    }

    public String getPluginAuthor() {
        return "Paulo Roberto Massa Cereda";
    }

    public String getPluginFullPackageName() {
        return "net.sf.howabout.plugins.uolesporte";
    }

    public String getPluginHelp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Convert the parameter to string and add a zero if necessary.
     * @param value The integer value.
     * @return A string containing the integer and a zero, if necessary.
     */
    private String addZeroes(int value) {

        // convert string
        String newValue = String.valueOf(value);

        // check if a zero is necessary
        if ((value >= 0) && (value <= 9)) {

            // add a zero
            newValue = "0" + newValue;
        }

        // return string
        return newValue;
    }

    /**
     * Gets the current date and the string with the time and returns a calendar
     * with the correct time set.
     * @param date The current date.
     * @param value The time represented as a string.
     * @return The current date with the correct time.
     */
    private GregorianCalendar getDateFromString(GregorianCalendar date, String value) {

        // split the time string into hours and minutes
        StringTokenizer token = new StringTokenizer(value, "h");

        // get the hours
        String hour = token.nextToken();

        // get the minutes
        String minute = token.nextToken();

        // create a new calendar based on the current date and current time
        GregorianCalendar newdate = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), Integer.parseInt(hour), Integer.parseInt(minute), 0);

        // return the new date
        return newdate;
    }
}
