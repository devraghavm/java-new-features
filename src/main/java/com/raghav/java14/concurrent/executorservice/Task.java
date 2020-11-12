package com.raghav.java14.concurrent.executorservice;

public class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread Id: "+Thread.currentThread().getId());
    }
}
