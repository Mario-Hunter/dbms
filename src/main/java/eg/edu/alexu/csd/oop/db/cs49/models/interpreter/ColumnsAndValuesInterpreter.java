package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

import java.util.HashMap;
import java.util.Map;

public class ColumnsAndValuesInterpreter implements Interpreter {
    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String columns = context.getColumns();
        String values = context.getValues();

        if (columns == null || values == null) return;

        String[] columnsArray = columns.split(",");
        String[] valuesArray = values.split(",");

        if (columnsArray.length != valuesArray.length) {
            throw new InvalidQuerySyntaxException("Different arguments number");
        }

        Map<String, String> columnsValues = new HashMap<>();

        for (int i = 0; i < columnsArray.length; i++) {
            columnsValues.put(columnsArray[i].toLowerCase().trim(), valuesArray[i].trim().toLowerCase());
        }

        context.setColumnsAndValuesMap(columnsValues);

    }
}
