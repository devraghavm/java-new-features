package com.raghav.java14.concurrent.basic;

public class SimpleRunnable implements Runnable {
    private String message;

    public SimpleRunnable(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(message);
    }
}
