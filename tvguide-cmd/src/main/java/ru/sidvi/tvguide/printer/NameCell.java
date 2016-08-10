package ru.sidvi.tvguide.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class NameCell extends BaseCell {
    private final Logger logger = LoggerFactory.getLogger(NameCell.class);

    public NameCell(String name) {
        super(name);
        size = 35;
    }
}
