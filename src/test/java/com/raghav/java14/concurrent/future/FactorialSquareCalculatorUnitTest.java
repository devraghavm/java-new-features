package com.raghav.java14.concurrent.future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

public class FactorialSquareCalculatorUnitTest {

    @Test
    public void whenCalculatesFactorialSquare_thenReturnCorrectValue() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);

        forkJoinPool.execute(calculator);

        Assertions.assertEquals(385, calculator.join().intValue(), "The sum of the squares from 1 to 10 is 385");
    }
}
