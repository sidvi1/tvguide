package ru.sidvi.tvguide.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class ChannelCell extends BaseCell {
    private final Logger logger = LoggerFactory.getLogger(ChannelCell.class);

    public ChannelCell(String value) {
        super(value);
        size = 30;
    }
}
