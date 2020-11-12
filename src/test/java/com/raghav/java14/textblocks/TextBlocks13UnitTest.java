package com.raghav.java14.textblocks;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextBlocks13UnitTest {
    private TextBlocks13 subject = new TextBlocks13();

    @Test
    void givenAnOldStyleMultilineString_whenComparing_thenEqualsTextBlock() {
        String expected = "<html>\n"
                + "\n"
                + "    <body>\n"
                + "        <span>example text</span>\n"
                + "    </body>\n"
                + "</html>";
        Assertions.assertTrue(subject.getBlockOfHtml().equals(expected));
    }

    @Test
    void givenAnOldStyleString_whenComparing_thenEqualsTextBlock() {
        String expected = "<html>\n\n    <body>\n        <span>example text</span>\n    </body>\n</html>";
        Assertions.assertTrue(subject.getBlockOfHtml().equals(expected));
    }

    @Test
    void givenAnIndentedString_thenMatchesIndentedOldStyle() {
        Assertions.assertTrue(subject.getNonStandardIndent().equals("    Indent\n"));
    }

    @Test
    void givenAStringWithEscapedWhitespace_thenItAppearsInTheResultingString() {
        Assertions.assertAll("text",
                () -> Assertions.assertTrue(subject.getTextWithEscapes().contains("fun with\n\n")),
                () -> Assertions.assertTrue(subject.getTextWithEscapes().contains("whitespace\t\r\n"))
                //() -> Assertions.assertTrue(subject.getTextWithEscapes().contains("and other escapes \"\"\""))
        );
    }

    @Test
    void givenATextWithCarriageReturns_thenItContainsBoth() {
        Assertions.assertEquals(subject.getTextWithCarriageReturns(), "separated with\r\ncarriage returns");
    }

    @Test
    void givenAMultilineQuery_thenItCanContainUnescapedQuotes() {
        Assertions.assertTrue(subject.getQuery().contains("select \"id\", \"user\""));
    }

    @Test
    void givenAMultilineQuery_thenItEndWithANewline() {
        Assertions.assertTrue(subject.getQuery().endsWith("\n"));
    }

    @Test
    void givenAFormattedString_thenTheParameterIsReplaced() {
        Assertions.assertTrue(subject.getFormattedText("parameter").contains("Some parameter: parameter"));
    }
}
