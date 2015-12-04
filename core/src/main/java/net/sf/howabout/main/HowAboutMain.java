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
package net.sf.howabout.main;

// needed imports
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import net.sf.howabout.handlers.ParametersHandler;
import net.sf.howabout.handlers.PluginHandler;
import net.sf.howabout.plugin.Event;
import net.sf.howabout.plugin.Query;
import net.sf.howabout.plugin.api.HowAboutPlugin;
import net.sf.howabout.printer.TablePrinter;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.xeustechnologies.jcl.exception.JclException;

/**
 * Provides the main method for the HowAbout application to run. It basically
 * calls the methods from other helper classes.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class HowAboutMain {

    /**
     * Main method for HowAbout.
     * @param args The command line arguments.
     * @throws FileNotFoundException This exception is thrown if the
     * properties file is not found.
     * @throws IOException This exception is thrown if some IO problem
     * happens.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        // get the log instance
        Logger log = Logger.getRootLogger();

        // set the error level
        log.setLevel(Level.ERROR);

        // add an appender to the log
        log.addAppender(new ConsoleAppender(new SimpleLayout()));

        // create a HowAboutPlugin object
        HowAboutPlugin howaboutplugin = null;

        // print the header info
        System.out.println("HowAbout - An online TV guide searcher");
        System.out.println("Copyright (c) 2011, Paulo Roberto Massa Cereda");
        System.out.println("All rights reserved.\n");

        // create a new plugin handler based on the contents
        // of the properties file
        PluginHandler pluginhandler = new PluginHandler("howabout.properties");

        // check if contents are valid
        if (pluginhandler.checkPluginInfo()) {

            // try to load the plugin
            try {

                // load the plugin
                howaboutplugin = pluginhandler.load();

                // create a parameters handler with the
                // provided application parameters
                ParametersHandler parametershanlder = new ParametersHandler(args);

                // if parameters are OK
                if (parametershanlder.validate()) {

                    // get a query based on such parameters
                    Query query = parametershanlder.getQuery();

                    // print the search
                    System.out.println(parametershanlder.getHumanReadableQuery());

                    // warn the user, this may take some time
                    System.out.println("Please, wait...\n");

                    // create an events list, in which the plugin will
                    // fill it with the desired information
                    ArrayList<Event> eventlist = (ArrayList<Event>) howaboutplugin.getEvents(query);

                    // create a new printer class and provide the
                    // events list
                    TablePrinter tableprinter = new TablePrinter(eventlist);

                    // define which columns will be displayed and print
                    tableprinter.draw(parametershanlder.getTableParameters());

                }
            }
            catch (JclException exception) {
                // something bad happened when trying to load
                // the plugin

                // print the error message
                log.error("Plugin could not be loaded. Sorry." + exception.getMessage());
            }
            catch (Exception exception) {
                // other exceptions were raised, just warn
                // the user about it

                // print the error message
                log.error("Something bad happened.");
            }
        }
    }

}
