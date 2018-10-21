package org.github.kovaku.concurency;

import org.testng.annotations.Test;

public class ConcurrencyTest {

  private static final String THREAD_NAME = "MyThread";

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

  private class MyThread extends Thread {

    public MyThread(String name) {
      super(name);
    }

    @Override
    public void run() {
      assert Thread.currentThread().getName().equals(THREAD_NAME);
    }
  }
}
