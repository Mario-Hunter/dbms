package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;


public class AnyColumnColumnCriteria extends ColumnCriteria {
    public AnyColumnColumnCriteria(final String... values) {
        super(values);
    }

    @Override
    public boolean test(final Field column) {
        return true;
    }

    public Predicate<Field> and(final Predicate<? super Field> other) {
        return (Predicate<Field>) other;
    }

    public Predicate<Field> negate() {
        return (Predicate<Field>) new NoColumnColumnCriteria();
    }

    public Predicate<Field> or(final Predicate<? super Field> other) {
        return (Predicate<Field>) this;
    }
}
