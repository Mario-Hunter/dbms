package eg.edu.alexu.csd.oop.db.cs49.models.filter;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;

public interface Predicate<T> {
    boolean test(T other) throws InvalidCondition;
}
