package ru.sidvi.howabout.plugin.filters;

import net.sf.howabout.plugin.Query;
import org.apache.commons.collections.Predicate;

/**
 * Created by sidvi on 26.12.2015.
 */
public class GenrePredicate implements Predicate {
    private String genre;

    public GenrePredicate(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean evaluate(Object object) {
        if (!genre.contains("#")) {
            if (genre.toLowerCase().contains(((Query)object).getGenre().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
