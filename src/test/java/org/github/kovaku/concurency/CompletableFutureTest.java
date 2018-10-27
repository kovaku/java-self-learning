package org.github.kovaku.concurency;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.github.kovaku.functions.FibonacciTest;
import org.testng.annotations.Test;

public class CompletableFutureTest {

  private static final Integer TEST_INTEGER = 40;
  private static final Long EXPECTED_TEST_RESULT = 102334155L;

  private FibonacciTest fibonacci = new FibonacciTest();

  @Test(description = "Concurrency - Testing the CompletableFuture feature")
  public void testCompletableFuture() throws ExecutionException, InterruptedException {
    CompletableFuture<Long> completableFuture = new CompletableFuture<>();
    Executors.newCachedThreadPool().submit(() -> {
      Long result = fibonacci.fibonacciRecursive.apply(TEST_INTEGER);
      completableFuture.complete(result);
    });

    assert completableFuture.get().equals(EXPECTED_TEST_RESULT);
  }

  @Test(description = "Concurrency - Testing the CompletableFuture - Cancelling calculation",
      expectedExceptions = CancellationException.class)
  public void testCompletableFuture2() throws ExecutionException, InterruptedException {
    CompletableFuture<Long> completableFuture = new CompletableFuture<>();
    Executors.newCachedThreadPool().submit(() -> completableFuture.cancel(true));
    completableFuture.get();
  }

  @Test(description = "Concurrency - Testing the CompletableFuture - chaining tasks")
  public void testCompletableFuture3() throws ExecutionException, InterruptedException {
    CompletableFuture<Long> completableFuture = CompletableFuture
        .supplyAsync(() -> fibonacci.fibonacciRecursive.apply(TEST_INTEGER))
        .thenApplyAsync(input -> input - fibonacci.fibonacciIterative.apply(TEST_INTEGER));

    assert completableFuture.get().equals(0L);
  }

  @Test(description = "Concurrency - Testing the CompletableFuture - joining CompletableFutures 1")
  public void testCompletableFuture4() throws ExecutionException, InterruptedException {
    CompletableFuture<Long> recursiveFuture = CompletableFuture
        .supplyAsync(() -> fibonacci.fibonacciRecursive.apply(TEST_INTEGER));

    CompletableFuture<Long> recursiveWithTempArrayFuture = CompletableFuture
        .supplyAsync(() -> fibonacci.fibonacciWithTempArray.apply(TEST_INTEGER));

    CompletableFuture<Long> iterativeFuture = CompletableFuture
        .supplyAsync(() -> fibonacci.fibonacciIterative.apply(TEST_INTEGER));

    CompletableFuture<Void> joinedFuture = CompletableFuture
        .allOf(recursiveFuture, recursiveWithTempArrayFuture, iterativeFuture);

    joinedFuture.get();

    assert recursiveFuture.isDone();
    assert recursiveWithTempArrayFuture.isDone();
    assert iterativeFuture.isDone();

    assert recursiveFuture.get().equals(EXPECTED_TEST_RESULT);
    assert recursiveWithTempArrayFuture.get().equals(EXPECTED_TEST_RESULT);
    assert iterativeFuture.get().equals(EXPECTED_TEST_RESULT);
  }

  @Test(description = "Concurrency - Testing the CompletableFuture - joining CompletableFutures 1")
  public void testCompletableFuture5() {
    CompletableFuture<Long> recursiveFuture = CompletableFuture
        .supplyAsync(() -> fibonacci.fibonacciRecursive.apply(TEST_INTEGER));

    CompletableFuture<Long> recursiveWithTempArrayFuture = CompletableFuture
        .supplyAsync(() -> fibonacci.fibonacciWithTempArray.apply(TEST_INTEGER));

    CompletableFuture<Long> iterativeFuture = CompletableFuture
        .supplyAsync(() -> fibonacci.fibonacciIterative.apply(TEST_INTEGER));

    Boolean result = Stream.of(recursiveFuture, recursiveWithTempArrayFuture, iterativeFuture)
        .map(CompletableFuture::join).map(EXPECTED_TEST_RESULT::equals).reduce(Boolean::logicalAnd).get();

    assert result;
  }
}
