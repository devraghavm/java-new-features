package com.raghav.java14.concurrent.sleepwait;

/***
 * Example of waking up a waiting thread
 */
public class ThreadB extends Thread {
    int sum;

    @Override
    public void run() {
        synchronized (this) {
            int i = 0;
            while (i < 100000) {
                sum += i;
                i++;
            }
            notify();
        }
    }
}
