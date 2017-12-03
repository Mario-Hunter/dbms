package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;


public class ColumnLessThanColumnCriteria extends RowCriteria {
    private Double value;

    public ColumnLessThanColumnCriteria(final String col, final String values) throws InvalidCondition {
        super(col, values);
        try {
            value = Double.valueOf(values);
        } catch (NumberFormatException e) {
            throw new InvalidCondition();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    public ColumnLessThanColumnCriteria() {

    }

    @Override
    public void setValue(final String values) throws InvalidCondition {
        super.setValue(values);
        try {
            value = Double.valueOf(values);
        } catch (NumberFormatException e) {
            throw new InvalidCondition();
        }
    }

    public ColumnLessThanColumnCriteria(final Double value) {
        this.value = value;
    }


    @Override
    public boolean test(final Row other) throws InvalidCondition {
        Field field = other.getFieldOfName(getColumn());
        try {
            return Double.compare(value, Double.valueOf(field.toString())) > 0;
        } catch (NumberFormatException e) {
            throw new InvalidCondition();
        }
    }
}
