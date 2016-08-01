package ru.sidvi.tvguide.plugin.filters;

import ru.sidvi.tvguide.plugin.Event;

/**
 * Created by Vitaly Sidorov (mail@vitaly-sidorov.com) on 26.12.2015.
 */
public class NamePredicate extends BasePredicate {

    public NamePredicate(String value) {
        super(value);
    }

    @Override
    public boolean evaluate(Object object) {
        return isAny || ((Event) object).getName().toLowerCase().contains(value);
    }
}
