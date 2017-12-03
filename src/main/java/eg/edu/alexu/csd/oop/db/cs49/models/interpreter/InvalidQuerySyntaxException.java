package eg.edu.alexu.csd.oop.db.cs49.models.interpreter;

import java.sql.SQLException;

public class InvalidQuerySyntaxException extends SQLException {
    public InvalidQuerySyntaxException(String message) {
        super(message);
    }
}
