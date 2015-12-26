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
 * <b>TablePrinter.java</b>: provides a method for drawing a TV Guide events
 * table in the terminal.
 */

// package definition
package ru.sidvi.tvguide.printer;

// needed imports

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import ru.sidvi.tvguide.plugin.Event;

/**
 * Provides a method for drawing a TV Guide events table in the terminal. It
 * basically takes a list of <code>Event</code> and a
 * vector of booleans to determine which columns will be printed.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class TablePrinter {

    private ResourceBundle i18n = ResourceBundle.getBundle("messages", Locale.getDefault());

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

    private List<Event> list;

    /**
     * Constructor method. Sets the events list.
     *
     * @param list
     */
    public TablePrinter(List<Event> list) {
        this.list = list;
    }


    /**
     * Draws the TV Guide events table according to the events list and
     * the boolean vector.
     *
     * @param columns A vector of booleans of which columns will be shown.
     */
    public void draw(boolean[] columns) {

        StringBuilder stringbuilder = new StringBuilder();
        String line = "";

        if (columns[0]) {
            stringbuilder.append(formatColumn(10, i18n.getString("time")));
            line = line.concat("----------");
        }

        if (columns[1]) {
            stringbuilder.append(formatColumn(30, i18n.getString("channel")));
            line = line.concat("--------------------");
        }

        if (columns[2]) {
            stringbuilder.append(formatColumn(15, i18n.getString("genre")));
            line = line.concat("---------------");
        }

        if (columns[3]) {
            stringbuilder.append(formatColumn(45, i18n.getString("name")));
            line = line.concat("---------------------------------------------");
        }

        stringbuilder.append("\n");
        stringbuilder.append(line).append("\n");

        if (list.isEmpty()) {
            stringbuilder.append("No entries found, sorry.");
        } else {
            for (Event event : list) {
                if (columns[0]) {
                    stringbuilder.append(formatColumn(10, format(event.getDate())));
                }

                if (columns[1]) {
                    stringbuilder.append(formatColumn(30, event.getChannel()));
                }

                if (columns[2]) {
                    stringbuilder.append(formatColumn(15, event.getGenre()));
                }

                if (columns[3]) {
                    stringbuilder.append(formatColumn(45, event.getName()));
                }


                stringbuilder.append("\n");
            }
            stringbuilder.append(line).append("\n");
        }

        System.out.println(stringbuilder.toString());
    }

    private String formatColumn(int numberOfSpaces, String data) {
        return String.format("%1$-" + numberOfSpaces + "s", data);
    }

    /**
     * Transforms a calendar format to a string containing the event time.
     *
     * @param date The event date.
     * @return A string containing the event time.
     */
    private String format(Calendar date) {
        return DATE_FORMAT.format(date.getTime());
    }


    public void draw() {
        draw(new boolean[]{true, true, true, true});
    }
}
