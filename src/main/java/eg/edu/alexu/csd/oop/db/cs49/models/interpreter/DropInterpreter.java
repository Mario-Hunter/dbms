package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

import static eg.edu.alexu.csd.oop.db.cs49.models.Context.*;


public class DropInterpreter implements Interpreter {
    private static final String DROP_PATTERN =  "\\s*(?i)drop\\s+(table|database)(?-i)\\s*([a-zA-Z_\\\\`0-9]+\\.{0,1}" +
            "[a-zA-Z_\\`0-9]*)\\s*;{0,1}\\s*";

    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] groups = SyntaxValidator.validateAndGroupInput(context.getInput(), DROP_PATTERN);


        if (groups[0].toLowerCase().equals("table")) {
            context.setCommand(DROP_TABLE);
        } else {
            context.setCommand(DROP_DATABASE);
        }

        context.setTargetName(groups[1]);

        InterpreterFactory.getInterpreter(InterpreterFactory.TARGET_INTERPRETER).interpret(context);

    }
}
