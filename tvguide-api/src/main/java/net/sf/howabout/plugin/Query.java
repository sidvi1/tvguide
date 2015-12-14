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
 * <b>Query.java</b>: provides query features for HowAbout classes.
 */

// package definition
package net.sf.howabout.plugin;

/**
 * Provides query features for HowAbout classes. The query object is heavily
 * used by all plugins, as they rely on the information a query holds.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class Query {

    // event channel
    private String channel;

    // event genre
    private String genre;

    // event day
    private Day day;

    
    /**
     * Getter method for channel.
     * @return The event channel.
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Setter method for channel.
     * @param channel The event channel.
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Getter method for day.
     * @return The event day.
     */
    public Day getDay() {
        return day;
    }

    /**
     * Setter method for day.
     * @param day The event day.
     */
    public void setDay(Day day) {
        this.day = day;
    }

    /**
     * Getter method for genre.
     * @return The event genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Setter method for genre.
     * @param genre The event genre.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Empty constructor method.
     */
    public Query() {
    }

    /**
     * Constructor method which sets all attributes.
     * @param channel The event channel.
     * @param genre The event genre.
     * @param day The event day.
     */
    public Query(String channel, String genre, Day day) {

        // set all values
        this.channel = channel;
        this.genre = genre;
        this.day = day;
    }

    

}
