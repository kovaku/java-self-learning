package org.github.kovaku.algorithms.prime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.stream.IntStream;
import org.github.kovaku.BaseTest;
import org.testng.annotations.Test;

public class PrimeAlgorithmTest extends BaseTest {

    public static final Integer MAX_TO_TEST = 30;
    private Integer[] primeArray = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};

    @Test
    public void primeTest() {
        for (int i = 1; i <= MAX_TO_TEST; i++) {
            if (isPrime(i)) {
                assertThat(i, is(in(primeArray)));
            } else {
                assertThat(i, is(not(in(primeArray))));
            }
        }
    }

    @Test
    public void sumOfThePrimes() {
        int sumOfInts = IntStream.rangeClosed(0, MAX_TO_TEST)
            .filter(this::isPrime)
            .reduce((sum, i) -> sum + i)
            .orElse(-1);
        assertThat(sumOfInts, equalTo(129));
    }

    private boolean isPrime(final Integer number) {
        if (number < 2) {
            return false;
        }
        for (var i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
