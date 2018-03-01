package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchScheduler searchScheduler;

    @PostMapping
    public void search(@RequestBody @Valid SearchCriteria criteria) {
        searchScheduler.setSearchCriteria(criteria);
    }

}
