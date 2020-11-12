package com.raghav.java14.concurrent.semaphores;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

import java.util.concurrent.TimeUnit;

class DelayQueueUsingTimedSemaphore {
    private TimedSemaphore timedSemaphore;

    DelayQueueUsingTimedSemaphore(long period, int slotLimit) {
        timedSemaphore = new TimedSemaphore(period, TimeUnit.SECONDS, slotLimit);
    }

    boolean tryAdd() {
        return timedSemaphore.tryAcquire();
    }

    int availableSlots() {
        return timedSemaphore.getAvailablePermits();
    }
}
