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
 * <b>HowAboutPlugin.java</b>: provides a default standard plugin interface
 * for writting HowAbout plugins.
 */

// package definition
package net.sf.howabout.plugin.api;

// needed imports
import java.util.List;
import net.sf.howabout.plugin.Event;
import net.sf.howabout.plugin.Query;

/**
 * Provides a default standard plugin interface for writting HowAbout plugins.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public interface HowAboutPlugin {

    /**
     * Returns all events found which match with the provided query.
     * @param query A <code>net.sf.howabout.plugin.Query</code> object, which
     * is set according to HowAbout parameters.
     * @return A list of events. If no events are found, it returns an
     * empty list.
     */
    public List<Event> getEvents(Query query);

    /**
     * Get the plugin name.
     * @return A string containing the plugin name.
     */
    public String getPluginName();

    /**
     * Get the plugin version.
     * @return A string containing the plugin version.
     */
    public String getPluginVersion();

    /**
     * Get the plugin author.
     * @return A string containing the plugin author.
     */
    public String getPluginAuthor();

    /**
     * Get the plugin full package name.
     * @return A string containing the plugin full package name.
     */
    public String getPluginFullPackageName();

    /**
     * Get the plugin help.
     * @return A string containing the plugin help.
     */
    public String getPluginHelp();

}
