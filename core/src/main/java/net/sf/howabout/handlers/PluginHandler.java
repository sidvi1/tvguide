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
 * <b>PluginHandler.java</b>: provides methods for loading on-the-fly plugins
 * for HowAbout. It makes use of the JarClassLoader (JCL) library, from
 * XeusTechnologies in order to automatically load classes from Jar files.
 */

// package definition
package net.sf.howabout.handlers;

// needed imports
import java.io.IOException;
import net.sf.howabout.plugin.api.HowAboutPlugin;
import org.apache.log4j.Logger;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.exception.JclException;

/**
 * Provides methods for loading on-the-fly plugins for HowAbout. It makes use
 * of the JarClassLoader (JCL) library, from XeusTechnologies in order to
 * automatically load classes from Jar files This class loads plugins that
 * implement the HowAbout Plugin API interface.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class PluginHandler {

    // the properties file handler
    private PropertiesHandler propertieshandler;

    // get the default log instance
    private Logger log = Logger.getRootLogger();


    /**
     * Constructor method. It basically handles properties from a file
     * and tries to load the plugin.
     * @param filename The filename.
     */
    public PluginHandler(String filename) {

        // new properties handler
        propertieshandler = new PropertiesHandler();

        // lets try to load the file
        try {

            // if there is no file, it is created
            propertieshandler.createIfNotAvailable(filename);

            // file is loaded
            propertieshandler.load(filename);

        } catch (IOException exception) {

            // something happened, print error
            log.error("An IO error happened: " + exception.getMessage());
        }

    }

    /**
     * Checks if the properties file has valid keys and values.
     * @return True if the properties file has valid properties, or
     * false otherwise.
     */
    public boolean checkPluginInfo() {

        // if JAR and CLASS keys are empty
        if ((propertieshandler.getProperty("jar").equals("")) || (propertieshandler.getProperty("class").equals(""))) {

            // print error and return false
            log.error("Properties file has empty values. Please, edit the howabout.properties and fix those values.");
            return false;
        }

        // everything seems OK, return true
        return true;
    }

    /**
     * Loads the plugin set in the properties file.
     * @return A <code>net.sf.howabout.plugin.api.HowAboutPlugin</code> object.
     * @throws JclException If the Jar file does not exist or is invalid,
     * this exception is thrown.
     */
    public HowAboutPlugin load() throws JclException {

        // create a new loader
        JarClassLoader jcl = new JarClassLoader();

        // load the Jar from the plugins directory
        jcl.add("plugins/" + propertieshandler.getProperty("jar"));

        // create a loader factory
        JclObjectFactory factory = JclObjectFactory.getInstance();

        // try to load the plugin according to the class defined in
        // the properties file.
        Object object = factory.create(jcl, propertieshandler.getProperty("class"));

        // cast the object to HowAboutPlugin class
        HowAboutPlugin howaboutplugin = (HowAboutPlugin) object;

        // return the plugin
        return howaboutplugin;
    }

}
