package com.raghav.java14.concurrent.java.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ThreadPoolInParallelStreamIntegrationTest {
    @Test
    public void giveRangeOfLongs_whenSummedInParallel_shouldBeEqualToExpectedTotal() throws ExecutionException, InterruptedException {
        long firstNum = 1;
        long lastNum = 1_000_000;

        List<Long> alist = LongStream.rangeClosed(firstNum, lastNum).boxed().collect(Collectors.toList());

        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        long actualTotal = customThreadPool.submit(() -> alist.parallelStream().reduce(0L, Long::sum)).get();

        Assertions.assertEquals((lastNum + firstNum) * lastNum / 2, actualTotal);
    }

    @Test
    public void givenList_whenCallingParallelStream_shouldBeParallelStream() {
        List<Long> aList = new ArrayList<>();
        Stream<Long> parallelStream = aList.parallelStream();

        Assertions.assertTrue(parallelStream.isParallel());
    }
}
