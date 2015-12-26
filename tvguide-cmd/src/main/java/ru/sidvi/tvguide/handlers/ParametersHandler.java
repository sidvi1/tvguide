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

    private static final StringBuilder ERROR_MESSAGE = new StringBuilder();

    static {
        ERROR_MESSAGE.append("TvGuide expects 4 or 5 parameters:\n\n");
        ERROR_MESSAGE.append("tvguide WHAT on WHERE WHEN GENRE\n\n");
        ERROR_MESSAGE.append("tvguide # on MTV today music \n\n");
        ERROR_MESSAGE.append("When searching for sentences, you must escape the ");
        ERROR_MESSAGE.append("parameters in double quotes, e.g.,\n\n");
        ERROR_MESSAGE.append("You may also search using wildcards (#), e.g.,\n\n");
    }


    // parameters provided by the main class, usually args[]
    private String[] parameters;

    // what we are looking
    private String what;

    // where we are looking
    private String where;

    // and when we are looking
    private String when;

    private String genre;

    // create a log instance
    private Logger log = Logger.getRootLogger();


    /**
     * Constructor method. It basically handles the application parameters,
     * in this case, the argv[] attribute.
     *
     * @param parameters
     */
    public ParametersHandler(String[] parameters) {
        this.parameters = parameters;
    }


    /**
     * Validates the parameters. Since this is a console application,
     * those parameters must be checked against a known command line
     * pattern.
     *
     * @return True if parameters have been validated, or false
     * otherwise.
     */
    public boolean validate() {

        // set the default error message


        if (parameters.length < 4 || parameters.length > 5) {
            printErrorMessage("The number of parameters is incorrect. ");
            return false;
        } else {
            what = parameters[0].toLowerCase();

            // the second parameter must be "ON"
            if (!parameters[1].toLowerCase().equals("on")) {
                printErrorMessage("The syntax is incorrect. ");
                return false;
            }

            where = parameters[2];

            when = parameters[3].toLowerCase();

            // if this parameter is not equal to "TODAY" or "TOMORROW"
            if ((!when.equals("today")) && (!when.equals("tomorrow"))) {
                printErrorMessage("The syntax is incorrect. ");
                return false;
            }


            if (parameters.length == 5) {
                genre = parameters[4];
            } else {
                genre = "#";
            }

            return true;
        }
    }

    private void printErrorMessage(String s) {
        System.out.println(s + ERROR_MESSAGE.toString());
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
