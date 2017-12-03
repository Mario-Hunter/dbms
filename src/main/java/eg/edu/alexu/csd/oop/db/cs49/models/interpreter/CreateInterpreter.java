package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

import static eg.edu.alexu.csd.oop.db.cs49.models.Context.CREATE_DATABASE;
import static eg.edu.alexu.csd.oop.db.cs49.models.Context.CREATE_TABLE;

class CreateInterpreter implements Interpreter {
    public static final String CREATE_PATTERN ="\\s*(?i)create\\s+(table|database)(?-i)\\s*([a-zA-Z_\\\\`0-9]+" +
            "\\.{0,1}[a-zA-Z_\\`0-9]*)\\s*\\({0,1}\\s*(([a-zA-Z_\\`0-9]+\\s+[a-zA-Z]+\\s*,*\\s*)*)\\s*\\){0,1}\\s*" +
            ";{0,1}\\s*";

    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] groups = SyntaxValidator.validateAndGroupInput(context.getInput(), CREATE_PATTERN);

        if (groups[0].toLowerCase().equals("table")) {
            context.setCommand(CREATE_TABLE);
        } else {
            context.setCommand(CREATE_DATABASE);
        }

        context.setTargetName(groups[1]);
        context.setColumnsAndTypes(groups[2]);

        InterpreterFactory.getInterpreter(InterpreterFactory.TARGET_INTERPRETER).interpret(context);
        InterpreterFactory.getInterpreter(InterpreterFactory.COLUMNS_AND_TYPES_INTERPRETER).interpret(context);
    }


}
