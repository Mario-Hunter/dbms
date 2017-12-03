package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;

import eg.edu.alexu.csd.oop.db.cs49.models.pools.Type;
import eg.edu.alexu.csd.oop.db.cs49.models.validator.Validatable;

public class VarCharField extends Field<String> {

    public VarCharField(final String value, final String columnName) throws ValueAndTypeMismatchException {
        super(value, columnName);
        this.type = Type.varChar;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    String getValue(final String value) {
        try {
            return value.substring(0, 255);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
    }

    @Override
    public boolean isComparable() {
        return false;
    }


}
