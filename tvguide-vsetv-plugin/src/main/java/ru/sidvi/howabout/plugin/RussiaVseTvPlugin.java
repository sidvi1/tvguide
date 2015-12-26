/**
 * \cond LICENSE
 * ********************************************************************
 * This is a conditional block for preventing the DoxyGen documentation
 * tool to include this license header within the description of each
 * source code file. If you want to include this block, please define
 * the LICENSE parameter into the provided DoxyFile.
 * ********************************************************************
 * <p/>
 * HowAbout - An online TV guide searcher
 * Copyright (c) 2011, Paulo Roberto Massa Cereda
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p/>
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 * <p/>
 * 3. Neither the name of the project's author nor the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * <p/>
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
 * <p/>
 * ********************************************************************
 * End of the LICENSE conditional block
 * ********************************************************************
 * \endcond
 * <p/>
 * <b>UOLEsportePlugin.java</b>: provides methods for retrieving data from
 * UOL Esportes.
 */
// package definition
package ru.sidvi.howabout.plugin;


import net.sf.howabout.Utils;
import net.sf.howabout.plugin.Day;
import net.sf.howabout.plugin.Event;
import net.sf.howabout.plugin.Query;
import net.sf.howabout.plugin.api.HowAboutPlugin;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import ru.sidvi.howabout.plugin.filters.ChannelPredicate;
import ru.sidvi.howabout.plugin.filters.GenrePredicate;
import ru.sidvi.howabout.plugin.filters.NamePredicate;

import java.io.InputStream;
import java.net.*;
import java.util.*;

/**
 * Provides methods for retrieving data from UOL Esportes. It basically
 * implements the <code>net.sf.howabout.plugin.api.HowAboutPlugin</code>
 * interface.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class RussiaVseTvPlugin implements HowAboutPlugin {

    private static List<Event> parse(String lookup) {
        List<Event> list = new ArrayList<Event>();

        try {
            String url = "http://www.vsetv.com/schedule_package_rubase_day_" + lookup + ".html";

            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36");

            int responseCode = c.getResponseCode();

            if (responseCode == 200) {
                InputStream inputStream = c.getInputStream();
                list = new VseTvParser(inputStream).parse();
            }

            c.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String formatDate(GregorianCalendar date) {
        return String.valueOf(date.get(Calendar.YEAR)) + "-" + Utils.addZeroes(date.get(Calendar.MONTH) + 1) + "-" + Utils.addZeroes(date.get(Calendar.DATE));
    }

    public List<Event> getEvents(Query query) {
        GregorianCalendar lookupDate = new GregorianCalendar();
        if (query.getDay() == Day.TOMORROW) {
            lookupDate.add(Calendar.DATE, 1);
        }
        String lookup = formatDate(lookupDate);

        List<Event> parsed = parse(lookup);

        CollectionUtils.filter(parsed, new AllPredicate(new Predicate[]{
                new ChannelPredicate(query.getChannel()),
                new GenrePredicate(query.getGenre()),
                new NamePredicate(query.getName())
        }));

        return parsed;
    }

    public String getPluginName() {
        return "www.vsetv.com Plugin";
    }

    public String getPluginVersion() {
        return "1.0";
    }

    public String getPluginAuthor() {
        return "Vitaly Sidorov";
    }

    public String getPluginFullPackageName() {
        return "ru.sidvi.howabout.plugin";
    }

    public String getPluginHelp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Gets the current date and the string with the time and returns a calendar
     * with the correct time set.
     *
     * @param date  The current date.
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
