package eg.edu.alexu.csd.oop.calculator.cs49.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a calculator, that computes mathematical formulas.
 */
public class Calculator implements eg.edu.alexu.csd.oop.calculator.Calculator {
    /**
     * File name in which the history of the operations
     * is saved and loaded from.
     */
    private static final String FILE_NAME = "Calculator History";
    /**
     * Parser of the string input into a valid formula for computing.
     */
    private FormulaParser parser;
    /**
     * The collection of the formulas, representing the history of user
     * operations.
     */
    private ArrayList<Formula> formulas;
    /**
     * The index of the current operation.
     */
    private int curr;
    /**
     * The maximum number of operations allowed for the history
     * container to have.
     */
    public static final int MAX_SIZE = 5;
    /**
     * The index of the first element in the history.
     */
    private static final int FIRST_ELEMENT = 0;


    /**
     * Constructor of an empty Calculator.
     */
    public Calculator() {
        parser = new SimpleFormulaParser();
        formulas = new ArrayList<>();
        curr = -1;
    }

    /**
     * Method that permits a client to pass some formula in string format.
     *
     * @param formulaString string representing the formula.
     */
    @Override
    public final void input(final String formulaString) {
        Formula formula = parser.parseFormula(formulaString);
        formulas.add(formula);
        ensureFormulasCapacity();
        curr = formulas.size() - 1;
    }

    /**
     * Ensures that the maximum capacity of the history is not exceeded.
     */
    private void ensureFormulasCapacity() {
        if (formulas.size() > MAX_SIZE) {
            formulas.remove(FIRST_ELEMENT);
        }
    }

    /**
     * Computes the result of the current operation
     * in the calculator and returns the result.
     *
     * @return a string representing the result of the operation.
     */
    @Override
    public final String getResult() {
        return formulas.get(curr).compute();
    }

    /**
     * Getter for the current operation of the calculator.
     *
     * @return string representation of the formula, null if no prior input
     * has been passed to the calculator.
     */
    @Override
    public final String current() {
        try {
            return formulas.get(curr).toString();
        } catch (final IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public final String prev() {
        if (curr > 0) {
            curr--;
            return formulas.get(curr).toString();
        } else {
            return null;
        }

    }

    @Override
    public final String next() {
        if (curr < formulas.size() - 1) {
            curr++;
            return formulas.get(curr).toString();
        } else {
            return null;
        }


    }

    @Override
    public final void save() {
        CalculatorParserAdapter.save(curr, getLastFiveFormulas(), FILE_NAME);
    }


    /**
     * Gets the last five formulas or less in the history container.
     *
     * @return a list containing the last five formulas.
     */
    private List<Formula> getLastFiveFormulas() {
        if (formulas.size() >= Calculator.MAX_SIZE) {
            return formulas.subList(formulas.size() - Calculator.MAX_SIZE,
                    formulas.size());
        } else {
            return formulas.subList(0, formulas.size());
        }
    }


    @Override
    public final void load() {
        List<String> history = CalculatorParserAdapter.loadFile(FILE_NAME);
        formulas.clear();
        int currLocal = Integer.valueOf(history.get(FIRST_ELEMENT));
        history.remove(FIRST_ELEMENT);
        for (String operation : history) {
            input(operation);
        }
        this.curr = currLocal;
    }

}
