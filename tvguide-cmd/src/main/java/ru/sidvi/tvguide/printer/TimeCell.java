package ru.sidvi.tvguide.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class TimeCell extends BaseCell {
    private final Logger logger = LoggerFactory.getLogger(TimeCell.class);

    public TimeCell(String value) {
        super(value);
    }
}
