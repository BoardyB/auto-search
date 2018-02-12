package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.TestBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchListElementExtractorTest extends TestBase {

    private Document resultPage;

    @Before
    public void setUp() throws Exception {
        String html = readFile("search-list-element-extractor-test.html");
        resultPage = Jsoup.parse(html, "ISO-8859-1");
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

    private static class TestSearchListElementExtractor extends SearchListElementExtractor {

        @Override
        protected LocalDate getLocalDateOfToday() {
            return LocalDate.of(1995, 7, 6);
        }
    }
}