package org.github.kovaku.concurency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.testng.annotations.Test;

public class ConcurrencyTest {

  private static final String THREAD_NAME = "MyThread";
  private static Integer iteratorForSyncronizedAccess = 0;
  private static AtomicInteger iterator = new AtomicInteger(0);
  private static final Integer DEFAULT_INTEGER_VALUE = 5;
  private ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();
  private InheritableThreadLocal<Integer> integerInheritableThreadLocal = new InheritableThreadLocal<>();

  @Test(description = "Thread testing - Extending Thread")
  public void newTreadShouldBeStarted1() {
    new MyThread(THREAD_NAME).start();
  }

  @Test(description = "Thread testing - Anonymous class")
  public void newTreadShouldBeStarted2() {
    Thread myThread = new Thread(THREAD_NAME) {
      @Override
      public void run() {
        assert Thread.currentThread().getName().equals(THREAD_NAME);
      }
    };
    myThread.start();
  }

  @Test(description = "Thread testing - Implementing runnable interface")
  public void newTreadShouldBeStarted3() {
    Thread myThread = new Thread(() -> {
      assert Thread.currentThread().getName().equals(THREAD_NAME);
    }, THREAD_NAME);
    myThread.start();
  }

  @Test(description = "Concurrency - Threads are incrementing the same iterator - using synchronized block")
  public void testConcurrencyIteratingTheSameIteratorSync() throws InterruptedException {
    iteratorForSyncronizedAccess = 0;
    Runnable runnable = () -> {
      for (var i = 0; i < 100000; i++) {
        increment();
      }
    };
    List<Thread> myThreads = List.of(
        new Thread(runnable),
        new Thread(runnable),
        new Thread(runnable)
    );
    myThreads.forEach(Thread::start);
    for (Thread myThread : myThreads) {
      myThread.join();
    }
    assert iterator.get() == 300000;
  }

  @Test(description = "Concurrency - Threads are incrementing the same iterator - using Atomic object")
  public void testConcurrencyIteratingTheSameIteratorAtomic() throws InterruptedException {
    IncrementerThread incrementerThread1 = new IncrementerThread();
    IncrementerThread incrementerThread2 = new IncrementerThread();
    IncrementerThread incrementerThread3 = new IncrementerThread();

    incrementerThread1.start();
    incrementerThread2.start();
    incrementerThread3.start();

    incrementerThread1.join();
    incrementerThread2.join();
    incrementerThread3.join();

    assert iterator.get() == 300000;
  }

  @Test(description = "Concurrency - Testing the functionality the ThreadLocal")
  public void testThreadLocal() {
    List<Thread> threads = new ArrayList<>();
    Runnable runnable = () -> {
      integerThreadLocal.set(new Random().nextInt(DEFAULT_INTEGER_VALUE));
      assert !integerThreadLocal.get().equals(DEFAULT_INTEGER_VALUE);
    };
    IntConsumer consumer = (index) -> {
      Thread thread = new Thread(runnable, "Thread index: " + index);
      threads.add(thread);
      thread.start();
    };
    //Main thread sets its value for the ThreadLocal
    integerThreadLocal.set(DEFAULT_INTEGER_VALUE);

    //Threads are executed and set their value in the ThreadLocal
    IntStream.range(1, 10).forEach(consumer);

    //Wait for the Threads to finish
    threads.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    //The set value of the main thread should remain the same
    assert integerThreadLocal.get().equals(DEFAULT_INTEGER_VALUE);
  }

  @Test(description = "Concurrency - Testing the InheritableThreadLocal")
  public void testInheritableThreadLocal() throws InterruptedException {
    integerInheritableThreadLocal.set(DEFAULT_INTEGER_VALUE);
    Runnable runnable = () -> {
      //Each of the child threads should inherit the preset value
      assert integerInheritableThreadLocal.get().equals(DEFAULT_INTEGER_VALUE);
    };

    IntConsumer consumer = (index) -> {
      Thread thread = new Thread();
      thread.start();
    };

    IntStream.range(1, 10).forEach(consumer);

    Thread.sleep(1000);
  }

  @Test(description = "Concurrency - Testing the ThreadPoolExecutor")
  public void testThreadPoolExecutor() throws InterruptedException {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS,
        new LinkedBlockingDeque<>());

    BlockingDeque<Integer> que = new LinkedBlockingDeque<>();

    Runnable runnable = () -> que.add(0);

    IntStream.range(0, 100).parallel().forEach(index -> threadPoolExecutor.submit(runnable));

    threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS);

    assert que.size() == 100;
  }

  private synchronized void increment() {
    iteratorForSyncronizedAccess++;
  }

  private class MyThread extends Thread {

    public MyThread(String name) {
      super(name);
    }

    @Override
    public void run() {
      assert Thread.currentThread().getName().equals(THREAD_NAME);
    }
  }

  private class IncrementerThread extends Thread {

    @Override
    public void run() {
      for (var i = 0; i < 100000; i++) {
        iterator.getAndIncrement();
      }
    }
  }
}
