package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

public class UpdateInterpreter implements Interpreter {
    public static final String UPDATE_PATTERN = "\\s*([a-zA-Z_0-9]+)\\s+([a-zA-Z_0-9\\`]+\\.{0,1}[a-zA-Z_0-9\\`]*)\\s+" +
            "(?i)set(?-i)\\s+(([a-zA-Z\\`\\s0-9_]+\\s*=[a-zA-Z\\'\\s0-9]+\\s*,{0,1})+)\\s*(?i)where(?-i)\\s*" +
            "([a-zA-Z\\`\\s0-9_]+[=<>]{1,2}[a-zA-Z_0-9\\s\\']+)\\s*;{0,1}\\s*";
    public static final String UPDATE_PATTERN_WITH_NO_CONDITION = "\\s*([a-zA-Z_0-9]+)\\s+([a-zA-Z_0-9\\`]+\\.{0," +
            "1}[a-zA-Z_0-9\\`]*)\\s+" +
            "(?i)set(?-i)\\s+(([a-zA-Z\\`\\s0-9_]+\\s*=[a-zA-Z\\'\\s0-9]+\\s*,{0,1})+)\\s*;{0,1}\\s*";

    @Override
    public void interpret(final Context context) throws InvalidQuerySyntaxException {
        String[] groups;
        try {
            groups = SyntaxValidator.validateAndGroupInput(context.getInput(), UPDATE_PATTERN);
        } catch (InvalidQuerySyntaxException e) {
            groups = SyntaxValidator.validateAndGroupInput(context.getInput(), UPDATE_PATTERN_WITH_NO_CONDITION);

        }

        context.setCommand(groups[0].toLowerCase());
        context.setTargetName(groups[1]);

        String updatedValues = groups[2];
        String[] updatedValuesArray = updatedValues.split(",");

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (String updatedValue : updatedValuesArray) {
            String[] colVal = updatedValue.trim().split("=");
            if (colVal.length != 2) {
                throw new InvalidQuerySyntaxException("Types and values pairs error");
            }
            columns.append(colVal[0].toLowerCase().trim() + ",");
            values.append(colVal[1].toLowerCase().trim() + ",");
        }

        context.setColumns(columns.toString());
        context.setValues(values.toString());
        if(groups.length < 5){
            context.setConditions("*");
        }else{
            context.setConditions(groups[4]);
        }

        InterpreterFactory.getInterpreter(InterpreterFactory.TARGET_INTERPRETER).interpret(context);
        InterpreterFactory.getInterpreter(InterpreterFactory.COLUMNS_AND_VALUES_INTERPRETER).interpret(context);
        InterpreterFactory.getInterpreter(InterpreterFactory.CONDITION).interpret(context);

    }
}
