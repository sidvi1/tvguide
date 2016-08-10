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
 * <b>HowAboutMain.java</b>: provides the main method for the HowAbout
 * application to run. It basically calls the methods from other
 * helper classes.
 */

// package definition
package ru.sidvi.tvguide.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import ru.sidvi.tvguide.comparator.EventTimeComparator;
import ru.sidvi.tvguide.handlers.ParametersHandler;
import ru.sidvi.tvguide.handlers.PluginRegistry;
import ru.sidvi.tvguide.plugin.Event;
import ru.sidvi.tvguide.plugin.Query;
import ru.sidvi.tvguide.plugin.api.Plugin;
import ru.sidvi.tvguide.printer.TablePrinter;

public class HowAboutMain {


    public static void main(String[] args) throws IOException {
        new HowAboutMain().doMain(args);
    }

    private  void doMain(String[] args) {
        System.out.println("TvGuide - An online TV guide searcher");
        System.out.println("Copyright (c) 2015, Vitaly Sidorov");
        System.out.println("Based on code, Paulo Roberto Massa Cereda");
        System.out.println("All rights reserved.\n");

        PluginRegistry registry = new PluginRegistry();
        registry.load();

        Set<Event> eventlist = new HashSet<Event>();

        ParametersHandler parametershanlder = new ParametersHandler(args);
        for (Plugin plugin : registry.getPlugins()) {
            if (parametershanlder.validate()) {
                Query query = parametershanlder.getQuery();

                System.out.println(parametershanlder.getHumanReadableQuery() + " in Plugin " + plugin.getPluginName());

                eventlist.addAll(plugin.getEvents(query));
            }
        }


        ArrayList<Event> list = new ArrayList<Event>(eventlist);
        Collections.sort(list, new EventTimeComparator());

        TablePrinter tableprinter = new TablePrinter(list, new PrintWriter(System.out));
        tableprinter.draw();
    }

}
