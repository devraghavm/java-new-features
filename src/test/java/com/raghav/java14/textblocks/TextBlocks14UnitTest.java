package com.raghav.java14.textblocks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextBlocks14UnitTest {
    private TextBlocks14 subject = new TextBlocks14();

    @Test
    void givenAStringWithEscapedNewLines_thenTheResultHasNoNewLines() {
        String expected = "This is a long test which looks to have a newline but actually does not";
        Assertions.assertEquals(subject.getIgnoredNewLines(), expected);
    }

    @Test
    void givenAStringWithEscapesSpaces_thenTheResultHasLinesEndingWithSpaces() {
        String expected = "line 1\nline 2        \n";
        Assertions.assertEquals(subject.getEscapedSpaces(), expected);
    }
}
