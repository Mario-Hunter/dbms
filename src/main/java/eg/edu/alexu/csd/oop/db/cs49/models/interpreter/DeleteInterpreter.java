package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

public class DeleteInterpreter implements Interpreter {
    public static final String DELETE_PATTERN = "\\s*(?i)(delete)\\s+from(?-i)\\s+([a-zA-z_\\'\\`0-9]+\\" +
            ".*[a-zA-Z_\\'\\`0-9]*)\\s+(?i)where(?-i)\\s*([a-zA-Z\\`\\s0-9_]+[=<>]{1,2}[a-zA-Z_0-9\\s\\']+)" +
            "\\s*;{0,1}\\s*";
    public static final String DELETE_PATTERN_WITH_NO_PATTERN = "\\s*(?i)(delete)\\s+from(?-i)\\s+([a-zA-z_\\'\\`0-9]+\\" +
            ".*[a-zA-Z_\\'\\`0-9]*)\\s*;{0,1}\\s*";

    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] groups;

        try{
            groups = SyntaxValidator.validateAndGroupInput(context.getInput(),DELETE_PATTERN);

        }catch (InvalidQuerySyntaxException e){
            groups = SyntaxValidator.validateAndGroupInput(context.getInput(),DELETE_PATTERN_WITH_NO_PATTERN);

        }

        context.setCommand(groups[0].toLowerCase());
        context.setTargetName(groups[1]);

        if(groups.length>=3){
            context.setConditions(groups[2]);
        }else{
            context.setConditions("*");
        }

        InterpreterFactory.getInterpreter(InterpreterFactory.TARGET_INTERPRETER).interpret(context);
        InterpreterFactory.getInterpreter(InterpreterFactory.CONDITION).interpret(context);
    }
}
