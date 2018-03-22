package com.github.boardyb.jofogas.search.criteria;

import org.apache.http.client.utils.URIBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.github.boardyb.jofogas.search.request.SearchURLStringBuilder.createURL;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * This class represents properties to filter the search by.
 * It is also responsible for generating URI for HttpClient to post and adding the criteria to this URI.
 */
public class SearchCriteria {

    private RegionTypes region = RegionTypes.DEFAULT;
    private String city;
    private int distanceFromCity;
    private String category;
    private int maxPrice;
    private int minPrice;
    @NotNull
    private String term;

    public SearchCriteria(RegionTypes region,
                          String city,
                          int distanceFromCity,
                          String category,
                          int maxPrice,
                          int minPrice,
                          String term) {
        this.region = region;
        this.city = city;
        this.distanceFromCity = distanceFromCity;
        this.category = category;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.term = term;
    }

    public SearchCriteria(String term) {
        this.term = term;
    }

    public SearchCriteria() {
    }

    /**
     * This method is responsible for generating an URI for the HttpClient to post and adding criteria to the URI.
     *
     * @return URI which contains criteria.
     * @throws URISyntaxException if an error occurs during URI building.
     */
    public URI generateURI() throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(buildURLString());
        addTermIfNotEmpty(uriBuilder);
        addMaxPriceIfNotZero(uriBuilder);
        addMinPriceIfNotZero(uriBuilder);
        return uriBuilder.build();
    }

    /**
     * This method creates an URL string which contains the criteria that needs to appear in the URL.
     *
     * @return URL string with criteria.
     */
    protected String buildURLString() {
        if (distanceFromCity == 0) {
            return createURL().appendSegmentToURL(this.region.getValue())
                              .appendSegmentToURL(this.city)
                              .appendSegmentToURL(this.category)
                              .build();
        } else {
            return createURL().appendSegmentToURL(this.region.getValue())
                              .appendSegmentToURL(this.city)
                              .appendSegmentToURL(this.distanceFromCity + "-km")
                              .appendSegmentToURL(this.category)
                              .build();

        }
    }

    /**
     * Adds search term to URI if it is not null or empty.
     *
     * @param uriBuilder with search term added as URI parameter.
     */
    private void addTermIfNotEmpty(URIBuilder uriBuilder) {
        if (!isNullOrEmpty(this.term)) {
            uriBuilder.setParameter("q", this.term);
        }
    }

    /**
     * Adds max price to URI if it is not zero.
     *
     * @param uriBuilder with max price added as URI parameter.
     */
    private void addMaxPriceIfNotZero(URIBuilder uriBuilder) {
        if (maxPrice != 0) {
            uriBuilder.setParameter("max_price", String.valueOf(maxPrice));
        }
    }

    /**
     * Adds min price to URI if it is not zero.
     *
     * @param uriBuilder with min price added as URI parameter.
     */
    private void addMinPriceIfNotZero(URIBuilder uriBuilder) {
        if (minPrice != 0) {
            uriBuilder.setParameter("min_price", String.valueOf(minPrice));
        }
    }

    public RegionTypes getRegion() {
        return region;
    }

    public void setRegion(RegionTypes region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDistanceFromCity() {
        return distanceFromCity;
    }

    public void setDistanceFromCity(int distanceFromCity) {
        this.distanceFromCity = distanceFromCity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchCriteria that = (SearchCriteria) o;
        return distanceFromCity == that.distanceFromCity && maxPrice == that.maxPrice && minPrice == that.minPrice &&
               region == that.region && Objects.equals(city, that.city) && Objects.equals(category, that.category) &&
               Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {

        return Objects.hash(region, city, distanceFromCity, category, maxPrice, minPrice, term);
    }
}
