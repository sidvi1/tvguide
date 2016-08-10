package ru.sidvi.tvguide.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaly Sidorov mail@vitaly-sidorov.com
 */
public class Row {
    private final Logger logger = LoggerFactory.getLogger(Row.class);

    private List<Cell> row = new ArrayList<Cell>();

    public void add(Cell cell){
        row.add(cell);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Cell cell : row) {
            builder.append(cell.getFormattedValue());
        }
        return builder.toString();
    }
}
