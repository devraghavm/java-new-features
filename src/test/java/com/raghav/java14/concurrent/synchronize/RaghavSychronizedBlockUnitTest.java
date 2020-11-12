package com.raghav.java14.concurrent.synchronize;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RaghavSychronizedBlockUnitTest {
    @Test
    public void givenMultiThread_whenBlockSync() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        RaghavSynchronizedBlocks synchronizedBlocks = new RaghavSynchronizedBlocks();

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(synchronizedBlocks::performSynchronizedTask));
        service.awaitTermination(500, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(1000, synchronizedBlocks.getCount());
    }

    @Test
    public void givenMultiThread_whenStaticSyncBlock() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(RaghavSynchronizedBlocks::performStaticSyncTask));
        service.awaitTermination(500, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(1000, RaghavSynchronizedBlocks.getStaticCount());
    }

    @Test
    public void givenHoldingTheLock_whenReentrant_thenCanAcquireItAgain() {
        Object lock = new Object();
        synchronized (lock) {
            System.out.println("First time acquiring it");

            synchronized (lock) {
                System.out.println("Entering again");

                synchronized (lock) {
                    System.out.println("And again");
                }
            }
        }
    }
}
