package com.raghav.java14.concurrent.forkjoin;

import com.raghav.java14.concurrent.forkjoin.util.PoolUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Java8ForkJoinIntegrationTest {
    private int[] arr;
    private CustomRecursiveTask customRecursiveTask;

    @BeforeEach
    public void init() {
        Random random = new Random();
        arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(35);
        }
        customRecursiveTask = new CustomRecursiveTask(arr);
    }

    @Test
    public void callPoolUtil_whenExistsAndExpectedType_thenCorrect() {
        ForkJoinPool forkJoinPool = PoolUtil.forkJoinPool;
        ForkJoinPool forkJoinPoolTwo = PoolUtil.forkJoinPool;

        Assertions.assertNotNull(forkJoinPool);
        Assertions.assertEquals(2, forkJoinPool.getParallelism());
        Assertions.assertEquals(forkJoinPool, forkJoinPoolTwo);
    }

    @Test
    public void callCommonPool_whenExistsAndExpectedType_thenCorrect() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        ForkJoinPool commonPoolTwo = ForkJoinPool.commonPool();

        Assertions.assertNotNull(commonPool);
        Assertions.assertEquals(commonPool, commonPoolTwo);
    }

    @Test
    public void executeRecursiveAction_whenExecuted_thenCorrect() {
        CustomRecursiveAction myRecursiveAction = new CustomRecursiveAction("ddddffffgggghhhh");
        ForkJoinPool.commonPool().invoke(myRecursiveAction);

        Assertions.assertTrue(myRecursiveAction.isDone());
    }

    @Test
    public void executeRecursiveTask_whenExecuted_thenCorrect() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        forkJoinPool.execute(customRecursiveTask);
        int result = customRecursiveTask.join();
        Assertions.assertTrue(customRecursiveTask.isDone());

        forkJoinPool.submit(customRecursiveTask);
        int resultTwo = customRecursiveTask.join();
        Assertions.assertTrue(customRecursiveTask.isDone());
    }

    @Test
    public void executeRecursiveTaskWithFJ_whenExecuted_thenCorrect() {
        CustomRecursiveTask customRecursiveTaskFirst = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskSecond = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskLast = new CustomRecursiveTask(arr);

        customRecursiveTaskFirst.fork();
        customRecursiveTaskSecond.fork();
        customRecursiveTaskLast.fork();
        int result = 0;
        result += customRecursiveTaskLast.join();
        result += customRecursiveTaskSecond.join();
        result += customRecursiveTaskFirst.join();

        Assertions.assertTrue(customRecursiveTaskFirst.isDone());
        Assertions.assertTrue(customRecursiveTaskSecond.isDone());
        Assertions.assertTrue(customRecursiveTaskLast.isDone());
        Assertions.assertTrue(result != 0);

    }
}
