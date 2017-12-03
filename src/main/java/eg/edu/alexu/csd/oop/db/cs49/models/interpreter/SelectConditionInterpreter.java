package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

public class SelectConditionInterpreter implements Interpreter {
    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] columns = context.getColumns().trim().toLowerCase().split(",");
        context.setSelectColumns(columns);
    }
}
