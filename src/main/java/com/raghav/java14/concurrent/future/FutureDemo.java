package com.raghav.java14.concurrent.future;

import java.util.concurrent.*;

public class FutureDemo {
    public String invoke() {
        String str = null;
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<String> future = service.submit(() -> {
            //Task
            Thread.sleep(10000l);
            return "Hello world";
        });

        future.cancel(true);

        try {
            future.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        if (future.isDone() && !future.isCancelled()) {
            try {
                str = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static void main(String[] args) {
        FutureDemo demo = new FutureDemo();
        System.out.println(demo.invoke());
    }
}
