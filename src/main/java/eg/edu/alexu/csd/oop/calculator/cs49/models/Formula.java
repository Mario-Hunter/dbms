package eg.edu.alexu.csd.oop.calculator.cs49.models;

/**
 * Formula representation containing two operands and an operator.
 * The formula itself is an operand to allow for more complex forms to be
 * represented.
 */
public class Formula implements Operand {

    /**
     * Constant denoting the addition operation.
     */
    public static final int ADDITION = 0;
    /**
     * Constant denoting the subtraction operation.
     */
    public static final int SUBTRACTION = 1;
    /**
     * Constant denoting the multiplication operation.
     */
    public static final int MULTIPLICATION = 2;
    /**
     * Constant denoting the division operation.
     */
    public static final int DIVISION = 3;

    /**
     * The left operand in the formula.
     */
    private Operand first;
    /**
     * The right operand in the formula.
     */
    private Operand second;
    /**
     * Integer representing the operator, takes values of the constant fields
     * defined in the class.
     */
    private int operator;

    /**
     * Constructor of the formula.
     *
     * @param firstParameter    The left operand.
     * @param secondParameter   The right operand.
     * @param operatorParameter string representing the operator.
     */
    public Formula(final Operand firstParameter, final Operand secondParameter,
                   final String operatorParameter) {
        this.first = firstParameter;
        this.second = secondParameter;
        switch (operatorParameter) {
            case "+":
                this.operator = ADDITION;
                break;
            case "-":
                this.operator = SUBTRACTION;
                break;
            case "*":
                this.operator = MULTIPLICATION;
                break;
            case "/":
                this.operator = DIVISION;
                break;
            default:
                //TODO handle not valid operator

        }
    }

    @Override
    public final double getValue() {
        switch (operator) {
            case ADDITION:
                return computeAddition();
            case SUBTRACTION:
                return computeSubtraction();
            case MULTIPLICATION:
                return computeMultiplication();
            case DIVISION:
                return computeDivision();
            default:
                return Double.parseDouble(null);
        }
    }

    /**
     * Compute the value of the formula.
     *
     * @return string representation of the result.
     */
    public final String compute() {
        return String.valueOf(getValue());
    }

    /**
     * formats the double value by removing the redundant .0 at the end.
     *
     * @param value the double value to format.
     * @return formatted string representation.
     */
    private String formatValueOf(final double value) {
        String stringValue = String.valueOf(value);
        return format(stringValue);

    }

    /**
     * Removes the redundant .0 at the end of the string, if any.
     *
     * @param stringValue the string to be formatted.
     * @return the string formatted.
     */
    private String format(final String stringValue) {
        if (stringValue.endsWith(".0")) {
            return stringValue.substring(0, stringValue.length() - 2);
        }
        return stringValue;
    }

    /**
     * Computes the division operation.
     *
     * @return double value of the result.
     */
    private double computeDivision() {
        return first.getValue() / second.getValue();
        //TODO add illegal division exception
    }

    /**
     * Computes the multiplication operation.
     *
     * @return double value of the result.
     */
    private double computeMultiplication() {
        return first.getValue() * second.getValue();
    }

    /**
     * Computes the subtraction operation.
     *
     * @return double value of the result.
     */
    private double computeSubtraction() {
        return first.getValue() - second.getValue();
    }

    /**
     * Computes the addition operation.
     *
     * @return double value of the result.
     */
    private double computeAddition() {
        return first.getValue() + second.getValue();
    }

    @Override
    public final String toString() {
        return format(first.toString()) + valueOfOperator()
                + format(second.toString());
    }

    /**
     * The string representation of the operator of the formula.
     *
     * @return string the operator in string format.
     */
    private String valueOfOperator() {
        switch (operator) {
            case ADDITION:
                return "+";
            case SUBTRACTION:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            default:
                return String.valueOf(null);
        }
    }
}
