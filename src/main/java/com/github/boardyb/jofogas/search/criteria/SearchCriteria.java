package com.github.boardyb.jofogas.search.criteria;

import org.apache.http.client.utils.URIBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.boardyb.jofogas.search.request.SearchURLStringBuilder.createURL;
import static com.google.common.base.Strings.isNullOrEmpty;

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

    public SearchCriteria() {
    }

    public URI generateURI() throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(buildURLString());
        addTermIfNotEmpty(uriBuilder);
        addMaxPriceIfNotZero(uriBuilder);
        addMinPriceIfNotZero(uriBuilder);
        return uriBuilder.build();
    }

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

    private void addTermIfNotEmpty(URIBuilder uriBuilder) {
        if (!isNullOrEmpty(this.term)) {
            uriBuilder.setParameter("q", this.term);
        }
    }

    private void addMaxPriceIfNotZero(URIBuilder uriBuilder) {
        if (maxPrice != 0) {
            uriBuilder.setParameter("max_price", String.valueOf(maxPrice));
        }
    }

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
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchCriteria{");
        sb.append("region='").append(region).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", distanceFromCity='").append(distanceFromCity).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", maxPrice=").append(maxPrice);
        sb.append(", minPrice=").append(minPrice);
        sb.append(", term='").append(term).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
