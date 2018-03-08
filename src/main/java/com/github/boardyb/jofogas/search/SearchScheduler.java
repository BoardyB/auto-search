package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

/**
 * This component is responsible for the collection of the latest search results.
 * It runs search and collects search results at scheduled times.
 */
@Component
public class SearchScheduler {

    private Logger logger = LoggerFactory.getLogger(SearchScheduler.class);

    public static final int RESULT_SIZE = 25;

    @Autowired
    private SearchClient searchClient;

    private SearchCriteria searchCriteria = new SearchCriteria();

    Map<String, SearchListElement> searchResults = new LinkedHashMap<String, SearchListElement>(RESULT_SIZE) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > RESULT_SIZE;
        }
    };

    /**
     * This method runs indefinitely every 30 seconds to find the current search results.
     * If there is no search term provided the method returns with nothing.
     * Every other case a search is being run and the results are collected in searchResults LinkedHashMap,
     * if the Map does not already contain the element.
     * If the Map reaches it's maximum size,the eldest element is being removed and the new element is being put
     * to the top of the map.
     *
     * @throws Exception if an error occurs during search.
     */
    @Scheduled(fixedRate = 30000)
    public void scheduledSearch() throws Exception {
        if (isNullOrEmpty(this.searchCriteria.getTerm())) {
            return;
        }
        List<SearchListElement> results = searchClient.search(this.searchCriteria);
        results.forEach((SearchListElement result) -> {
            if (isNull(searchResults.get(result.getId()))) {
                searchResults.put(result.getId(), result);
                logger.info("SearchListElement has been added: [{}]", result);
            }
        });

        logger.info("Search returned with the following results: [{}]", this.searchResults);
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

}
