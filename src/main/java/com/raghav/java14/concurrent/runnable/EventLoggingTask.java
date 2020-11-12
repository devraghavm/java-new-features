package com.raghav.java14.concurrent.runnable;

public class EventLoggingTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Message read from the event queue");
    }
}
