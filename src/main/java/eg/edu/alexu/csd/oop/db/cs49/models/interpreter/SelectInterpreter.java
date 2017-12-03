package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

public class SelectInterpreter implements Interpreter {
    public static final String SELECT_PATTERN = "\\s*([a-zA-Z]+)\\s+((\\*)|([a-zA-Z_0-9\\`]+,{0,1})+)" +
            "\\s+(?i)from(?-i)\\s+([a-zA-Z_0-9\\`]+\\.{0,1}[a-zA-Z_0-9\\`]*)\\s*(?i)where(?-i)\\s*" +
            "([a-zA-Z\\`\\s0-_9]+[=<>]{1,2}[a-zA-Z_0-9\\s\\']+)\\s*;{0,1}\\s*";

    public static final String SELECT_PATTERN_WITH_NO_CONDITION = "\\s*([a-zA-Z]+)\\s+((\\*)|([a-zA-Z_0-9\\`]+,{0,1})+)" +
            "\\s+(?i)from(?-i)\\s+([a-zA-Z_0-9\\`]+\\.{0,1}[a-zA-Z_0-9\\`]*)\\s*;{0,1}\\s*";

    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] groups;

        try{
            groups = SyntaxValidator.validateAndGroupInput(context.getInput(),SELECT_PATTERN);

        }catch (InvalidQuerySyntaxException e){
            groups = SyntaxValidator.validateAndGroupInput(context.getInput(),SELECT_PATTERN_WITH_NO_CONDITION);

        }

        context.setCommand(groups[0].toLowerCase());
        context.setTargetName(groups[4]);
        context.setColumns(groups[1]);

        if(groups.length>=6){
            context.setConditions(groups[5]);
        }else{
            context.setConditions("*");
        }


        InterpreterFactory.getInterpreter(InterpreterFactory.TARGET_INTERPRETER).interpret(context);
        InterpreterFactory.getInterpreter(InterpreterFactory.CONDITION).interpret(context);
        InterpreterFactory.getInterpreter(InterpreterFactory.SELECT_CONDITION).interpret(context);

    }
}
