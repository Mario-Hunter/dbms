package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

class InterpreterFactory {
    public static final String CREATE_INTERPRETER = "create";
    public static final String TARGET_INTERPRETER = "target";
    public static final String COLUMNS_AND_VALUES_INTERPRETER = "values";
    public static final String COLUMNS_AND_TYPES_INTERPRETER = "types";
    public static final String DROP_INTERPRETER = "drop";
    public static final String INSERT_INTERPRETER = "insert";
    public static final String UPDATE_INTERPRETER = "update";
    public static final String CONDITION = "condition";
    public static final String DELETE = "delete";
    public static final String SELECT = "select";
    public static final String SELECT_CONDITION = "select condition";

    public static Interpreter getInterpreter(final String command) {
        switch (command) {
            case CREATE_INTERPRETER:
                return new CreateInterpreter();
            case TARGET_INTERPRETER:
                return new TargetInterpreter();
            case COLUMNS_AND_TYPES_INTERPRETER:
                return new ColumnsAndTypesInterpreter();
            case COLUMNS_AND_VALUES_INTERPRETER:
                return new ColumnsAndValuesInterpreter();
            case DROP_INTERPRETER:
                return new DropInterpreter();
            case INSERT_INTERPRETER:
                return new InsertInterpreter();
            case UPDATE_INTERPRETER:
                return new UpdateInterpreter();
            case CONDITION:
                return new ConditionInterpreter();
            case DELETE:
                return new DeleteInterpreter();
            case SELECT:
                return new SelectInterpreter();
            case SELECT_CONDITION:
                return new SelectConditionInterpreter();
        }
        return null;
    }
}
