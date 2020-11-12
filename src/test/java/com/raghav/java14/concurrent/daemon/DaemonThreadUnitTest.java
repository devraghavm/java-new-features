package com.raghav.java14.concurrent.daemon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DaemonThreadUnitTest {
    @Test
    @Disabled("")
    public void whenCallIsDaemon_thenCorrect() {
        NewThread daemonThread = new NewThread();
        NewThread userThread = new NewThread();
        daemonThread.setDaemon(true);
        daemonThread.start();
        userThread.start();

        Assertions.assertTrue(daemonThread.isDaemon());
        Assertions.assertFalse(userThread.isDaemon());
    }

    @Test
    @Disabled
    public void givenUserThread_whenSetDaemonWhileRunning_thenIllegalThreadStateException() {
        NewThread daemonThread = new NewThread();
        daemonThread.start();
        Assertions.assertThrows(IllegalThreadStateException.class, () -> {
            daemonThread.setDaemon(true);
        });
    }
}
