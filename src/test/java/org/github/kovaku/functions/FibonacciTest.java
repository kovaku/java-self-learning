package org.github.kovaku.functions;

import org.testng.annotations.Test;

import java.util.function.Function;

public class FibonacciTest {

  private Function<Integer, Long> fibonacciRecursive = new Function<Integer, Long>() {
    @Override
    public Long apply(Integer i) {
      if (i == 0 || i == 1) {
        return i.longValue();
      } else {
        return apply(i - 1) + apply(i - 2);
      }
    }
  };

  private Function<Integer, Long> fibonacciIterative = i -> {
    int ii = 0;
    Long previous, next = 1L, result = 0L;
    while (ii++ < i) {
      previous = next;
      next = result;
      result = previous + next;
    }
    return result;
  };

  @Test
  public void fibonacciRecursiveTest() {
    assert fibonacciRecursive.apply(40) == 102334155;
  }

  @Test
  public void fibonacciIterativeTest() {
    assert fibonacciIterative.apply(40) == 102334155;
  }

  @Test
  public void fibonacciRecursiveWithTempArrayTest() {
    assert fibonacciRecursiveWithTempArray(40) == 102334155;
  }

  private Long fibonacciRecursiveWithTempArray(Integer i) {
    return fib(i, new Long[i + 1]);
  }

  private Long fib(Integer i, Long[] intArray) {
    if (intArray[i] == null) {
      if (i == 0 || i == 1) {
        intArray[i] = i.longValue();
      } else {
        intArray[i] = fib(i - 1, intArray) + fib(i - 2, intArray);
      }
    }
    return intArray[i];
  }
}
