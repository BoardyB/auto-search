package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;

@Component
public class SearchScheduler {
    
    private Logger logger = LoggerFactory.getLogger(SearchScheduler.class);

    @Autowired
    private SearchClient searchClient;

    private SearchCriteria searchCriteria = new SearchCriteria();
    private List<SearchListElement> searchResults = newArrayList();

    @Scheduled(fixedRate = 5000)
    public void scheduledSearch() throws Exception {
        if (isNullOrEmpty(this.searchCriteria.getTerm())) {
            return;
        }
        this.searchResults = searchClient.search(this.searchCriteria);
        logger.info("Search returned with the following results: [{}]", this.searchResults);
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<SearchListElement> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchListElement> searchResults) {
        this.searchResults = searchResults;
    }

}
