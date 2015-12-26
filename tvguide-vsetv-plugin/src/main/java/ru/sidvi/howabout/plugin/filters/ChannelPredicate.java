package ru.sidvi.howabout.plugin.filters;

import net.sf.howabout.plugin.Query;
import org.apache.commons.collections.Predicate;

/**
 * Created by sidvi on 26.12.2015.
 */
public class ChannelPredicate implements Predicate {
    private String channel;

    public ChannelPredicate(String channel) {
        this.channel = channel;
    }

    @Override
    public boolean evaluate(Object object) {
        if (!channel.contains("#")) {
            if (channel.toLowerCase().contains(((Query) object).getChannel().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
