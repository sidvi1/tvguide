package ru.sidvi.howabout.plugin.filters;

import net.sf.howabout.plugin.Query;
import org.apache.commons.collections.Predicate;

/**
 * Created by sidvi on 26.12.2015.
 */
public class NamePredicate implements Predicate {
    private String name;

    public NamePredicate(String name) {

        this.name = name;
    }

    @Override
    public boolean evaluate(Object object) {
        if (!name.contains("#")) {
            if (name.toLowerCase().contains(((Query) object).getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
