package eg.edu.alexu.csd.oop.calculator.cs49.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple formulas parser.
 * It can parse only simple formulas with two numerical operands,
 * and one of these operators [ + - * / ].
 */
public class SimpleFormulaParser extends FormulaParser {
    /**
     * Constant denoting the index of the first operand.
     */
    private static final int FIRST_OPERAND_GROUP = 1;
    /**
     * Constant denoting the index of the first operand.
     */
    private static final int SECOND_OPERAND_GROUP = 3;
    /**
     * Constant denoting the index of the first operand.
     */
    private static final int OPERATOR_GROUP = 2;

    @Override
    public final Formula parseFormula(final String formula) {
        setPattern(Pattern.compile("(\\-?\\d+\\.?\\d*)([\\-\\+\\*\\/])"
                + "(\\-?\\d+\\.?\\d*)"));
        Matcher matcher = getPattern().matcher(formula);
        matcher.find();
        String first = matcher.group(FIRST_OPERAND_GROUP);
        String operator = matcher.group(OPERATOR_GROUP);
        String second = matcher.group(SECOND_OPERAND_GROUP);
        Operand firstOperand = new DoubleOperand(first);
        Operand secondOperand = new DoubleOperand(second);
        return new Formula(firstOperand, secondOperand, operator);
    }
}
