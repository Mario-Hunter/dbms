package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;

public class ColumnLessThanOrEqualColumnCriteria extends RowCriteria {
    private Double value;

    public ColumnLessThanOrEqualColumnCriteria(final String col,final String value) throws InvalidCondition {
        super(col,value);
        try{
            this.value = Double.valueOf(value);
        }catch (NumberFormatException e){
            throw new InvalidCondition();
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

    public ColumnLessThanOrEqualColumnCriteria(final Double value) {
        super();
        this.value = value;
    }

    @Override
    public boolean test(final Row other) {
        return false;
        //return new ColumnLessThanColumnCriteria(value).or(new ColumnEqualsCrieteria(values.get(0))).test(field);

    }
}
