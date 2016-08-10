package ru.sidvi.tvguide.printer;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class TestUtils {
    private static final Logger logger = LoggerFactory.getLogger(TestUtils.class);

    public static String removeWhiteSpaces(String input) {
        return input.replace("\\s+", "");
    }

    public static String removeReturn(String input) {
        return input.replaceAll("\\r", "");
    }

    public static String fromResource(String file) {
        try {
            return load(file);
        } catch (IOException e) {
            logger.error("Exception while load resource from file {}", file, e);
        }
        return "";
    }

    private static String load(String file) throws IOException {
        return IOUtils.toString(
                TestUtils.class.getClassLoader().getResourceAsStream(file),
                "UTF-8"
        );
    }
}
