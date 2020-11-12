package com.raghav.java14.concurrent.basic;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadBasicTest {

    private static ExecutorService executorService;

    @BeforeAll
    public static void setup() {
        executorService = Executors.newCachedThreadPool();
    }

    @Test
    public void givenAThread_whenRunIt_thenResult() throws InterruptedException {
        Thread thread = new SimpleThread("SimpleThread executed using Thread");
        thread.start();
        thread.join();
    }

    @Test
    public void givenAThread_whenSubmitToES_thenResult() throws ExecutionException, InterruptedException {
        executorService.submit(new SimpleThread("SimpleThread executed using Thread")).get();
    }

    @Test
    public void givenARunnable_whenRunIt_thenResult() throws InterruptedException {
        Thread thread = new Thread(new SimpleRunnable("SimpleRunnable executed using Thread"));
        thread.start();
        thread.join();
    }

    @Test
    public void givenARunnable_whenSubmitToES_thenResult() throws ExecutionException, InterruptedException {
        executorService.submit(new SimpleRunnable("SimpleRunnable executed using ExecutorService")).get();
    }

    @Test
    public void givenARunnableLambda_whenSubmitToES_thenResult() throws ExecutionException, InterruptedException {
        executorService.submit(() -> System.out.println("Lambda runnable executed!!!")).get();
    }

    @Test
    public void givenACallable_whenSubmitToES_thenResult() throws ExecutionException, InterruptedException {
        Future<Integer> future = executorService.submit(new SimpleCallable());
        System.out.println(String.format("Result from callable: %d", future.get()));
    }

    @Test
    public void givenACallableAsLambda_whenSubmitToES_thenResult() throws ExecutionException, InterruptedException {
        Future<Integer> future = executorService.submit(() -> RandomUtils.nextInt(0, 100));

        System.out.println(String.format("Result from callable: %d", future.get()));
    }

    @AfterAll
    public static void tearDown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
