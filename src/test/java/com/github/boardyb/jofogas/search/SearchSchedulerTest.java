package com.github.boardyb.jofogas.search;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import com.github.boardyb.jofogas.search.extract.SearchListElementExtractor;
import ie.corballis.fixtures.annotation.Fixture;
import ie.corballis.fixtures.annotation.FixtureAnnotations;
import ie.corballis.fixtures.core.ObjectMapperProvider;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SearchSchedulerTest {

    @Fixture("defaultSearchResults")
    private List<SearchListElement> defaultSearchResults;

    @MockBean
    private HttpClient httpClient;

    @SpyBean
    private SearchListElementExtractor extractor;

    @SpyBean
    private SearchResultWriter searchResultWriter;

    @SpyBean
    private BasicSearchClient searchClient;

    @InjectMocks
    private SearchScheduler searchScheduler;

    @Before
    public void setUp() throws Exception {
        searchScheduler = new SearchScheduler();
        searchScheduler.setSearchClient(searchClient);
        searchScheduler.setSearchResultWriter(searchResultWriter);
        ObjectMapperProvider.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        FixtureAnnotations.initFixtures(this);
    }

    @Test
    public void shouldNotSearchIfNoCriteriaWasProvided() throws Exception {
        searchScheduler.scheduledSearch();
        verify(searchClient, never()).search(any(SearchCriteria.class));
        verify(searchClient, never()).search(anyString());
    }

    @Test
    public void shouldAddAllSearchResultsIfResultListIsEmpty() throws Exception {
        SearchCriteria criteria = new SearchCriteria("term");
        doReturn(defaultSearchResults).when(searchClient).search(criteria);
        searchScheduler.setSearchCriteria(criteria);
        searchScheduler.scheduledSearch();
        assertThat(searchScheduler.getSearchResults()).containsAll(defaultSearchResults);
    }

    @Test
    public void shouldAddSingleResultIfResultsContainsElements() throws Exception {
        doNothing().when(searchResultWriter).displaySearchResult(any(SearchListElement.class));
        SearchCriteria criteria = new SearchCriteria("term");
        doReturn(defaultSearchResults).when(searchClient).search(criteria);
        searchScheduler.setSearchCriteria(criteria);
        searchScheduler.scheduledSearch();

        SearchCriteria criteria2 = new SearchCriteria("term");
        List<SearchListElement> secondResult =
            newArrayList(new SearchListElement("id", "subject", 2, 12000, "category", null, newArrayList(), false));
        doReturn(secondResult).when(searchClient).search(criteria2);
        searchScheduler.setSearchCriteria(criteria2);
        searchScheduler.scheduledSearch();

        assertThat(searchScheduler.getSearchResults()).containsAll(defaultSearchResults);
        assertThat(searchScheduler.getSearchResults()).containsAll(secondResult);
    }

    @Test
    public void shouldWriteResultsToFileAndEmptyThem() throws Exception {
        doNothing().when(searchResultWriter).displaySearchResult(any(SearchListElement.class));
        SearchCriteria criteria = new SearchCriteria("term");
        doReturn(defaultSearchResults).when(searchClient).search(criteria);
        searchScheduler.setSearchCriteria(criteria);
        searchScheduler.scheduledSearch();

        searchScheduler.writeAndEmptyResultHistory();
        Path path = Paths.get("search-results.txt");
        List<String> fileContent = Files.readAllLines(path, Charset.forName("UTF-8"));
        assertThat(fileContent).isEqualTo(newArrayList("SAMSUNG LED TV,12000",
                                                       "Samsung LE32R81W - 32 LCD TV,80000",
                                                       "Samsung UE55F6340 3D LED TV 138CM,120000"));
        assertThat(searchScheduler.getSearchResults()).isEmpty();
    }
}