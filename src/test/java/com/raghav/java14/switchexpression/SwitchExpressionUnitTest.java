package com.raghav.java14.switchexpression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.function.Function;

public class SwitchExpressionUnitTest {
    @Test
    public void whenSwitchingOverMonthJune_thenWillReturn3() {
        var month = Month.JUNE;

        var result = switch (month) {
            case JANUARY, JUNE, JULY -> 3;
            case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
            case MARCH, MAY, APRIL -> 2;
            default -> 0;
        };

        Assertions.assertEquals(result, 3);
    }

    @Test
    public void whenSwitchingOverMonthAugust_thenWillReturn24() {
        var month = Month.AUGUST;

        var result = switch (month) {
            case JANUARY, JUNE, JULY -> 3;
            case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
            case MARCH, MAY, APRIL, AUGUST -> {
                int monthLength = month.toString().length();
                yield monthLength * 4;
            }
            default -> 0;
        };

        Assertions.assertEquals(result, 24);
    }

    @Test
    public void whenSwitchingOverMonthJanuary_thenWillReturn3() {
        Function<Month, Integer> func = (month) -> {
            switch (month) {
                case JANUARY, JUNE, JULY -> {return 3;}
                default -> {return 0;}
            }
        };

        Assertions.assertEquals(Integer.valueOf(3), func.apply(Month.JANUARY));
    }

    @Test
    public void whenSwitchingOverMonthAugust_thenWillReturn2() {
        var month = Month.AUGUST;

        var result = switch (month) {
            case JANUARY, JUNE, JULY -> 3;
            case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
            case MARCH, MAY, APRIL, AUGUST -> 2;
        };

        Assertions.assertEquals(result, 2);
    }

}
