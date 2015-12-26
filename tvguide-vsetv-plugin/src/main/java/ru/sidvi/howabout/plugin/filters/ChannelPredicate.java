package ru.sidvi.howabout.plugin.filters;

import net.sf.howabout.plugin.Event;

/**
 * Created by sidvi on 26.12.2015.
 */
public class ChannelPredicate extends BasePredicate {

    public ChannelPredicate(String value) {
        super(value);
    }

    @Override
    public boolean evaluate(Object object) {
        return isAny || ((Event) object).getChannel().toLowerCase().contains(value);
    }
}
