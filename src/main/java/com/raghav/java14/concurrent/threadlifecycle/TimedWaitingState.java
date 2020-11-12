package com.raghav.java14.concurrent.threadlifecycle;

public class TimedWaitingState {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new DemoThread());
        t1.start();
        // The following sleep will give enough time for ThreadScheduler
        // to start processing of thread t1
        Thread.sleep(1000);
        System.out.println(t1.getState());
    }
}

class DemoThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
