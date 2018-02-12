package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;

import java.util.List;

public interface SearchClient {
    
    List<SearchListElement> search(SearchCriteria searchCriteria) throws Exception;

}
