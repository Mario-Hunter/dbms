package eg.edu.alexu.csd.oop.db.cs49.models.filter;

import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.InvalidCondition;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.QueryCriteriaFactory;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria.AnyRowCriteria;
import eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.rowCriteria.RowCriteria;

import java.util.ArrayList;
import java.util.Stack;

import static eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias.QueryCriteriaFactory.*;

public class RowFilterBuilder {
    private static ArrayList<String> operations;


    private static void loadOperations() {
        operations = new ArrayList<>();
        operations.add("and");
        operations.add("or");
        operations.add("not");
    }

    public static Filter buildFilter(Stack<String> postfix) throws InvalidCondition {
        RowCriteria criteria = new AnyRowCriteria();
        Stack<String> operators = new Stack();
        Stack<RowCriteria> operands = new Stack<>();
        boolean pendingOperand = false;
        while (!postfix.empty()) {
            String token = postfix.pop();
            if (isOperator(token)) {
                operators.push(token);
                pendingOperand = false;
            } else {
                RowCriteria newCriteria = parseCriteria(token);
                if (pendingOperand) {
                    while (!operands.empty()) {
                        RowCriteria criteria1 = operands.pop();
                        String op = operators.pop();
                        newCriteria = evaluate(newCriteria, op, criteria1);
                    }
                }
                operands.push(newCriteria);
                pendingOperand = true;
            }
        }
        Filter rowFilter = new RowFilter(operands.pop());
        return rowFilter;
    }

    private static RowCriteria evaluate(final RowCriteria newCriteria, final String op, final RowCriteria criteria1) {
        switch (op) {
//            case "and":
//                return newCriteria.and(criteria1);
//            case "or":
//                return newCriteria.or(criteria1);
            default:
                return newCriteria;
        }
    }

    private static RowCriteria parseCriteria(final String token) throws InvalidCondition {
        if (token.equals("*")) {
            return (RowCriteria) QueryCriteriaFactory.getCriteria(ANY_ROW);
        }
        String[] elements = token.trim().split("[=<>]+");
        String op = token.replace(elements[0], "").replace(elements[1], "").trim();
        RowCriteria criteria = (RowCriteria) QueryCriteriaFactory.getCriteria(getCriteriaType(op));
        criteria.setColumn(elements[0].trim());
        criteria.setValue(elements[1].trim());
        return criteria;
    }


    private static int getCriteriaType(final String op) {
        switch (op) {
            case "=":
                return COLUMN_EQUALS;
            case "<":
                return COLUMN_SMALLER_THAN;
            case ">":
                return COLUMN_GREATER_THAN;
            case ">=":
                return COLUMN_GREATER_THAN_OR_EQUAL;
            case "<=":
                return COLUMN_SMALLER_THAN_OR_EQUAL;
        }
        return 0;
    }

    private static boolean isOperator(final String token) {
        if (operations == null) loadOperations();

        return operations.contains(token.trim().toLowerCase());
    }
}
