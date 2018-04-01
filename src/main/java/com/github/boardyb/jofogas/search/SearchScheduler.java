package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Sets.newLinkedHashSet;

/**
 * This component is responsible for the collection of the latest search results.
 * It runs search and collects search results at scheduled times.
 */
@Component
public class SearchScheduler {

    private Logger logger = LoggerFactory.getLogger(SearchScheduler.class);

    private static final int RESULT_SIZE = 1000;

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private SearchResultWriter searchResultWriter;

    @Autowired
    private SearchProperties searchProperties;

    private SearchCriteria searchCriteria = new SearchCriteria();

    private Set<SearchListElement> searchResults = new LinkedHashSet<>(RESULT_SIZE);

    @PostConstruct
    public void initializeCriteria() {
        this.searchCriteria = new SearchCriteria(searchProperties.getTerm());
    }

    /**
     * This method runs indefinitely every 5 seconds to find the current search results.
     * If there is no search term provided the method returns with nothing.
     * Every other case a search is being run and the results are collected in searchResults LinkedHashSet,
     * if the Set does not already contain the element.
     * If the searchResults set is empty all the found unique results will be put into the set.
     * If the searchResults set has elements the method iterates through the found search results and if
     * a unique value is amongst them it will be put into the set and will be displayed as a SearchTrayIcon by
     * SearchResultWriter component.
     *
     * @throws Exception if an error occurs during search.
     */
    @Scheduled(fixedRate = 5000)
    public void scheduledSearch() throws Exception {
        if (isNullOrEmpty(this.searchCriteria.getTerm())) {
            return;
        }
        List<SearchListElement> results = searchClient.search(this.searchCriteria);

        if (searchResults.isEmpty()) {
            searchResults.addAll(results);
        }

        for (SearchListElement result : results) {
            boolean successfulAddition = searchResults.add(result);
            if (successfulAddition) {
                searchResultWriter.displaySearchResult(result);
                logger.info("SearchListElement has been added: [{}]", result);
            }
        }

        logger.info("Search returned with the following results: [{}]", this.searchResults);
    }

    /**
     * This method is responsible for writing the collected search results to a file and then emptying the result set.
     *
     * @throws IOException if failed to write results to file.
     */
    @Scheduled(cron = "0 0 0/12 1/1 * *")
    public void writeAndEmptyResultHistory() throws IOException {
        logger.debug("Writing search results to search-results.txt file");
        searchResultWriter.writeResultsToFile(this.searchResults);
        logger.info("Removing all previous results of search. {}", this.searchResults);
        this.searchResults = newLinkedHashSet();
    }

    public Set<SearchListElement> getSearchResults() {
        return searchResults;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public void setSearchClient(SearchClient searchClient) {
        this.searchClient = searchClient;
    }

    public void setSearchResultWriter(SearchResultWriter searchResultWriter) {
        this.searchResultWriter = searchResultWriter;
    }
}
