package ru.sidvi.tvguide.handlers.parameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class Validator {
    private final Logger logger = LoggerFactory.getLogger(Validator.class);

    private String message = "";

    public String getMessage() {
        return message;
    }

    /**
     * Validates the parameters. Since this is a console application,
     * those parameters must be checked against a known command line
     * pattern.
     *
     * @return True if parameters have been validated, or false
     * otherwise.
     */
    public boolean validate(String[] parameters) {
        if (parameters.length < 4 || parameters.length > 5) {
            message = "The number of parameters is incorrect.";
            return false;
        }

        if (!parameters[1].toLowerCase().equals("on")) {
            message = "The syntax is incorrect. The second parameter must be 'on'.";
            return false;
        }

        String when = parameters[3].toLowerCase();
        if ((!when.equals("today")) && (!when.equals("tomorrow"))) {
            message = "The syntax is incorrect. The fourth parameter must be 'today' or 'tomorrow'.";
            return false;
        }

        return true;
    }
}
