package com.github.boardyb.jofogas.search.criteria;

import org.junit.Test;

import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;

public class SearchCriteriaTest {

    @Test
    public void shouldBuildUriForCriteria() throws Exception {
        SearchCriteria criteria = createTestCriteria();
        URI uri = criteria.generateURI();
        assertThat(uri.toString()).isEqualTo(
            "https://www.jofogas.hu/magyarorszag/miskolc/12-km/category?q=term&max_price=10000&min_price=5000");
    }

    private SearchCriteria createTestCriteria() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setRegion(RegionTypes.DEFAULT);
        searchCriteria.setCity("miskolc");
        searchCriteria.setDistanceFromCity(12);
        searchCriteria.setCategory("category");
        searchCriteria.setMaxPrice(10000);
        searchCriteria.setMinPrice(5000);
        searchCriteria.setTerm("term");
        return searchCriteria;
    }

    @Test
    public void shouldBuildUrlString() throws Exception {
        SearchCriteria criteria = createTestCriteria();
        String urlString = criteria.buildURLString();
        assertThat(urlString).isEqualTo("https://www.jofogas.hu/magyarorszag/miskolc/12-km/category");
    }

    @Test
    public void shouldBuildUrlStringIfDistanceFromCityIsNotGiven() throws Exception {
        SearchCriteria criteria = createTestCriteria();
        criteria.setDistanceFromCity(0);
        String urlString = criteria.buildURLString();
        assertThat(urlString).isEqualTo("https://www.jofogas.hu/magyarorszag/miskolc/category");
    }
}