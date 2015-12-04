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
 * <b>TablePrinter.java</b>: provides a method for drawing a TV Guide events
 * table in the terminal.
 */

// package definition
package net.sf.howabout.printer;

// needed imports
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import net.sf.howabout.plugin.Event;

/**
 * Provides a method for drawing a TV Guide events table in the terminal. It
 * basically takes a list of <code>net.sf.howabout.plugin.Event</code> and a
 * vector of booleans to determine which columns will be printed.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class TablePrinter {

    // events list
    private List<Event> list;

    /**
     * Constructor method. Sets the events list.
     * @param list
     */
    public TablePrinter(List<Event> list) {

        // set the events list
        this.list = list;
    }

    /**
     * Empty constructor list.
     */
    public TablePrinter() {
    }

    /**
     * Setter method for the events list.
     * @param list The events list.
     */
    public void setList(List<Event> list) {

        // set the attribute
        this.list = list;
    }

    /**
     * Draws the TV Guide events table according to the events list and
     * the boolean vector.
     * @param columns A vector of booleans of which columns will be shown.
     */
    public void draw(boolean[] columns) {

        // table builders
        StringBuilder stringbuilder = new StringBuilder();
        String line = "";

        // if first column is set
        if (columns[0]) {

            // add column
            stringbuilder.append(addSpaces("TIME",10));
            line = line.concat("----------");
        }

        // if second column is set
        if (columns[1]) {

            // add column
            stringbuilder.append(addSpaces("CHANNEL",20));
            line = line.concat("--------------------");
        }

        // if third column is set
        if (columns[2]) {

            // add column
            stringbuilder.append(addSpaces("DESCRIPTION",45));
            line = line.concat("---------------------------------------------");
        }

        // if fourth column is set
        if (columns[3]) {

            // add column
            stringbuilder.append(addSpaces("GENRE",15));
            line = line.concat("---------------");
        }

        // end table headers
        stringbuilder.append("\n");
        stringbuilder.append(line).append("\n");

        // if events list is empty
        if (list.isEmpty()) {

            // print a message
            stringbuilder.append("No entries found, sorry.");
        }
        else {
            // there are events found

            // list every single event from the list
            for (Event event : list) {

                // if first column is set
                if (columns[0]) {

                    // add event date
                    stringbuilder.append(addSpaces(" " + getTimeFormat(event.getDate()),10));
                }

                // if second column is set
                if (columns[1]) {

                    // add event channel
                    stringbuilder.append(addSpaces(" " + event.getChannel(),20));
                }

                // if third column is set
                if (columns[2]) {

                    // add event description
                    stringbuilder.append(addSpaces(" " + event.getDescription(),45));
                }

                // if fourth column is set
                if (columns[3]) {

                    // add event genre
                    stringbuilder.append(addSpaces(" " + event.getGenre(),15));
                }

                // end current event
                stringbuilder.append("\n");
                
            }

            // close table
            stringbuilder.append(line).append("\n");

        }
        
        // print table
        System.out.println(stringbuilder.toString());

    }

    /**
     * Transforms a calendar format to a string containing the event time.
     * @param date The event date.
     * @return A string containing the event time.
     */
    private String getTimeFormat(GregorianCalendar date) {

        // create a date format with time instance
        DateFormat dateformat = DateFormat.getTimeInstance(DateFormat.SHORT);

        // return the date format for the provided date
        return dateformat.format(date.getTime());
    }

    /**
     * Add spaces and cuts long strings if necessary.
     * @param string The text.
     * @param value The number of spaces to be added or the substring to be extracted.
     * @return The new string.
     */
    private String addSpaces(String string, int value) {

        // if the string length and value are the same
        if (string.length() == value) {

            // just return the string
            return string;
        }
        else {

            // if string is bigger
            if (string.length() > value) {

                // return a substring
                return string.substring(0, value-1).concat(" ");
            }
            else {

                // while string has not the defined length
                while (string.length() != value) {

                    // add spaces
                    string = string.concat(" ");
                }

                // return the converted string
                return string;
            }
        }
    }

}
