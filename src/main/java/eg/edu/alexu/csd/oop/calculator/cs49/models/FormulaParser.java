package eg.edu.alexu.csd.oop.calculator.cs49.models;

import java.util.regex.Pattern;

/**
 * The abstract class of the parser of strings into formula object.
 */
public abstract class FormulaParser {
    /**
     * The pattern to match the string against.
     */
    private static Pattern pattern;

    /**
     * Parses a string equation into a formula object.
     *
     * @param formula the string representation of the formula.
     * @return the formula object.
     */
    public abstract Formula parseFormula(String formula);

    /**
     * Setter for the pattern to match the string against.
     *
     * @param patternParameter the new pattern to set.
     */
    public static void setPattern(final Pattern patternParameter) {
        FormulaParser.pattern = patternParameter;
    }

    /**
     * Getter for the pattern to match the string against.
     *
     * @return pattern object.
     */
    public static Pattern getPattern() {
        return pattern;
    }
}
