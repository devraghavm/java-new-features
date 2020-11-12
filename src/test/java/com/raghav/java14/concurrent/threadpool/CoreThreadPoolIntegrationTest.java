package com.raghav.java14.concurrent.threadpool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CoreThreadPoolIntegrationTest {
    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    public void whenCallingExecuteWithRunnable_thenRunnableIsExecuted() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            System.out.println("Hello World");
            lock.countDown();
        });

        lock.await(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void whenUsingExecutorServiceAndFuture_thenCanWaitOnFutureResult() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<String> future = service.submit(() -> "Hello World");
        String result = future.get();

        Assertions.assertEquals("Hello World", result);
    }

    @Test
    public void whenUsingFixedThreadPool_thenCoreAndMaximumThreadSizeAreTheSame() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        Assertions.assertEquals(2, executor.getPoolSize());
        Assertions.assertEquals(1, executor.getQueue().size());
    }

    @Test
    public void whenUsingCachedThreadPool_thenPoolSizeGrowsUnbounded() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        Assertions.assertEquals(3, executor.getPoolSize());
        Assertions.assertEquals(0, executor.getQueue().size());
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    public void whenUsingSingleThreadPool_thenTasksExecuteSequentially() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(2);
        AtomicInteger counter = new AtomicInteger();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() -> {
            counter.set(1);
            lock.countDown();
        });
        service.submit(() -> {
            counter.compareAndSet(1, 2);
            lock.countDown();
        });

        lock.await(1000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(2, counter.get());
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    public void whenSchedulingTask_thenTaskExecutesWithinGivenPeriod() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        executor.schedule(() -> {
            System.out.println("Hello World");
            lock.countDown();
        }, 500, TimeUnit.MILLISECONDS);

        lock.await(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    public void whenSchedulingTaskWithFixedPeriod_thenTaskExecutesMultipleTimes() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(3);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            System.out.println("Hello World");
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);

        lock.await();
        future.cancel(true);
    }

    @Test
    public void whenUsingForkJoinPool_thenSumOfTreeElementsIsCalculatedCorrectly() {
        TreeNode tree = new TreeNode(5, new TreeNode(3), new TreeNode(2, new TreeNode(2), new TreeNode(8)));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int sum = forkJoinPool.invoke(new CountingTask(tree));

        Assertions.assertEquals(20, sum);
    }
}
