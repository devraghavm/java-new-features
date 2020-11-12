package com.raghav.java14.concurrent.threadlifecycle;

public class NewState implements Runnable {
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Runnable runnable = new NewState();
        Thread t = new Thread(runnable);
        System.out.println(t.getState());
    }
}
