package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import eg.edu.alexu.csd.oop.db.cs49.models.Context;

public interface Interpreter {
    void interpret(Context context) throws InvalidQuerySyntaxException;
}
