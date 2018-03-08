package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;

import java.util.List;

/**
 * SearchClient is an interface which contains all search related functionality that the app provides.
 */
public interface SearchClient {

    /**
     * @param searchCriteria criteria by which search results are filtered.
     * @return this method returns a List of SearchListElements which are parsed from the HttpResponse of the search and
     * match the criteria.
     * @throws Exception if an error happens during the execution of the request or while parsing data from the response.
     */
    List<SearchListElement> search(SearchCriteria searchCriteria) throws Exception;

}
