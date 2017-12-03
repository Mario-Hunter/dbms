package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;

public abstract class RowCriteria implements Predicate<Row> {
    private String column;
    protected String value;

    public RowCriteria() {

    }

    public String getValue() {
        return value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(final String column) {

        this.column = column;
    }

    public RowCriteria(final String column, final String value) {
        this.column = column;
        this.value = value;
    }

    public void setValue(final String value) throws InvalidCondition {
        this.value = value;
    }
}
