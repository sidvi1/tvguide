package ru.sidvi.tvguide.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sidvi.tvguide.Utils;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class BaseCell implements Cell {
    private final Logger logger = LoggerFactory.getLogger(BaseCell.class);

    protected int size = 10;
    private String value;

    public BaseCell(String value) {
        this.value = value;
    }

    @Override
    public String getFormattedValue() {
        return Utils.rightPadUntil(size, value);
    }
}
