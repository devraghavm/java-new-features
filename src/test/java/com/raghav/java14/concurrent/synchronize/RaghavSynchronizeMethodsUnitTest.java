package com.raghav.java14.concurrent.synchronize;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RaghavSynchronizeMethodsUnitTest {
    @Test
    public void givenMultiThread_whenNonSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        RaghavSynchronizedMethods method = new RaghavSynchronizedMethods();

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(method::calculate));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(1000, method.getSum());
    }

    @Test
    public void givenMultiThread_whenMethodSync() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        RaghavSynchronizedMethods method = new RaghavSynchronizedMethods();

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(method::synchronizedCalculate));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(1000, method.getSyncSum());
    }

    @Test
    public void givenMultiThread_whenStaticSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(RaghavSynchronizedMethods::syncStaticCalculate));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(1000, RaghavSynchronizedMethods.staticSum);
    }

}
