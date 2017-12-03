package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;


public class NoColumnColumnCriteria extends ColumnCriteria {
    @Override
    public boolean test(final Field column) {
        return false;
    }


    public Predicate<Field> and(final Predicate<? super Field> other) {
        return (Predicate<Field>) this;
    }

    public Predicate<Field> negate() {
        return (Predicate<Field>) new AnyColumnColumnCriteria();
    }

    public Predicate<Field> or(final Predicate<? super Field> other) {
        return (Predicate<Field>) other;
    }
}
