package eg.edu.alexu.csd.oop.db.cs49.models.filter.crieterias;

public class InvalidCondition extends Exception {
    public InvalidCondition() {
        super();
    }

    public InvalidCondition(final Throwable cause) {
        super(cause);
    }

    public InvalidCondition(final String message) {
        super(message);
    }
}
