package ru.sidvi.tvguide.handlers.parameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sidvi.tvguide.Utils;
import ru.sidvi.tvguide.handlers.ParametersHandler;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class Validator {
    private final Logger logger = LoggerFactory.getLogger(Validator.class);
    private ParametersHandler handler;

    public Validator(ParametersHandler handler) {
        this.handler = handler;
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
            Utils.printErrorMessage("The number of parameters is incorrect.");
            return false;
        } else {
            handler.what = parameters[0].toLowerCase();

            // the second parameter must be "ON"
            if (!parameters[1].toLowerCase().equals("on")) {
                Utils.printErrorMessage("The syntax is incorrect. The second parameter must be 'on'.");
                return false;
            }

            handler.where = parameters[2];

            handler.when = parameters[3].toLowerCase();

            // if this parameter is not equal to "TODAY" or "TOMORROW"
            if ((!handler.when.equals("today")) && (!handler.when.equals("tomorrow"))) {
                Utils.printErrorMessage("The syntax is incorrect. The fourth parameter must be 'today' or 'tomorrow'.");
                return false;
            }


            if (parameters.length == 5) {
                handler.genre = parameters[4];
            } else {
                handler.genre = "#";
            }

            return true;
        }
    }
}
