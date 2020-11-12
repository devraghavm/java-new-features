package com.raghav.java14.threadlocal;

import com.raghav.java14.concurrent.threadlocal.SharedMapWithUserContext;
import com.raghav.java14.concurrent.threadlocal.ThreadLocalWithUserContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadLocalIntegrationTest {
    @Test
    public void givenThreadThatStoresContextInAMap_whenStartThread_thenShouldSetContextForBothUsers() throws InterruptedException {
        //when
        SharedMapWithUserContext firstUser = new SharedMapWithUserContext(1);
        SharedMapWithUserContext secondUser = new SharedMapWithUserContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();

        Thread.sleep(3000);
        //then
        Assertions.assertEquals(SharedMapWithUserContext.userContextPerUserId.size(), 2);
    }

    @Test
    public void givenThreadThatStoresContextInThreadLocal_whenStartThread_thenShouldStoreContextInThreadLocal() throws InterruptedException {
        //when
        ThreadLocalWithUserContext firstUser = new ThreadLocalWithUserContext(1);
        ThreadLocalWithUserContext secondUser = new ThreadLocalWithUserContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();

        Thread.sleep(3000);
    }
}
