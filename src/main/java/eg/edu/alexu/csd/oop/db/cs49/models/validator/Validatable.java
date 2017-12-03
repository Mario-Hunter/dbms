package eg.edu.alexu.csd.oop.db.cs49.models.validator;


public interface Validatable {
    boolean validate(Validatable roleModel);
    Object getValidationField();
}
