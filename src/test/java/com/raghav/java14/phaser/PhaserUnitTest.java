package com.raghav.java14.phaser;

import com.raghav.java14.concurrent.phaser.LongRunningAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserUnitTest {
    @Test
    public void givenPhaser_whenCoordinateWorksBetweenThreads_thenShouldCoordinateBetweenMultiplePhases() {
        //given
        ExecutorService service = Executors.newCachedThreadPool();
        Phaser ph = new Phaser(1);
        Assertions.assertEquals(0, ph.getPhase());

        //when
        service.submit(new LongRunningAction("thread-1", ph));
        service.submit(new LongRunningAction("thread-2", ph));
        service.submit(new LongRunningAction("thread-3", ph));

        //then
        ph.arriveAndAwaitAdvance();
        Assertions.assertEquals(1, ph.getPhase());

        //and
        service.submit(new LongRunningAction("thread-4", ph));
        service.submit(new LongRunningAction("thread-5", ph));
        ph.arriveAndAwaitAdvance();
        Assertions.assertEquals(2, ph.getPhase());

        ph.arriveAndDeregister();
    }
}
