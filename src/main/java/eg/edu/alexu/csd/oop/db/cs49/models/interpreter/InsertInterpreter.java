package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

public class InsertInterpreter implements Interpreter {
    public static final String INSERT_PATTERN ="\\s*([a-zA-Z]+)\\s+(?i)into(?-i)\\s+([a-zA-Z_0-9\\`]+\\" +
            ".*[a-zA-Z_0-9\\`]*)" +
            "\\s*\\(\\s*([a-zA-Z_0-9\\'\\`,\\s]+)\\s*\\)\\s+(?i)values(?-i)\\s*\\(\\s*([a-zA-Z_\\',\\s0-9]+)" +
            "\\s*\\)\\s*" +
            ";{0,1}\\s*";

    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] groups = SyntaxValidator.validateAndGroupInput(context.getInput(),INSERT_PATTERN);

        context.setCommand(groups[0].toLowerCase());
        context.setTargetName(groups[1]);
        context.setColumns(groups[2]);
        context.setValues(groups[3]);

        InterpreterFactory.getInterpreter(InterpreterFactory.TARGET_INTERPRETER).interpret(context);
        InterpreterFactory.getInterpreter(InterpreterFactory.COLUMNS_AND_VALUES_INTERPRETER).interpret(context);

    }
}
