package com.github.boardyb.jofogas.search.request;

import static com.google.common.base.Strings.isNullOrEmpty;

public class SearchURLStringBuilder {

    public static final String SITE_BASE_URL = "https://www.jofogas.hu/";

    private String url;

    private SearchURLStringBuilder(String url) {
        if (isNullOrEmpty(url)) {
            this.url = SITE_BASE_URL;
            return;
        }
        this.url = url;
    }

    public SearchURLStringBuilder() {
        this.url = SITE_BASE_URL;
    }

    public static SearchURLStringBuilder createURL() {
        return new SearchURLStringBuilder();
    }

    public static SearchURLStringBuilder createURL(String url) {
        return new SearchURLStringBuilder(url);
    }

    public SearchURLStringBuilder appendSegmentToURL(String segment) {
        if (isNullOrEmpty(segment)) {
            return this;
        }

        if (url.charAt(url.length() - 1) == '/' || segment.startsWith("/")) {
            return createURL(url + segment);
        }

        return createURL(url + "/" + segment);
    }

    public String build() {
        return this.url;
    }

}
