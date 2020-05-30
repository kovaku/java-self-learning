package org.github.kovaku.designpatterns.creational.strategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import org.github.kovaku.designpatterns.creational.strategy.impl.Addition;
import org.github.kovaku.designpatterns.creational.strategy.impl.Division;
import org.github.kovaku.designpatterns.creational.strategy.impl.Multiplication;
import org.github.kovaku.designpatterns.creational.strategy.impl.Subtraction;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CalculatorTest {

    private Calculator underTest;
    private static final Double X = 1.0;
    private static final Double Y = 2.0;

    @BeforeClass
    public void before() {
        List<Operation> operations = List.of(
            new Addition(),
            new Subtraction(),
            new Multiplication(),
            new Division());
        underTest = new Calculator(operations);
    }

    @Test
    public void testAddition() {
        //Given
        //When
        Double result = underTest.doCalculation(X, Y, "+");

        //Then
        assertThat(result, is(3.0));
    }

    @Test
    public void testSubtraction() {
        //Given
        //When
        Double result = underTest.doCalculation(X, Y, "-");

        //Then
        assertThat(result, is(-1.0));
    }

    @Test
    public void testDivision() {
        //Given
        //When
        Double result = underTest.doCalculation(X, Y, "/");

        //Then
        assertThat(result, is(0.5));
    }

    @Test
    public void testMultiplication() {
        //Given
        //When
        Double result = underTest.doCalculation(X, Y, "*");

        //Then
        assertThat(result, is(2.0));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testUnsupportedOperationException() {
        //Given
        //When
        Double result = underTest.doCalculation(X, Y, "^");

        //Then
    }
}
