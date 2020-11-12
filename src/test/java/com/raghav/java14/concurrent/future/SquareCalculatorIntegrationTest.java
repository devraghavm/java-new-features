package com.raghav.java14.concurrent.future;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class SquareCalculatorIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(SquareCalculatorIntegrationTest.class);

    private long start;

    private SquareCalculator squareCalculator;

    @Test
    public void givenExecutorIsSingleThreaded_whenTwoExecutionsAreTriggered_thenRunInSequence() throws InterruptedException, ExecutionException {
        squareCalculator = new SquareCalculator(Executors.newSingleThreadExecutor());

        Future<Integer> result1 = squareCalculator.calculate(4);
        Future<Integer> result2 = squareCalculator.calculate(1000);

        while (!result1.isDone() || !result2.isDone()) {
            System.out.println(String.format("Task 1 is %s and Task 2 is %s.", result1.isDone() ? "done" : "not done", result2.isDone() ? "done" : "not done"));

            Thread.sleep(300);
        }

        Assertions.assertEquals(16, result1.get().intValue());
        Assertions.assertEquals(1000000, result2.get().intValue());
    }

    @Test
    public void whenGetWithTimeoutLowerThanExecutionTime_thenThrowException() throws InterruptedException, ExecutionException, TimeoutException {
        squareCalculator = new SquareCalculator(Executors.newSingleThreadExecutor());

        Future<Integer> result = squareCalculator.calculate(4);

        Assertions.assertThrows(TimeoutException.class, () -> {
            result.get(500, TimeUnit.MILLISECONDS);
        });
    }

    @Test
    public void givenExecutorIsMultiThreaded_whenTwoExecutionsAreTriggered_thenRunInParallel() throws InterruptedException, ExecutionException {
        squareCalculator = new SquareCalculator(Executors.newFixedThreadPool(2));

        Future<Integer> future1 = squareCalculator.calculate(4);
        Future<Integer> future2 = squareCalculator.calculate(1000);

        while (!future1.isDone() || !future2.isDone()) {
            System.out.println(String.format("Task 1 is %s and Task 2 is %s.", future1.isDone() ? "done" : "not done", future2.isDone() ? "done" : "not done"));

            Thread.sleep(300);
        }
        Integer result1 = future1.get();
        Integer result2 = future2.get();
        System.out.println(result1 + " and " + result2);
        Assertions.assertEquals(16, result1);
        Assertions.assertEquals(1000000, result2);
    }

    @Test
    public void whenCancelFutureAndCallGet_thenThrowException() throws ExecutionException, InterruptedException {
        squareCalculator = new SquareCalculator(Executors.newSingleThreadExecutor());

        Future<Integer> result = squareCalculator.calculate(4);

        boolean cancelled = result.cancel(true);

        Assertions.assertTrue(cancelled, "Future was cancelled");
        Assertions.assertTrue(result.isCancelled(), "Future was cancelled");

        Assertions.assertThrows(CancellationException.class, () -> {
            result.get();
        });
    }

    @BeforeEach
    public void start() {
        start = System.currentTimeMillis();
    }

    @AfterEach
    public void end(TestInfo testInfo) {
        System.out.println(String.format("Test %s took %s ms \n", testInfo.getTestMethod().get().getName(), System.currentTimeMillis() - start));
    }

}
