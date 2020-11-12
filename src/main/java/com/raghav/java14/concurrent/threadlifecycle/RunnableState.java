package com.raghav.java14.concurrent.threadlifecycle;

public class RunnableState implements Runnable {
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Runnable runnable = new RunnableState();
        Thread t = new Thread(runnable);
        t.start();
        System.out.println(t.getState());
    }
}
