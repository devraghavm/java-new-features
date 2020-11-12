package com.raghav.java14.concurrent.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {
    ExecutorService executor = Executors.newFixedThreadPool(10);

    public void execute() {
        executor.submit(() -> {
            new Task();
        });

        executor.shutdown();
        executor.shutdownNow();

        try {
            executor.awaitTermination(20l, TimeUnit.NANOSECONDS);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorServiceDemo executorServiceDemo = new ExecutorServiceDemo();
        executorServiceDemo.execute();
    }
}
