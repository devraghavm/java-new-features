package com.raghav.java14.concurrent.countdownlatch;

import checkers.units.quals.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountdownLatchExampleIntegrationTest {
    @Test
    public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {
        // Given
        List<String> outputScrapper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new Worker(outputScrapper, countDownLatch)))
                .limit(5)
                .collect(Collectors.toList());

        // When
        workers.forEach(Thread::start);
        countDownLatch.await();
        outputScrapper.add("Latch released");

        // Then
        String[] actual = {"Counted down", "Counted down", "Counted down", "Counted down", "Counted down", "Latch released"};
        Assertions.assertArrayEquals(outputScrapper.toArray(), actual);
    }

    @Test
    public void whenFailingToParallelProcess_thenMainThreadShouldTimeout() throws InterruptedException {
        // Given
        List<String> outputScrapper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new BrokenWorker(outputScrapper, countDownLatch)))
                .limit(5)
                .collect(Collectors.toList());

        // When
        workers.forEach(Thread::start);
        final boolean result = countDownLatch.await(3L, TimeUnit.SECONDS);

        // Then
        Assertions.assertFalse(result);
    }

    @Test
    public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime() throws InterruptedException {
        // Given
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readyThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new WaitingWorker(outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter))).limit(5).collect(Collectors.toList());

        // When
        workers.forEach(Thread::start);
        readyThreadCounter.await(); // Block until workers start
        outputScraper.add("Workers ready");
        callingThreadBlocker.countDown();
        completedThreadCounter.await(); // Block until workers finish
        outputScraper.add("Workers complete");

        // Then
        String[] actual = {"Workers ready", "Counted down", "Counted down", "Counted down", "Counted down", "Counted down", "Workers complete"};
        Assertions.assertArrayEquals(outputScraper.toArray(), actual);
    }
}
