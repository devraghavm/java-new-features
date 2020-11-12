package com.raghav.java14.concurrent.semaphores;

import java.util.concurrent.Semaphore;

class CounterUsingMutex {
    private Semaphore mutex;
    private int count;

    CounterUsingMutex() {
        mutex = new Semaphore(1);
        count = 0;
    }

    void increase() throws InterruptedException {
        mutex.acquire();
        this.count = this.count + 1;
        Thread.sleep(1000);
        mutex.release();
    }

    int getCount() {
        return this.count;
    }

    boolean hasQueuedThreads() {
        return mutex.hasQueuedThreads();
    }
}
