package com.raghav.java14.concurrent.executor;

import java.util.concurrent.Executor;

public class ExecutorDemo {
    public void execute() {
        Executor executor = new Invoker();
        executor.execute(() -> {
            System.out.println("Printing From Thread: "+Thread.currentThread().getId());
        });
    }
}
