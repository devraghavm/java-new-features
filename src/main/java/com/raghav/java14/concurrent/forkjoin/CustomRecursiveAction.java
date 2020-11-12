package com.raghav.java14.concurrent.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {
    private String workLoad = "";
    public static final int THRESHOLD = 4;
    private static Logger logger = LoggerFactory.getLogger(CustomRecursiveAction.class);

    public CustomRecursiveAction(String workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if (workLoad.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubTasks());
        } else {
            processing(workLoad);
        }
    }

    private Collection<CustomRecursiveAction> createSubTasks() {
        List<CustomRecursiveAction> subTasks = new ArrayList<>();

        String partOne = workLoad.substring(0, workLoad.length() / 2);
        String partTwo = workLoad.substring(workLoad.length() / 2, workLoad.length());

        subTasks.add(new CustomRecursiveAction(partOne));
        subTasks.add(new CustomRecursiveAction(partTwo));

        return subTasks;
    }

    private void processing(String work) {
        String result = work.toUpperCase();
        logger.info("This result - (" + result + ") - was processed by " + Thread.currentThread()
                .getName());

        System.out.println("This result - (" + result + ") - was processed by " + Thread.currentThread()
                .getName());
    }
}
