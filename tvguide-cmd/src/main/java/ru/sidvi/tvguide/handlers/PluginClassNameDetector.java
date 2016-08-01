package ru.sidvi.tvguide.handlers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 14.12.2015.
 */
class PluginClassNameDetector {

    private Logger log = Logger.getLogger(PluginClassNameDetector.class);

    private String jar;
    private String pluginClass;

    public PluginClassNameDetector(String jar) {
        this.jar = jar;
    }

    public boolean detect() {

        try {
            JarFile jarFile = new JarFile(jar);
            Enumeration allEntries = jarFile.entries();
            while (allEntries.hasMoreElements()) {
                JarEntry entry = (JarEntry) allEntries.nextElement();
                String name = entry.getName();

                if (name.equals("config.properties")) {
                    InputStream is = jarFile.getInputStream(entry);

                    Properties prop = new Properties();
                    prop.load(is);
                    pluginClass = prop.getProperty("plugin.class");
                    log.debug("Found plugin class: " + pluginClass);

                    return true;
                }
            }
        } catch (IOException e) {
            log.error("Plugin could not be loaded. Sorry.",e);
            return false;
        }
        return false;
    }

    public String getPlugginClassName() {
        return pluginClass;
    }

}
