package org.github.kovaku.functions;

import java.util.function.Function;
import org.github.kovaku.BaseTest;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

@Component("fibonacci")
public class FibonacciTest extends BaseTest {

  public Function<Integer, Long> fibonacciRecursive = new Function<>() {
    @Override
    public Long apply(Integer i) {
      if (i == 0 || i == 1) {
        return i.longValue();
      } else {
        return apply(i - 1) + apply(i - 2);
      }
    }
  };

  public Function<Integer, Long> fibonacciIterative = i -> {
    int ii = 0;
    Long previous, next = 1L, result = 0L;
    while (ii++ < i) {
      previous = next;
      next = result;
      result = previous + next;
    }
    return result;
  };

  public Function<Integer, Long> fibonacciWithTempArray = i -> fib(i, new Long[i + 1]);

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
    assert fibonacciWithTempArray.apply(40) == 102334155;
  }
}
