package com.raghav.java14.concurrent.callable;

import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FactorialTaskManualTest {
    private ExecutorService executorService;

    @BeforeEach
    public void setup() {
        executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    public void whenTaskSubmitted_ThenFutureResultObtained() throws ExecutionException, InterruptedException {
        FactorialTask task = new FactorialTask(5);
        Future<Integer> future = executorService.submit(task);
        Assertions.assertEquals(120, future.get().intValue());
    }

    @Test
    public void whenException_ThenCallableThrowsIt() throws ExecutionException, InterruptedException {
        FactorialTask task = new FactorialTask(-5);
        Future<Integer> future = executorService.submit(task);
        Assertions.assertThrows(ExecutionException.class, () -> {
            future.get().intValue();
        });
    }

    @Test
    public void whenException_ThenCallableDoesntThrowsItIfGetIsNotCalled() {
        FactorialTask task = new FactorialTask(-5);
        Future<Integer> future = executorService.submit(task);
        Assertions.assertFalse(future.isDone());
    }

    @AfterEach
    public void cleanup() {
        executorService.shutdown();
    }
}
