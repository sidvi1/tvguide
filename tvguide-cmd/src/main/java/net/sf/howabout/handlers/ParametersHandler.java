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
 * <b>ParametersHandler.java</b>: handles command line parameters to be
 * checked against a known pattern.
 */

// package definition
package net.sf.howabout.handlers;

// needed imports
import net.sf.howabout.plugin.Day;
import net.sf.howabout.plugin.Query;
import org.apache.log4j.Logger;

/**
 * Handles command line parameters to be checked against a know pattern. These
 * parameters will be transformed into a query, which will be submitted to the
 * plugin implementation in order to fetch the desired information.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class ParametersHandler {

    // parameters provided by the main class, usually args[]
    private String[] parameters;

    // what we are looking
    private String what;

    // where we are looking
    private String where;

    // and when we are looking
    private String when;

    // create a log instance
    private Logger log = Logger.getRootLogger();

    // this is a boolean array to be passed as a parameter
    // to the printer class to determine which columns
    // should be visible according to the provided parameters
    private boolean tableParameters[];

    /**
     * Constructor method. It basically handles the application parameters,
     * in this case, the argv[] attribute.
     * @param parameters
     */
    public ParametersHandler(String[] parameters) {

        // get the parameters
        this.parameters = parameters;

        // allocate the vector for the printer class parameters
        this.tableParameters = new boolean[4];
    }


    /**
     * Validates the parameters. Since this is a console application,
     * those parameters must be checked against a known command line
     * pattern.
     * @return True if parameters have been validated, or false
     * otherwise.
     */
    public boolean validate() {

        // set the default error message
        StringBuilder errormessage = new StringBuilder();
        errormessage.append("HowAbout expects exact 4 parameters:\n\n");
        errormessage.append("$ howabout WHAT on WHERE WHEN\n");
        errormessage.append("            |        |     |\n");
        errormessage.append("          genre  channel  day (today or tomorrow)\n\n");
        errormessage.append("When searching for sentences, you must escape the ");
        errormessage.append("parameters in double quotes, e.g.,\n\n");
        errormessage.append("$ howabout news on \"CNN in Spanish\" today\n\n");
        errormessage.append("You may also search using wildcards (#), e.g.,\n\n");
        errormessage.append("$ howabout soccer on # today");

        // First check: number of parameters
        if (parameters.length != 4) {

            // print the message error and return
            log.error("The number of parameters is incorrect. " + errormessage.toString());
            return false;
        }
        else {

            // parameters match, so lets work

            // first column always is shown
            tableParameters[0] = true;

            // get the first field
            what = parameters[0].toLowerCase();

            // if it's a wildcard
            if (what.equals("#")) {

                // show the third column
                tableParameters[3] = true;
            }
            else {

                // do not show the third column
                tableParameters[3] = false;
            }

            // the second parameter must be "ON"
            if (!parameters[1].toLowerCase().equals("on")) {

                // print the message error and return
                log.error("The syntax is incorrect. " + errormessage.toString());
                return false;
            }

            // get the third parameter
            where = parameters[2];

            // check if it's a wildcard
            if (where.equals("#")) {

                // if so, display the second column
                tableParameters[1] = true;
            }
            else {

                // do not display the second column
                tableParameters[1] = false;
            }

            // third column is always shown
            tableParameters[2] = true;

            // get the fourth parameter
            when = parameters[3].toLowerCase();

            // if this parameter is not equal to "TODAY" or "TOMORROW"
            if ((!when.equals("today")) && (!when.equals("tomorrow"))) {

                // print the message error and return
                log.error("The syntax is incorrect. " + errormessage.toString());
                return false;
            }

            // so everything worked just fine, return true
            return true;
            
        }
    }

    /**
     * Getter method for the table parameters.
     * @return A boolean array containing values to be passed to the printer
     * class in order to set which columns will be displayed.
     */
    public boolean[] getTableParameters() {

        // just return the attribute
        return tableParameters;
    }

    /**
     * Builds the query according to the provided parameters.
     * @return A <code>net.sf.howabout.plugin.Query</code> object.
     */
    public Query getQuery() {

        // create a new query
        Query query = new Query();

        // set the channel
        query.setChannel(where);

        // set the genre
        query.setGenre(what);

        // check if the parameter is "TODAY"
        if (when.equals("today")) {

            // set the day
            query.setDay(Day.TODAY);
        }
        else {
            // "TOMORROW" it is
            // set the day
            query.setDay(Day.TOMORROW);
        }

        // return the query
        return query;
    }

    /**
     * Gets the query in a humanized fashion, that is, in a human
     * readable format.
     * @return A String containing the human readable query.
     */
    public String getHumanReadableQuery() {

        // variables
        String genre;
        String channel;

        // check if it's a wildcard
        if (what.equals("#")) {

            // set the genre
            genre = "every genre";
        }
        else {

            // set the genre
            genre = "\"" + what.toUpperCase() + "\"";
        }

        // check if it's a wildcard
        if (where.equals("#")) {

            // set the channel
            channel = "every channel";
        }
        else {

            // set the channel
            channel = "\"" + where.toUpperCase() + "\"";
        }

        // return the string
        return "Searching for " + genre + " in " + channel + " " + when.toLowerCase() + ".";
    }

}
