package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private SearchScheduler searchScheduler;

    @PostMapping
    public List<SearchListElement> search(@RequestBody @Valid SearchCriteria criteria) throws Exception {
        searchScheduler.setSearchCriteria(criteria);
        searchScheduler.scheduledSearch();
        return searchScheduler.getSearchResults();
    }

}
