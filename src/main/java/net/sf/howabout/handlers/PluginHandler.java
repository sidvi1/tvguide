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
 * <b>PluginHandler.java</b>: provides methods for loading on-the-fly plugins
 * for HowAbout. It makes use of the JarClassLoader (JCL) library, from
 * XeusTechnologies in order to automatically load classes from Jar files.
 */

// package definition
package net.sf.howabout.handlers;

// needed imports

import java.io.*;
import java.util.*;

import net.sf.howabout.plugin.api.HowAboutPlugin;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.exception.JclException;

/**
 * Provides methods for loading on-the-fly plugins for HowAbout. It makes use
 * of the JarClassLoader (JCL) library, from XeusTechnologies in order to
 * automatically load classes from Jar files This class loads plugins that
 * implement the HowAbout Plugin API interface.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class PluginHandler {

    private static final String PLUGIN_DIR = "plugins";


    public Collection<HowAboutPlugin> load() throws JclException {
        Collection<HowAboutPlugin> plugins = new ArrayList<HowAboutPlugin>();

        JarClassLoader jcl = new JarClassLoader();
        jcl.add(PLUGIN_DIR + File.separator);
        JclObjectFactory factory = JclObjectFactory.getInstance();

        List<String> jarFiles = listJarFiles();
        for (String jarFile : jarFiles) {

            ClassNameFinder finder = new ClassNameFinder(jarFile);
            if (finder.findPluginClass()) {
                plugins.add((HowAboutPlugin) factory.create(jcl, finder.getPluginClass()));
            }
        }

        return plugins;
    }

    private List<String> listJarFiles() {
        File pluginDir = new File(PLUGIN_DIR);
        String[] plugins = pluginDir.list(new FilenameFilter() {
            public boolean accept(File file, String s) {
                return s.endsWith(".jar");
            }
        });

        return Arrays.asList(plugins);
    }


}
