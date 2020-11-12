package com.raghav.java14.concurrent.delayqueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class DelayQueueIntegrationTest {
    @Test
    public void givenDelayQueue_whenProduceElement_thenShouldConsumeAfterGivenDelay() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 500;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        //when
        executorService.submit(producer);
        executorService.submit(consumer);

        //then
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();
        Assertions.assertEquals(consumer.numberOfConsumedElements.get(), numberOfElementsToProduce);
    }

    @Test
    public void givenDelayQueue_whenProduceElementWithHugeDelay_thenConsumerWasNotAbleToConsumeMessageInGivenTime() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 10_000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        //when
        executorService.submit(producer);
        executorService.submit(consumer);

        //then
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();
        Assertions.assertEquals(consumer.numberOfConsumedElements.get(), 0);
    }

    @Test
    public void givenDelayQueue_whenProduceElementWithNegativeDelay_thenConsumeMessageImmediately() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        int delayOfEachProducedMessageMilliseconds = -10_000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        //when
        executorService.submit(producer);
        executorService.submit(consumer);

        //then
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
        Assertions.assertEquals(consumer.numberOfConsumedElements.get(), 1);
    }
}
