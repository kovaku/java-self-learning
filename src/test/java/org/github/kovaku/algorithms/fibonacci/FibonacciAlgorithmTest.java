package org.github.kovaku.algorithms.fibonacci;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.github.kovaku.BaseTest;
import org.testng.annotations.Test;

public class FibonacciAlgorithmTest extends BaseTest {

  private Integer[] fibonacciArray = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};

  @Test
  public void fibonacciTest() {
    for (var i = 0; i < fibonacciArray.length; i++) {
      assertThat(recursiveFibonacci(i), is(fibonacciArray[i]));
      assertThat(iterativeFibonacci(i), is(fibonacciArray[i]));
      assertThat(iterativeOptimizedFibonacci(i), is(fibonacciArray[i]));
    }
  }

  /**
   * Returns the fibonacci number using iteration an constant memory usage.
   */
  private Integer iterativeOptimizedFibonacci(Integer order) {
    var previous = 0;
    var next = 1;
    var nOrder = 0;
    for (int i = 0; i < order; i++) {
      previous = next;
      next = nOrder;
      nOrder = previous + next;
    }
    return nOrder;
  }

  /**
   * Returns the fibonacci number using iteration an linear memory usage.
   */
  private int iterativeFibonacci(int order) {
    int fibs[] = new int[order + 2];
    fibs[0] = 0;
    fibs[1] = 1;
    for (var i = 2; i <= order; i++) {
      fibs[i] = fibs[i - 2] + fibs[i - 1];
    }
    return fibs[order];
  }

  /**
   * Returns the fibonacci number using recursion.
   *
   * @return fibonacci value
   */
  private Integer recursiveFibonacci(Integer order) {
    if (order == 0 || order == 1) {
      return order;
    } else {
      return recursiveFibonacci(order - 2) + recursiveFibonacci(order - 1);
    }
  }
}
