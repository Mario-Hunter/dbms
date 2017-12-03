package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.NoColumnColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;


public class AnyRowCriteria extends RowCriteria {

    @Override
    public boolean test(final Row column) {
        return true;
    }

    public Predicate<Row> and(final Predicate<? super Row> other) {
        return (Predicate<Row>) other;
    }

    public Predicate<Row> negate() {
        return (Predicate<Row>) new NoRowCriteria();
    }

    public Predicate<Row> or(final Predicate<? super Row> other) {
        return (Predicate<Row>) this;
    }
}
