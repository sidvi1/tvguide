package ru.sidvi.tvguide.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class GenreCell extends BaseCell {
    private final Logger logger = LoggerFactory.getLogger(GenreCell.class);

    public GenreCell(String genre) {
        super(genre);
        size = 15;
    }
}
