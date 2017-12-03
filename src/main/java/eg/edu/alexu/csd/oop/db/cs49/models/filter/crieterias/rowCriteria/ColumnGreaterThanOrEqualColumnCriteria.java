package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;

public class ColumnGreaterThanOrEqualColumnCriteria extends RowCriteria {
    private Double value;

    public ColumnGreaterThanOrEqualColumnCriteria(final String col,final String values) throws InvalidCondition {
        super(col,values);
        try {
            value = Double.valueOf(values);
        } catch (NumberFormatException e) {
            throw new InvalidCondition();
        } catch (IndexOutOfBoundsException e) {

        }
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

    public ColumnGreaterThanOrEqualColumnCriteria(final Double value) {
        this.value = value;
    }


    @Override
    public boolean test(final Row other) {
        return false;
        //return new ColumnGreaterThanColumnCriteria(value).or(new ColumnEqualsCrieteria(values.get(0))).test(field);

    }
}
