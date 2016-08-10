package ru.sidvi.tvguide;

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
            return load(file, "UTF-8");
        } catch (IOException e) {
            logger.error("Exception while load resource from file {}", file, e);
        }
        return "";
    }

    public static String fromResource(String file, String charset) {
        try {
            return load(file, charset);
        } catch (IOException e) {
            logger.error("Exception while load resource from file {}", file, e);
        }
        return "";
    }

    private static String load(String file, String charset) throws IOException {
        return IOUtils.toString(
                TestUtils.class.getClassLoader().getResourceAsStream(file),
                charset
        );
    }
}
