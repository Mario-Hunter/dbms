package eg.edu.alexu.csd.oop.db.cs49.models.filter;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;

import java.util.List;

public interface Filter {

    List<Filterable> meetCriteria(List<Filterable> elementsToFiler) throws InvalidCondition;

    interface Filterable {

    }
}
