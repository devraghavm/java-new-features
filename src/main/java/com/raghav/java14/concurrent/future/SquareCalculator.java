package com.raghav.java14.concurrent.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SquareCalculator {
    private final ExecutorService service;

    SquareCalculator(ExecutorService service) {
        this.service = service;
    }

    Future<Integer> calculate(Integer input) {
        System.out.println("Calculating square for "+ input);
        return service.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }
}
