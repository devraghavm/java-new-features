package com.raghav.java14.concurrent.delayqueue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class DelayQueueProducer implements Runnable {
    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToProduce;
    private Integer delayOfEachProducedMessageMilliSeconds;

    DelayQueueProducer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToProduce, Integer delayOfEachProducedMessageMilliSeconds) {
        this.queue = queue;
        this.numberOfElementsToProduce = numberOfElementsToProduce;
        this.delayOfEachProducedMessageMilliSeconds = delayOfEachProducedMessageMilliSeconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayObject object = new DelayObject(UUID.randomUUID().toString(), delayOfEachProducedMessageMilliSeconds);
            System.out.println("Put object = " + object);
            try {
                queue.put(object);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
