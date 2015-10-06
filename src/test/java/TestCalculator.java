import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kurtis on 2015-10-05.
 * All the tests from the example input
 */
public class TestCalculator {
    @Test
    public void testAdd() {
        Calculator calculator = new Calculator();
        calculator.setCommand("add(1,2)");
        assertEquals("add(1,2) = 3 ", 3L, calculator.execute());
    }

    @Test
    public void testAddWithMult() {
        Calculator calculator = new Calculator();
        calculator.setCommand("add(1, mult(2,3))");
        assertEquals("add(1, mult(2,3)) = 7", 7L, calculator.execute());
    }

    @Test
    public void testMultWithDiv() {
        Calculator calculator = new Calculator();
        calculator.setCommand("mult(add(2,2),div(9,3))");
        assertEquals("mult(add(2,2),div(9,3)) = 12", 12L, calculator.execute());
    }

    @Test
    public void testLetWithAdd() {
        Calculator calculator = new Calculator();
        calculator.setCommand("let(a,5,add(5,5))");
        assertEquals("let(a,5,add(5,5)) = 10", 10L, calculator.execute());
    }

    @Test
    public void testMultLetWithMultAndAdd() {
        Calculator calculator = new Calculator();
        calculator.setCommand("let(a,5,let(b,mult(a,10),add(b,a)))");
        assertEquals("let(a,5,let(b,mult(a,10),add(b,a))) = 55", 55L, calculator.execute());
    }

    @Test
    public void testMultLetWithMultAdd() {
        Calculator calculator = new Calculator();
        calculator.setCommand("let(a,let(b,10,add(b,b)),let(b,20,add(a,b)))");
        assertEquals("let(a,let(b,10,add(b,b)),let(b,20,add(a,b))) = 40", 40L, calculator.execute());
    }
}
