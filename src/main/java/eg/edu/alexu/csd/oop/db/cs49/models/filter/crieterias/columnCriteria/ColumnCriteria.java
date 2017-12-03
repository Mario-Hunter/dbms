package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;

import java.util.Arrays;
import java.util.List;

public abstract class ColumnCriteria implements Predicate<Field> {

    protected List<String> values;

    public List<String> getValues() {
        return values;
    }

    public ColumnCriteria(String... values) {
        this.values = Arrays.asList(values);
    }

    protected boolean valuesContain(String value) {
        return values.contains(value);
    }

    public void setValues(List<String> values) {
        this.values.clear();
        this.values.addAll(values);
    }

    public void setValues(final String... value) throws InvalidCondition {
        this.values = Arrays.asList(value);
    }
}
