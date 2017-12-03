package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;

import eg.edu.alexu.csd.oop.db.cs49.models.pools.Type;
import eg.edu.alexu.csd.oop.db.cs49.models.validator.Validatable;

public abstract class Field<T> implements Validatable {
    protected final T value;
    private String columnName;
    public Type type;


    public Field(String value, final String columnName) throws ValueAndTypeMismatchException {
        this.value = getValue(value);
        this.columnName = columnName;
    }

    public T getValue() {
        return value;
    }

    public abstract String toString();

    abstract T getValue(String value) throws ValueAndTypeMismatchException;

    public String getColumnName() {
        return columnName;
    }

    public abstract boolean isComparable();

    @Override
    public Object getValidationField() {
        return value;
    }
    @Override
    public boolean validate(final Validatable roleModel) {
        try {
            Field newField = FieldFactory.getField((Type) roleModel.getValidationField(), "", value.toString());
            newField = null;
        } catch (ValueAndTypeMismatchException e) {
            return false;
        }
        return true;
    }
}
