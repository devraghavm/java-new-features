package com.raghav.java14.concurrent.basic;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.Callable;

public class SimpleCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return RandomUtils.nextInt(0, 100);
    }
}
