package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

import java.util.Stack;

public class ConditionInterpreter implements Interpreter {
    public static final String CONDITION_PATTERN = "([a-zA-Z\\`\\s0-9]+)([=<>]{1,2})([a-zA-Z_0-9\\']+)";

    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String conditions = context.getConditions().toLowerCase().trim();

//        String[] groups = SyntaxValidator.validateAndGroupInput(conditions.trim(), CONDITION_PATTERN);

        Stack<String> postfixConditions = new Stack();
        postfixConditions.push(conditions);


        context.setPostfixConditions(postfixConditions);
    }
}
