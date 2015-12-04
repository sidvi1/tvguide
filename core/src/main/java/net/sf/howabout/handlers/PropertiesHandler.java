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
 * <b>PropertiesHandler.java</b>: provides methods for handling properties
 * files.
 */

// package definition
package net.sf.howabout.handlers;

// needed imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Provides methods for handling properties files. This class has some helper
 * methods to make it ease to read and write properties files, as it is used
 * by the HowAbout plugin system.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class PropertiesHandler {

    // properties file
    private Properties propertiesFile;

    /**
     * Constructor method. Nothing new in here, an instance of the properties
     * object is created.
     */
    public PropertiesHandler() {

        // create a new properties object
        propertiesFile = new Properties();

    }

    /**
     * Loads the properties file. It basically reads the filename and sets
     * keys and values from it to the properties object.
     * @param filename The properties filename.
     * @throws FileNotFoundException This exception is thrown in case of file
     * not found.
     * @throws IOException If something happened due to some IO problem, this
     * exception is thrown.
     */
    public void load(String filename) throws FileNotFoundException, IOException {

        // create a file reader from the filename
        FileReader filereader = new FileReader(new File(filename));

        // load the keys and values from the file reader
        propertiesFile.load(filereader);

        // close the reader
        filereader.close();

        // check if properties are null
        if ((propertiesFile.getProperty("jar") == null) || (propertiesFile.getProperty("class") == null)) {

            // if JAR property is missing
            if (propertiesFile.getProperty("jar") == null) {

                // set JAR property
                propertiesFile.setProperty("jar", "");
            }
            else {

                // JAR property is missing, so it is set now
                propertiesFile.setProperty("class", "");
            }

            // save the new properties file
            save(filename);
        }

    }

    /**
     * Saves the properties object to a file.
     * @param filename The properties filename.
     * @throws IOException An IOException is thrown whenever something
     * wrong happened.
     */
    public void save(String filename) throws IOException {

        // create a new file writer
        FileWriter filewriter = new FileWriter(new File(filename));

        // store the properties file
        propertiesFile.store(filewriter, "HowAbout Properties File");

        // close the writer
        filewriter.close();

    }

    /**
     * Gets a property.
     * @param key The property key.
     * @return The property value.
     */
    public String getProperty(String key) {

        // return the property value according to the key
        return propertiesFile.getProperty(key);
    }

    /**
     * Sets a property.
     * @param key The property key.
     * @param value The property value.
     */
    public void setProperty(String key, String value) {

        // call the inner setProperty method from the
        // properties object
        propertiesFile.setProperty(key, value);
    }

    /**
     * Checks if file exists.
     * @param filename The filename.
     * @return True if file exists, or false otherwise.
     */
    public boolean exists(String filename) {

        // create a new file instance
        File file = new File(filename);

        // call the file inner method
        return file.exists();
    }

    /**
     * Checks if properties object is empty.
     * @return True if properties object is empty, or false otherwise.
     */
    public boolean isEmpty() {

        // call the properties object inner method
        return propertiesFile.isEmpty();
    }

    /**
     * Creates the properties file if it does not exist,
     * @param filename The filename.
     * @throws IOException Throws this exception if some IO error happened.
     */
    public void createIfNotAvailable(String filename) throws IOException {

        // check if file does not exist
        if (!exists(filename)) {

            // set properties
            setProperty("jar", "");
            setProperty("class", "");

            // save file
            save(filename);
        }
    }

}
