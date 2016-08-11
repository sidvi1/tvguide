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
 * <b>HowAboutMain.java</b>: provides the main method for the HowAbout
 * application to run. It basically calls the methods from other
 * helper classes.
 */

// package definition
package ru.sidvi.tvguide.main;

import ru.sidvi.tvguide.Utils;
import ru.sidvi.tvguide.comparator.EventTimeComparator;
import ru.sidvi.tvguide.handlers.PluginRegistry;
import ru.sidvi.tvguide.handlers.QueryFactory;
import ru.sidvi.tvguide.handlers.parameters.Validator;
import ru.sidvi.tvguide.plugin.Event;
import ru.sidvi.tvguide.plugin.Query;
import ru.sidvi.tvguide.plugin.api.Plugin;
import ru.sidvi.tvguide.printer.TablePrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HowAboutMain {


    public static void main(String[] args) throws IOException {
        new HowAboutMain().doMain(args);
    }

    private void doMain(String[] args) {
        System.out.println("TvGuide - An online TV guide searcher");
        System.out.println("Copyright (c) 2015, Vitaly Sidorov");
        System.out.println("Based on code, Paulo Roberto Massa Cereda");
        System.out.println("All rights reserved.\n");

        PluginRegistry registry = new PluginRegistry();
        registry.load();

        Set<Event> eventlist = new HashSet<Event>();

        for (Plugin plugin : registry.getPlugins()) {
            Validator validator = new Validator();
            if (validator.validate(args)) {
                Query query = new QueryFactory(args).create();
                System.out.println(query.getHumanReadable() + " in Plugin " + plugin.getPluginName());

                eventlist.addAll(plugin.getEvents(query));
            } else {
                Utils.printErrorMessage(validator.getMessage());
            }
        }


        ArrayList<Event> list = new ArrayList<Event>(eventlist);
        Collections.sort(list, new EventTimeComparator());

        TablePrinter tableprinter = new TablePrinter(list, new PrintWriter(System.out));
        tableprinter.draw();
    }

}
