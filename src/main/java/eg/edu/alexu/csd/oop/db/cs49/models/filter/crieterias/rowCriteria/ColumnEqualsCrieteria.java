package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria;

import eg.edu.alexu.csd.oop.db.cs49.models.dataFields.Field;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.pools.Row;

public class ColumnEqualsCrieteria extends RowCriteria {
    public ColumnEqualsCrieteria(final String col,final String values) {
        super(col,values);
    }

    public ColumnEqualsCrieteria() {

    }


//    public Predicate<Field> and(final ColumnCriteria other) {
//        if (!(other instanceof ColumnEqualsCrieteria)) return other.and(this);
//        List<String> ANDedValues = new ArrayList<>();
//        for (String value : values) {
//            if (other.getValues().contains(value)) {
//                ANDedValues.add(value);
//            }
//        }
//        String[] valuesArray = new String[ANDedValues.size()];
//        ANDedValues.toArray(valuesArray);
//        return (Predicate<Field>) new ColumnEqualsCrieteria(valuesArray);
//    }

    public Predicate<Field> negate() {
        return null;
    }

    @Override
    public boolean test(final Row other) {
        return other.getFieldOfName(getColumn()).toString().equals(value);
    }


//    public Predicate<Field> or(final ColumnCriteria other) {
//        if (!(other instanceof ColumnEqualsCrieteria)) return other.and(this);
//        Set<String> valuesSet = new HashSet<>();
//        valuesSet.addAll(values);
//        valuesSet.addAll(other.getValues());
//        String[] valuesArray = new String[valuesSet.size()];
//        valuesSet.toArray(valuesArray);
//        return (Predicate<Field>) new ColumnEqualsCrieteria(valuesArray);
//    }
}
