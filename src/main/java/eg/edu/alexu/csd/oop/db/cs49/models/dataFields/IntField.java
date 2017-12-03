package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;

import eg.edu.alexu.csd.oop.db.cs49.models.pools.Type;
import eg.edu.alexu.csd.oop.db.cs49.models.validator.Validatable;

public class IntField extends Field<Integer> {
    public IntField(final String value, final String columnName) throws ValueAndTypeMismatchException {
        super(value, columnName);
        this.type = Type.INT;
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    Integer getValue(final String value) throws ValueAndTypeMismatchException {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ValueAndTypeMismatchException(value, Integer.class);
        }
    }

    @Override
    public boolean isComparable() {
        return true;
    }




}
