package com.raghav.java14.concurrent.threadfactory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public class RaghavThreadFactory implements ThreadFactory {
    private int threadId;
    private String name;

    public RaghavThreadFactory(String name) {
        this.threadId = 1;
        this.name = name;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + threadId);
        System.out.println("created new thread with id : " + threadId +
                " and name : " + t.getName());
        threadId++;
        return t;
    }
}
