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
 * <b>ParametersHandler.java</b>: handles command line parameters to be
 * checked against a known pattern.
 */

// package definition
package ru.sidvi.tvguide.handlers;

// needed imports

import ru.sidvi.tvguide.Utils;
import ru.sidvi.tvguide.plugin.Day;
import ru.sidvi.tvguide.plugin.Query;
import org.apache.log4j.Logger;

/**
 * Handles command line parameters to be checked against a know pattern. These
 * parameters will be transformed into a query, which will be submitted to the
 * plugin implementation in order to fetch the desired information.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class ParametersHandler {
    // what we are looking
    private String what;

    // where we are looking
    private String where;

    // and when we are looking
    private String when;

    private String genre = "#";;


    // create a log instance
    private Logger log = Logger.getRootLogger();

    public ParametersHandler(String[] parameters) {
        what = parameters[0].toLowerCase();
        where = parameters[2];
        when = parameters[3].toLowerCase();
        if (parameters.length == 5) {
            genre = parameters[4];
        }
    }

    /**
     * Builds the query according to the provided parameters.
     *
     * @return A <code>Query</code> object.
     */
    public Query getQuery() {
        Query query = new Query();
        query.setChannel(where);
        query.setName(what);
        if (when.equals("today")) {
            query.setDay(Day.TODAY);
        } else {
            query.setDay(Day.TOMORROW);
        }
        query.setGenre(genre);

        return query;
    }

    /**
     * Gets the query in a humanized fashion, that is, in a human
     * readable format.
     *
     * @return A String containing the human readable query.
     */
    public String getHumanReadableQuery() {

        String name;
        String genre = "";
        String channel;

        if (what.equals("#")) {
            name = "every event";
        } else {
            name = "event contains \"" + what.toUpperCase() + "\"";
        }

        if (where.equals("#")) {
            channel = "every channel";
        } else {
            channel = "channel contains \"" + where.toUpperCase() + "\"";
        }

        if (this.genre != null) {
            if (this.genre.equals("#")) {
                genre = "any genre";
            } else {
                genre = "genre contains \"" + where.toUpperCase() + "\"";
            }
        }


        return "Searching for " + name + " in " + channel + " " + when.toLowerCase() + " with " + genre;
    }

}
