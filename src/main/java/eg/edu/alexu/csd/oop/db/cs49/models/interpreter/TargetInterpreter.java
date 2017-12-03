package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

import static eg.edu.alexu.csd.oop.db.cs49.models.Context.CREATE_DATABASE;
import static eg.edu.alexu.csd.oop.db.cs49.models.Context.DROP_DATABASE;

class TargetInterpreter implements Interpreter {
    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] targetDetailed = context.getTargetName().split("\\.");
        if (targetDetailed.length == 1 &&
                (context.getCommand().equals(CREATE_DATABASE) || context.getCommand().equals(DROP_DATABASE))) {
            context.setDatabase(targetDetailed[0].trim().toLowerCase());
        } else if (targetDetailed.length == 1) {
            context.setTable(targetDetailed[0].trim().toLowerCase());
        } else if (targetDetailed.length == 2) {
            context.setDatabase(targetDetailed[0].trim().toLowerCase());
            context.setTable(targetDetailed[1].toLowerCase().trim());
        } else {
            throw new InvalidQuerySyntaxException("Missing table or database name");
        }
    }
}
