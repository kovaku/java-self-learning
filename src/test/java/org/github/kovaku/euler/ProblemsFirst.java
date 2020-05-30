package org.github.kovaku.euler;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.github.kovaku.BaseTest;
import org.testng.annotations.Test;

public class ProblemsFirst extends BaseTest {

  private BiPredicate<Long, Long> isMultipleOf = (number, base) -> number % base == 0;
  private Predicate<Long> isEven = number -> isMultipleOf.test(number, 2L);

  @Test
  public void multiplesOfThreeAndFive() {
    Long result = LongStream
        .range(0, 1000)
        .boxed()
        .filter((number) -> isMultipleOf.test(number, 3L) || isMultipleOf.test(number, 5L))
        .reduce(0L, Long::sum);
    System.out.println(result);
  }

  @Test
  public void sumOfEvenFibonacciNumbersUnderFourMillion() {
    Long result = LongStream
        .iterate(0, n -> n + 1)
        .map(this::recursiveFibonacci)
        .filter(isEven::test)
        .takeWhile(element -> element < 4000000)
        .reduce(0, Long::sum);
    System.out.println(result);
  }

  @Test
  public void largestPrimeFactorOf600851475143() {
    Long numberToTest = 600851475143L;
    Long result = numberToTest;
    while (!isPrime(result)) {
      Long i = 2L;
      while (i < result) {
        if (isPrime(i) && isMultipleOf.test(result, i)) {
          result = result / i;
          i = 2L;
        } else {
          i++;
        }
      }
    }
    System.out.println(result);
  }

  @Test
  public void largestPalindromeProductOfTwoThreeDigitNumber() {
    int smallestThreeDigitNumber = 100;
    int highestThreeDigitNumber = 999;
    Integer result = IntStream
        .rangeClosed(smallestThreeDigitNumber, highestThreeDigitNumber)
        .boxed()
        .map(i -> IntStream
            .rangeClosed(smallestThreeDigitNumber, highestThreeDigitNumber)
            .boxed()
            .map(j -> j * i)
            .collect(Collectors.toList()))
        .flatMap(Collection::stream)
        .sorted(Comparator.reverseOrder())
        .filter(this::isPalindrome)
        .findFirst()
        .get();
    System.out.println(result);
  }

  @Test
  public void sumSquareDifference() {
    Long squareAndSum = LongStream
        .rangeClosed(1, 100)
        .map(x -> Double.valueOf(Math.pow(x, 2)).longValue())
        .reduce(0, Long::sum);
    Long sumAndSquare = LongStream
        .rangeClosed(1, 100)
        .reduce(0, Long::sum);
    System.out.println(Double.valueOf(Math.pow(sumAndSquare, 2)).longValue() - squareAndSum);
  }

  @Test
  public void theTenThousandFirstPrime() {
    int desiredPrimeOrder = 10001;
    Long result = LongStream
        .iterate(0, n -> n + 1)
        .boxed()
        .filter(this::isPrime)
        .limit(desiredPrimeOrder)
        .skip(desiredPrimeOrder - 1)
        .findAny()
        .get();
    System.out.println(result);
  }

  @Test
  public void summationOfPrimesUnderTwoMillion() {
    int upperBoundary = 2000000;
    Long result = LongStream
        .range(2, upperBoundary)
        .boxed()
        .filter(this::isPrime)
        .peek(System.out::println)
        .reduce(0L, Long::sum);
    System.out.println(result);
  }

  @Test
  public void highlyDivisibleTriangularNumber() {
    Integer result = IntStream
        .iterate(1, i -> i + 1)
        .boxed()
        .map(i -> IntStream
            .rangeClosed(1, i)
            .reduce(0, Integer::sum))
        .filter(number -> {
          Long count = IntStream
              .rangeClosed(1, number)
              .boxed()
              .map(i -> isMultipleOf.test(Long.valueOf(number), Long.valueOf(i)))
              .filter(Boolean::booleanValue)
              .count();
          System.out.println(String.join(",", number.toString(), count.toString()));
          return count >= 500L;
        })
        .findFirst()
        .get();
    System.out.println(result);
  }

  private Long recursiveFibonacci(Long order) {
    if (order == 0 || order == 1) {
      return order;
    } else {
      return recursiveFibonacci(order - 2) + recursiveFibonacci(order - 1);
    }
  }

  private boolean isPrime(final Long number) {
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

  private boolean isPalindrome(Integer number) {
    String numberToTest = String.valueOf(number);
    String reversedNumberToTest = new StringBuilder(numberToTest).reverse().toString();
    return Objects.equals(numberToTest, reversedNumberToTest);
  }
}
