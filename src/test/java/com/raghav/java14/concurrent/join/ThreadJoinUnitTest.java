package com.raghav.java14.concurrent.join;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ThreadJoinUnitTest {
    @Test
    public void givenNewThread_whenJoinCalled_returnsImmediately() throws InterruptedException {
        Thread t1 = new SampleThread(0);
        System.out.println("Invoking join");
        t1.join();
        System.out.println("Returned from join");
        System.out.println("Thread state is "+ t1.getState());
        Assertions.assertFalse(t1.isAlive());
    }
    @Test
    public void givenStartedThread_whenJoinCalled_waitsTillCompletion() throws InterruptedException {
        Thread t2 = new SampleThread(1);
        t2.start();
        System.out.println("Invoking join");
        t2.join();
        System.out.println("Returned from join");
        Assertions.assertFalse(t2.isAlive());
    }

    @Test
    public void givenStartedThread_whenTimedJoinCalled_waitsUntilTimedout() throws InterruptedException {
        Thread t3 = new SampleThread(10);
        t3.start();
        t3.join(1000);
        Assertions.assertTrue(t3.isAlive());
    }

    @Test
    @Disabled("This is an invalid test")
    public void givenThreadTerminated_checkForEffect_notGuaranteed() {
        SampleThread t4 = new SampleThread(10);
        t4.start();
        //not guaranteed to stop even if t4 finishes.
        do {

        } while (t4.processingCount > 0);
    }

    @Test
    public void givenJoinWithTerminatedThread_checkForEffect_guaranteed() throws InterruptedException {
        SampleThread t4 = new SampleThread(10);
        t4.start();
        do {
            t4.join(100);
        } while (t4.processingCount > 0);
    }
}
