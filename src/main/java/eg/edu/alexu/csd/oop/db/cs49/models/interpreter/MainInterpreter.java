package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

public class MainInterpreter {
    public static void interpret(Context context) throws InvalidQuerySyntaxException {
        String input = context.getInput();
        Interpreter interpreter = InterpreterFactory.getInterpreter(input.trim().split(" ")[0].toLowerCase());
        interpreter.interpret(context);
    }
}
