package com.raghav.java14.concurrent.daemon;

public class NewThread extends Thread {
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + ": New Thread is running..." + i);
                try {
                    // Wait for 1 second so it doesn't print too fast
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // prevent the thread to run forever
            if (System.currentTimeMillis() - startTime > 2000) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
