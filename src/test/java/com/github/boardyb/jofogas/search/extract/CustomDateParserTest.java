package com.github.boardyb.jofogas.search.extract;

import org.junit.Test;

import java.time.LocalDate;

import static org.fest.assertions.api.Assertions.assertThat;

public class CustomDateParserTest {

    private static final LocalDate TEST_DATE = LocalDate.of(2018, 3, 23);

    @Test
    public void shouldParseCompleteDate() throws Exception {
        LocalDate parsedDate = CustomDateParser.parseString("feb 2. 14:38", TEST_DATE);
        assertThat(parsedDate).isEqualTo(LocalDate.of(2018, 2, 2));
    }

    @Test
    public void shouldParseFirstMonthIfMonthWasNotInStore() throws Exception {
        LocalDate parsedDate = CustomDateParser.parseString("notExistingMonth 2. 14:38", TEST_DATE);
        assertThat(parsedDate).isEqualTo(LocalDate.of(2018, 1, 2));

    }
}