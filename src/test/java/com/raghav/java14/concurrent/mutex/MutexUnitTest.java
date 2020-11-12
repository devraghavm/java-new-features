package com.raghav.java14.concurrent.mutex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class MutexUnitTest {
    @Test
    @Disabled("This is an invalid test")
    // This test verifies the race condition use case, it may pass or fail based on execution environment
    // Uncomment @Test to run it
    public void givenUnsafeSequenceGenerator_whenRaceCondition_thenUnexpectedBehavior() throws ExecutionException, InterruptedException {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGenerator(), count);
        Assertions.assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedMethod_whenRaceCondition_thenSuccess() throws ExecutionException, InterruptedException {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedMethod(), count);
        Assertions.assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedBlock_whenRaceCondition_thenSuccess() throws ExecutionException, InterruptedException {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedBlock(), count);
        Assertions.assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingReentrantLock_whenRaceCondition_thenSuccess() throws ExecutionException, InterruptedException {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingReentrantLock(), count);
        Assertions.assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSemaphore_whenRaceCondition_thenSuccess() throws ExecutionException, InterruptedException {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSemaphore(), count);
        Assertions.assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingMonitor_whenRaceCondition_thenSuccess() throws ExecutionException, InterruptedException {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingMonitor(), count);
        Assertions.assertEquals(count, uniqueSequences.size());
    }

    private Set<Integer> getUniqueSequences(SequenceGenerator sequenceGenerator, int count) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(sequenceGenerator::getNextSequence));
        }

        for (Future<Integer> future :
                futures) {
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();

        return uniqueSequences;
    }
}
