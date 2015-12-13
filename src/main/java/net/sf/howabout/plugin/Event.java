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
 * <b>Event.java</b>: provides the POJO class that represents a TV event.
 */

// package definition
package net.sf.howabout.plugin;

// needed imports
import java.util.GregorianCalendar;

/**
 * Provides the POJO class that represents a TV event.
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class Event {

    // the event date
    private GregorianCalendar date;

    // the event name
    private String name;

    // the event description
    private String description;

    // the event genre
    private String genre;

    // the event channel
    private String channel;

    /**
     * Empty constructor method.
     */
    public Event() {
    }

    /**
     * Constructor method. It basically sets all class attributes.
     * @param date The event date.
     * @param name The event name.
     * @param description The event description.
     * @param genre The event genre.
     * @param channel The event channel.
     */
    public Event(GregorianCalendar date, String name, String description, String genre, String channel) {

        // set everything
        this.date = date;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.channel = channel;
    }

    /**
     * Getter method for channel.
     * @return A string containing the event channel.
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
     * Getter method for description.
     * @return A string containing the event description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for description.
     * @param description The event description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for date.
     * @return A calendar object containing the event date.
     */
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * Setter method for date.
     * @param date The event date.
     */
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    /**
     * Getter method for genre.
     * @return A string containing the event genre.
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
     * Getter method for name.
     * @return A string containing the event name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name.
     * @param name The event name.
     */
    public void setName(String name) {
        this.name = name;
    }

}
