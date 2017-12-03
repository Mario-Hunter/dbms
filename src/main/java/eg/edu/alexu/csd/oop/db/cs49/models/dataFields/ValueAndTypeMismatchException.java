package eg.edu.alexu.csd.oop.db.cs49.models.dataFields;

public class ValueAndTypeMismatchException extends Throwable {
    public ValueAndTypeMismatchException(final String value, final Class cls) {
        super("The type and the value of the field are not compatible." +
                "Value: " + value + ". Type: " + cls.getSimpleName());
    }

}
