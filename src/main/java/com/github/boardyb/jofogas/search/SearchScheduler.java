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

@Component
public class SearchScheduler {

    private Logger logger = LoggerFactory.getLogger(SearchScheduler.class);

    public static int RESULT_SIZE = 25;

    @Autowired
    private SearchClient searchClient;

    private SearchCriteria searchCriteria = new SearchCriteria();

    Map<String, SearchListElement> searchResults = new LinkedHashMap<String, SearchListElement>(RESULT_SIZE) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > RESULT_SIZE;
        }
    };

    @Scheduled(fixedRate = 10000)
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
