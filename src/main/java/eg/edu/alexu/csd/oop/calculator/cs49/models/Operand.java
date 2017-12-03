package eg.edu.alexu.csd.oop.calculator.cs49.models;

/**
 * An operand representation, it exposes only methods to get the numerical
 * value and the string representation of the operand.
 */
public interface Operand {
    /**
     * Computes the value of the operand.
     *
     * @return double value of the operand.
     */
    double getValue();

    /**
     * String representation of the operand.
     *
     * @return String representation of the operand.
     */
    String toString();
}
