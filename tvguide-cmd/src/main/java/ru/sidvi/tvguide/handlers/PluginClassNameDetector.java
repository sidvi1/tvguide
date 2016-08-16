package ru.sidvi.tvguide.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger log = LoggerFactory.getLogger(PluginClassNameDetector.class);

    private String jar;
    private String pluginClass;

    public PluginClassNameDetector(String jar) {
        this.jar = jar;
    }

    public boolean detect() {
        try {
            if (tryToDetect()) {
                return true;
            }
        } catch (IOException e) {
            log.error("Plugin could not be loaded. Sorry.", e);
            return false;
        }
        return false;
    }

    private boolean tryToDetect() throws IOException {
        JarFile jarFile = new JarFile(jar);
        Enumeration allEntries = jarFile.entries();
        while (allEntries.hasMoreElements()) {
            JarEntry entry = (JarEntry) allEntries.nextElement();
            String name = entry.getName();

            if (name.equals("config.properties")) {
                pluginClass = read(jarFile.getInputStream(entry));
                return true;
            }
        }
        return false;
    }

    private String read(InputStream is) throws IOException {
        Properties prop = new Properties();
        prop.load(is);
        String pluginClass = prop.getProperty("plugin.class");
        log.debug("Found plugin class: " + pluginClass);
        return pluginClass;
    }

    public String getPlugginClassName() {
        return pluginClass;
    }

}
