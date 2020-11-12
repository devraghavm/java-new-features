package com.raghav.java14.concurrent.threadlifecycle;

public class WaitingState implements Runnable {
    public static Thread t1;

    @Override
    public void run() {
        Thread t2 = new Thread(new DemoThreadWS());
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        t1 = new Thread(new WaitingState());
        t1.start();
    }
}

class DemoThreadWS implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        System.out.println(WaitingState.t1.getState());
    }
}
