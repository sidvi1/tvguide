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
package ru.sidvi.tvguide.plugin;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sidvi.tvguide.plugin.api.Plugin;
import ru.sidvi.tvguide.plugin.filters.ChannelPredicate;
import ru.sidvi.tvguide.plugin.filters.GenrePredicate;
import ru.sidvi.tvguide.plugin.filters.NamePredicate;
import ru.sidvi.tvguide.plugin.net.DownloadExecutor;
import ru.sidvi.tvguide.plugin.net.VseTvDownloadAction;
import ru.sidvi.tvguide.plugin.parser.VseTvParser;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Plugin for http://www.vsetvcom/.
 *
 * @author Vitaly Sidorov (mail@vitaly-sidorov.com)
 */
public class RussiaVseTvPlugin implements Plugin {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private Logger logger = LoggerFactory.getLogger(RussiaVseTvPlugin.class);

    @Override
    public List<Event> getEvents(Query query) {
        String lookup = createDateLookup(query);
        List<Event> parsed = parseForDate(lookup);

        CollectionUtils.filter(parsed, new AllPredicate(new Predicate[]{
                new ChannelPredicate(query.getChannel()),
                new GenrePredicate(query.getGenre()),
                new NamePredicate(query.getName())
        }));

        return parsed;
    }

    private String createDateLookup(Query query) {
        GregorianCalendar lookupDate = new GregorianCalendar();
        if (query.getDay() == Day.TOMORROW) {
            lookupDate.add(Calendar.DATE, 1);
        }
        return df.format(lookupDate.getTime());
    }

    private List<Event> parseForDate(String lookup) {
        String url = "http://www.vsetv.com/schedule_package_rubase_day_" + lookup + ".html";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36");

        printRequestDebug(url, headers);

        DownloadExecutor executor = new DownloadExecutor(url, headers);
        executor.execute(new VseTvDownloadAction(new VseTvParser()));
        List<Event> eventList = executor.getDownloaded();

        logger.debug("Downloaded events: " + eventList.size());

        return eventList;
    }

    private void printRequestDebug(String url, Map<String, String> headers) {
        logger.debug("Download url: " + url);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            logger.debug("Download header: " + e.getKey() + "=" + e.getValue());
        }
    }

    @Override
    public String getPluginName() {
        return "www.tvguide.com Plugin";
    }

    @Override
    public String getPluginVersion() {
        return "1.0";
    }

    @Override
    public String getPluginAuthor() {
        return "Vitaly Sidorov";
    }

    @Override
    public String getPluginFullPackageName() {
        return "ru.sidvi.tvguide.plugin";
    }

    @Override
    public String getPluginHelp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
