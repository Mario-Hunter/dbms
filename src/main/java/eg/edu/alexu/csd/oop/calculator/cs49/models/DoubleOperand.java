package eg.edu.alexu.csd.oop.calculator.cs49.models;

/**
 * An operand that represents pure double real numbers.
 */
public class DoubleOperand implements Operand {
    /**
     * The value of the operand.
     */
    private double value;

    /**
     * Constructor of the Class.
     *
     * @param valueParameter initial value of the operand.
     */
    public DoubleOperand(final double valueParameter) {
        this.value = valueParameter;
    }

    /**
     * Construtor of the Class.
     *
     * @param valueParamter string representation of the double value.
     */
    public DoubleOperand(final String valueParamter) {
        try {
            this.value = Double.valueOf(valueParamter);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            //TODO handle the exception
        }

    }

    /**
     * String representation of the Operand.
     *
     * @return string representing the operand value.
     */
    @Override
    public final String toString() {
        return String.valueOf(value);
    }

    /**
     * Getter for the value of the operand.
     *
     * @return double value of the operand.
     */
    @Override
    public final double getValue() {
        return value;
    }
}
