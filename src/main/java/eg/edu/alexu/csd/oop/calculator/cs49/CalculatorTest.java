package eg.edu.alexu.csd.oop.calculator.cs49;

import eg.edu.alexu.csd.oop.calculator.cs49.models.Calculator;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class CalculatorTest extends TestCase {
    Calculator calc;

    public CalculatorTest() {
    }

    @BeforeEach
    protected void setUp() {
        calc = new Calculator();
    }

    @Test
    public void testSaveAndLoadWithHistory() {
        calc.input("2+5");
        calc.input("3+5");
        calc.input("7+8");
        calc.input("7+8");
        calc.input("7+8");
        calc.input("7+8");
        calc.save();
        calc.input("3*2");
        calc.save();
        calc.input("7+8");
        calc.prev();
        calc.prev();
        calc.prev();
        calc.prev();
        calc.prev();
        calc.prev();
        assertNull(calc.prev());
    }



}