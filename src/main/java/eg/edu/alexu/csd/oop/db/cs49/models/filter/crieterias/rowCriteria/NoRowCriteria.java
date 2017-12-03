package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.AnyColumnColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;


public class NoRowCriteria extends RowCriteria {
    @Override
    public boolean test(final Row row) {
        return false;
    }


    public Predicate<Row> and(final Predicate<? super Row> other) {
        return (Predicate<Row>) this;
    }

    public Predicate<Row> negate() {
        return (Predicate<Row>) new AnyRowCriteria();
    }

    public Predicate<Row> or(final Predicate<? super Row> other) {
        return (Predicate<Row>) other;
    }
}
