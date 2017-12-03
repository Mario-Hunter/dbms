package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;

import java.util.*;

public class ColumnNameColumnCriteria extends ColumnCriteria {
    public ColumnNameColumnCriteria(final String... values) {
        super(values);
    }

    @Override
    public boolean test(final Field field) {
        return valuesContain(field.getColumnName());
    }

    public Predicate<Field> and(final ColumnCriteria other) {
        List<String> ANDedValues = new ArrayList<>();
        for(String value: values){
            if(other.getValues().contains(value)){
                ANDedValues.add(value);
            }
        }
        String[] valuesArray = new String[ANDedValues.size()];
        ANDedValues.toArray(valuesArray);
        return (Predicate<Field>) new ColumnNameColumnCriteria(valuesArray);
    }

    public Predicate<Field> negate() {
        return null;
    }

    public Predicate<Field> or(final ColumnCriteria other) {
        Set<String> valuesSet = new HashSet<>();
        valuesSet.addAll(values);
        valuesSet.addAll(other.getValues());
        String[] valuesArray = new String[valuesSet.size()];
        valuesSet.toArray(valuesArray);
        return (Predicate<Field>) new ColumnNameColumnCriteria(valuesArray);
    }


}
