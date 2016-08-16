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

import org.apache.commons.lang3.StringUtils;
import ru.sidvi.tvguide.plugin.Event;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Provides a method for drawing a TV Guide events table in the terminal. It
 * basically takes a list of <code>Event</code> and a
 * vector of booleans to determine which columns will be printed.
 *
 * @author Paulo Roberto Massa Cereda
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 * @version 1.0
 * @since 1.0
 */
public class TablePrinter {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");
    private ResourceBundle i18n = ResourceBundle.getBundle("messages", Locale.getDefault());
    private List<Event> list;
    private PrintStream out;

    /**
     * Constructor method. Sets the events list.
     *  @param list
     * @param out
     */
    public TablePrinter(List<Event> list, PrintStream out) {
        this.list = list;
        this.out = out;
    }


    /**
     * Draws the TV Guide events table according to the events list and
     * the boolean vector.
     *
     * @param columns A vector of booleans of which columns will be shown.
     */
    public void draw(boolean[] columns) {
        List<Row> rows = buildRows(columns);
        printRows(rows);
    }

    private void printRows(List<Row> rows) {
        StringBuilder builder = new StringBuilder();
        for (Row row : rows) {
            builder.append(row).append("\n");
        }
        out.println(builder.toString());
    }

    private List<Row> buildRows(boolean[] columns) {
        List<Row> rows = new ArrayList<Row>();

        rows.add(createRow(columns, i18n.getString("time"), i18n.getString("channel"), i18n.getString("genre"), i18n.getString("name")));
        rows.add(createSeparatorRow(columns));

        for (Event event : list) {
            String time = format(event.getDate());
            String channel = event.getChannel();
            String genre = event.getGenre();
            String name = event.getName();

            rows.add(createRow(columns, time, channel, genre, name));
        }
        rows.add(createSeparatorRow(columns));
        return rows;
    }

    private Row createSeparatorRow(boolean[] columns) {
        return createRow(columns, StringUtils.repeat("-", 10), StringUtils.repeat("-", 30), StringUtils.repeat("-", 15), StringUtils.repeat("-", 35));
    }

    private Row createRow(boolean[] columns, String time, String channel, String genre, String name) {
        Row row1 = new Row();
        if (columns[0]) {
            row1.add(new TimeCell(time));
        }

        if (columns[1]) {
            row1.add(new ChannelCell(channel));
        }

        if (columns[2]) {
            row1.add(new GenreCell(genre));
        }

        if (columns[3]) {
            row1.add(new NameCell(name));
        }
        return row1;
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
