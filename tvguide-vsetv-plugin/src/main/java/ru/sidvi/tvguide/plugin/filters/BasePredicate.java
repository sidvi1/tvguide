package ru.sidvi.tvguide.plugin.filters;

import org.apache.commons.collections.Predicate;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 26.12.2015.
 */
public abstract class BasePredicate  implements Predicate{
    protected String value;
    protected boolean isAny;

    public BasePredicate(String value) {
        this.value = value.toLowerCase();
        isAny = value.equals("#");
    }
}
