package com.raghav.java14.concurrent.semaphores;

import cfjapa.parser.ast.stmt.AssertStmt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoresManualTest {

    @Test
    public void givenLoginQueue_whenReachLimit_thenBlocked() throws InterruptedException {
        final int slots = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);

        IntStream.range(0, slots)
                .forEach(user -> executorService.execute(loginQueue::tryLogin));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        Assertions.assertEquals(0, loginQueue.availableSlots());
        Assertions.assertFalse(loginQueue.tryLogin());
    }

    @Test
    public void givenLoginQueue_whenLogout_thenSlotsAvailable() throws InterruptedException {
        final int slots = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        IntStream.range(0, slots)
                .forEach(user -> executorService.execute(loginQueue::tryLogin));

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        Assertions.assertEquals(0, loginQueue.availableSlots());
        loginQueue.logout();
        Assertions.assertTrue(loginQueue.availableSlots() > 0);
        Assertions.assertTrue(loginQueue.tryLogin());
    }

    @Test
    public void givenDelayQueue_whenReachLimit_thenBlocked() {
        int slots = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        DelayQueueUsingTimedSemaphore delayQueue = new DelayQueueUsingTimedSemaphore(1, slots);

        IntStream.range(0, slots)
                .forEach(user -> executorService.execute(delayQueue::tryAdd));
        executorService.shutdown();

        Assertions.assertEquals(0, delayQueue.availableSlots());
        Assertions.assertFalse(delayQueue.tryAdd());
    }

    @Test
    public void givenDelayQueue_whenTimePass_thenSlotsAvailable() throws InterruptedException {
        int slots = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        DelayQueueUsingTimedSemaphore delayQueue = new DelayQueueUsingTimedSemaphore(1, slots);

        IntStream.range(0, slots)
                .forEach(user -> executorService.execute(delayQueue::tryAdd));
        executorService.shutdown();

        Assertions.assertEquals(0, delayQueue.availableSlots());
        Thread.sleep(1000);
        Assertions.assertTrue(delayQueue.availableSlots() > 0);
        Assertions.assertTrue(delayQueue.tryAdd());
    }

    @Test
    public void whenMutexAndMultipleThreads_thenBlocked() {
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CounterUsingMutex counter = new CounterUsingMutex();
        IntStream.range(0, count)
                .forEach(user -> executorService.execute(() -> {
                    try {
                        counter.increase();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        executorService.shutdown();

        Assertions.assertTrue(counter.hasQueuedThreads());
    }

    @Test
    public void givenMutexAndMultipleThreads_ThenDelay_thenCorrectCount() throws InterruptedException {
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CounterUsingMutex counter = new CounterUsingMutex();
        IntStream.range(0, count)
                .forEach(user -> executorService.execute(() -> {
                    try {
                        counter.increase();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        executorService.shutdown();

        Assertions.assertTrue(counter.hasQueuedThreads());
        Thread.sleep(5000);
        Assertions.assertFalse(counter.hasQueuedThreads());
        Assertions.assertEquals(count, counter.getCount());
    }
}
