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
 * <b>ParametersHandler.java</b>: handles command line parameters to be
 * checked against a known pattern.
 */

// package definition
package ru.sidvi.tvguide.handlers;


import org.apache.log4j.Logger;
import ru.sidvi.tvguide.plugin.Day;
import ru.sidvi.tvguide.plugin.Query;

/**
 * Create and fill Query object based on parameters.
 *
 * @author Vitaly Sidorov (mail@vitaly-sidorov.com)
 */
public class QueryFactory {
    private Logger log = Logger.getRootLogger();
    private String[] parameters;

    public QueryFactory(String[] parameters) {
        this.parameters = parameters;
    }

    public Query create() {
        Query query = new Query();

        query.setChannel(parameters[2]);
        query.setName(parameters[0].toLowerCase());

        if (parameters[3].toLowerCase().equals("today")) {
            query.setDay(Day.TODAY);
        } else {
            query.setDay(Day.TOMORROW);
        }

        if (parameters.length == 5) {
            query.setGenre(parameters[4]);
        }

        return query;
    }
}
