package org.github.kovaku.functions;

import org.github.kovaku.BaseTest;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceTest extends BaseTest {

    private Calculation add = (a, b) -> a + b;
    private Calculation subtract = (a, b) -> a - b;
    private Calculation multiply = (a, b) -> a * b;
    private Calculation divide = (a, b) -> a / b;

    private Function<Integer, Integer> square = a -> a * a;

    private Function<Integer, Integer> minusOne = a -> a - 1;

    private Function<Integer, String> binaryPrinter = Integer::toBinaryString;

    private Supplier<Integer> randomSupplier = () -> new Random().nextInt();

    private Consumer<Integer> printerConsumer = System.out::println;

    private Predicate<Integer> randomTester = i -> i == new Random().nextInt(i + 1);

    @Test
    public void calculationTest() {
        assert calc(10, 10, add) == 20;
        assert calc(10, 10, subtract) == 0;
        assert calc(10, 10, multiply) == 100;
        assert calc(10, 10, divide) == 1;
        assert calc(10, square) == 100;
    }

    @Test
    public void andThenTest() {
        // first calculate the square of 2, then minus one
        assert square.andThen(minusOne).apply(5) == 24;

    }

    @Test
    public void composeTest() {
        // first calculate the minus one, then the square
        assert square.compose(minusOne).apply(4) == 9;
    }

    @Test
    public void binaryPrinterTest() {
        assert binaryPrinter.apply(15).equals("1111");
    }

    @Test
    public void supplierAndConsumerTest() {
        printerConsumer.accept(randomSupplier.get());
    }

    @Test
    public void predicateTest() {
        int i = 0;
        while (!randomTester.test(100)) {
            System.out.println("Number of tries: " + ++i);
        }
        System.out.println("Match after try: " + ++i);
    }

    private Integer calc(Integer a, Integer b, Calculation calc) {
        return calc.calculate(a, b);
    }

    private Integer calc(Integer a, Function<Integer, Integer> calc) {
        return calc.apply(a);
    }
}
