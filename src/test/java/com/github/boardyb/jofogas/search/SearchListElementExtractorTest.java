package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.TestBase;
import com.github.boardyb.jofogas.search.extract.SearchListElementExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchListElementExtractorTest extends TestBase {

    private Document resultPage;
    private TestSearchListElementExtractor extractor;

    @Before
    public void setUp() throws Exception {
        String html = readFile("search-list-element-extractor-test.html");
        resultPage = Jsoup.parse(html, "ISO-8859-1");
        extractor = new TestSearchListElementExtractor();
    }

    @Test
    public void shouldParseFieldsCorrectly() throws Exception {
        Elements resultListFull = resultPage.select(".should-parse-fields-correctly");

        List<SearchListElement> searchListElement = newArrayList();
        TestSearchListElementExtractor extractor = new TestSearchListElementExtractor();
        resultListFull.forEach((Element element) -> searchListElement.add(extractor.extractFromElement(element)));
        SearchListElement listElement = searchListElement.get(0);

        assertThat(listElement.getId()).isEqualTo("75625912");
        assertThat(listElement.getPrice()).isEqualTo(109990);
        assertThat(listElement.isValidPhoneNumber()).isTrue();

    }

    @Test
    public void shouldParseFebruaryDateCorrectly() throws Exception {
        Elements dateField = resultPage.select(".should-parse-february-date-correctly");
        assertThat(extractor.extractDateFromElement(dateField.first())).isEqualTo(LocalDateTime.of(1995, 2, 2, 14, 38));
    }

    @Test
    public void shouldParseJanuaryDateCorrectly() throws Exception {
        Elements dateField = resultPage.select(".should-parse-january-date-correctly");
        assertThat(extractor.extractDateFromElement(dateField.first())).isEqualTo(LocalDateTime.of(1995,
                                                                                                   1,
                                                                                                   17,
                                                                                                   11,
                                                                                                   34));
    }

    @Test
    public void shouldParseTodayDateCorrectly() throws Exception {
        Elements dateField = resultPage.select(".should-parse-today-date-correctly");
        assertThat(extractor.extractDateFromElement(dateField.first())).isEqualTo(LocalDateTime.of(1995, 7, 6, 18, 38));
    }

    @Test
    public void shouldParseYesterdayDateCorrectly() throws Exception {
        Elements dateField = resultPage.select(".should-parse-yesterday-date-correctly");
        assertThat(extractor.extractDateFromElement(dateField.first())).isEqualTo(LocalDateTime.of(1995, 7, 5, 19, 57));
    }

    @Test
    public void shouldParseMoreThanaMonthDateCorrectly() throws Exception {
        Elements dateField = resultPage.select(".should-parse-more-than-a-month-date-correctly");
        assertThat(extractor.extractDateFromElement(dateField.first())).isEqualTo(LocalDateTime.MIN);
    }

    private static class TestSearchListElementExtractor extends SearchListElementExtractor {

        @Override
        protected LocalDate getLocalDateOfToday() {
            return LocalDate.of(1995, 7, 6);
        }

        @Override
        public LocalDateTime extractDateFromElement(Element element) {
            return super.extractDateFromElement(element);
        }
    }
}