package eg.edu.alexu.csd.oop.calculator.cs49.models;

import eg.edu.alexu.csd.oop.Utilities.FileParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Adapter to format the data between the file parser and the calculator class.
 */
public final class CalculatorParserAdapter {

    /**
     * Private constructor for the utility class.
     */
    private CalculatorParserAdapter() {
    }

    /**
     * Extract the string representation of a list of formulas.
     *
     * @param formulas the list of formulas to represent.
     * @return string representation of the formulas. Each one
     * in a separate line.
     */
    public static String extractStrings(final List<Formula> formulas) {
        StringBuilder sb = new StringBuilder();
        for (Formula formula : formulas) {
            sb.append(formula.toString());
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Saves the calculator history in a file.
     *
     * @param curr             the index of the current operation.
     * @param lastFiveFormulas the list of formulas to save.
     * @param fileName         the name of the file to save in.
     */
    public static void save(final int curr,
                            final List<Formula> lastFiveFormulas,
                            final String fileName) {
        int normalizedCurr = curr;
        if (curr >= Calculator.MAX_SIZE) {
            normalizedCurr = Calculator.MAX_SIZE - 1;
        }
        FileParser.save(String.valueOf(normalizedCurr) + "\n"
                + extractStrings(lastFiveFormulas), fileName);
    }

    /**
     * Loads the history of a calculator from a file.
     *
     * @param fileName the file name to load from.
     * @return list of strings, first entry is always the current index,
     * next entries consist of the formulas.
     */
    public static List<String> loadFile(final String fileName) {
        String history = FileParser.load(fileName);
        Scanner in = new Scanner(history);
        List<String> formulas = new ArrayList<>();
        while (in.hasNextLine()) {
            formulas.add(in.nextLine());
        }
        return formulas;
    }
}
