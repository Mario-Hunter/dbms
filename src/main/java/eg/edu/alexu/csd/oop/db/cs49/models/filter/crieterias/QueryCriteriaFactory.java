package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.Predicate;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.AnyColumnColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnNameColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.NoColumnColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria.AnyRowCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria.ColumnEqualsCrieteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria.ColumnGreaterThanColumnCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria.ColumnLessThanColumnCriteria;

public class QueryCriteriaFactory {
    public static final int COLUMN_NAME = 0;
    public static final int ANY_COLUMN = 1;
    public static final int NO_COLUMN = 2;
    public static final int COLUMN_EQUALS = 3;
    public static final int COLUMN_SMALLER_THAN = 4;
    public static final int COLUMN_GREATER_THAN = 5;
    public static final int COLUMN_GREATER_THAN_OR_EQUAL = 6;
    public static final int COLUMN_SMALLER_THAN_OR_EQUAL = 7;
    public static final int ANY_ROW = 8;

    private QueryCriteriaFactory() {
    }

    public static Predicate getCriteria(final int filterType) throws InvalidCondition {
        switch (filterType) {
            case COLUMN_NAME:
                return new ColumnNameColumnCriteria();
            case ANY_COLUMN:
                return new AnyColumnColumnCriteria();
            case NO_COLUMN:
                return new NoColumnColumnCriteria();
            case COLUMN_EQUALS:
                return new ColumnEqualsCrieteria();
            case COLUMN_SMALLER_THAN:
                return new ColumnLessThanColumnCriteria();
            case COLUMN_GREATER_THAN:
                return new ColumnGreaterThanColumnCriteria();
//            case COLUMN_GREATER_THAN_OR_EQUAL:
//                return new ColumnGreaterThanOrEqualColumnCriteria();
//            case COLUMN_SMALLER_THAN_OR_EQUAL:
//                return new ColumnLessThanOrEqualColumnCriteria();
            case ANY_ROW:
                return new AnyRowCriteria();
            default:
                return new AnyColumnColumnCriteria();
        }
    }
}
