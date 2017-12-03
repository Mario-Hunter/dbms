package eg.edu.alexu.csd.oop.db.cs49.models.filter;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.*;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.columnCriteria.ColumnCriteria;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.QueryCriteriaFactory.ANY_COLUMN;
import static eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.QueryCriteriaFactory.COLUMN_NAME;

public class QueryFilterBuilder {
    private static Map<String, ColumnCriteria> criteriasMap;
    private static QueryFilterBuilder builder;

    private QueryFilterBuilder() {
    }

    public static QueryFilterBuilder buildFilter() {
        criteriasMap = new HashMap<>();
        builder = new QueryFilterBuilder();
        return builder;
    }

    private static ColumnCriteria getCriteriaFor(final String column) throws InvalidCondition {
        if (!criteriasMap.containsKey(column)) return (ColumnCriteria) QueryCriteriaFactory.getCriteria(ANY_COLUMN);
        return criteriasMap.get(column);
    }

    private static ColumnCriteria getCriteria(final int criteriaType, final String... value) throws InvalidCondition {
        ColumnCriteria columnCriteria = (ColumnCriteria) QueryCriteriaFactory.getCriteria(criteriaType);
        columnCriteria.setValues(value);
        return columnCriteria;
    }

    public QueryFilterBuilder addCriteria(int criteriaType, String column, String... value) {
        ColumnCriteria newColumnCriteria = null;
        try {
            newColumnCriteria = (ColumnCriteria) QueryCriteriaFactory.getCriteria(criteriaType);
            newColumnCriteria.setValues(value);
            criteriasMap.put(column, newColumnCriteria);
        } catch (InvalidCondition invalidCondition) {
            invalidCondition.printStackTrace();
        }

        return builder;
    }


//    public QueryFilterBuilder and(final int criteriaType, String column, String... value) {
//        ColumnCriteria oldCriteria = null;
//        try {
//            oldCriteria = getCriteriaFor(column);
//            oldCriteria.and(getCriteria(criteriaType, value));
//            criteriasMap.put(column, oldCriteria);
//        } catch (InvalidCondition invalidCondition) {
//            invalidCondition.printStackTrace();
//        }
//
//        return builder;
//    }
//
//
//    public QueryFilterBuilder or(final int criteriaType, String column, String... value) {
//        try {
//            ColumnCriteria oldCriteria = getCriteriaFor(column);
//            oldCriteria.or(getCriteria(criteriaType, value));
//            criteriasMap.put(column, oldCriteria);
//        } catch (InvalidCondition invalidCondition) {
//            invalidCondition.printStackTrace();
//        }
//        return builder;
//    }

    public Filter build() {
        return new ColumnFilter(criteriasMap);
    }

    public static Filter buildFilter(final Stack<String> postfixConditions) {
        return null;
    }

    public static Filter buildFilter(final String[] selectColumns) {
        QueryFilterBuilder builder = buildFilter();
        if (selectColumns[0].equals("*")) {
            builder.addCriteria(ANY_COLUMN, "*");
            return builder.build();
        }
        for (String col : selectColumns) {
            builder.addCriteria(COLUMN_NAME, col,col);
        }
        return builder.build();
    }
}
