package com.github.boardyb.jofogas.search.request;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * This class is responsible for creating and managing URL strings for the HttpClient.
 * It can create the initial URL for to execute request and it supports appending to this URL.
 */
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

    /**
     * Creates a new SearchURLStringBuilder instance with SITE_BASE_URL as initial URL.
     *
     * @return a new SearchURLStringBuilder instance.
     */
    public static SearchURLStringBuilder createURL() {
        return new SearchURLStringBuilder();
    }

    /**
     * Creates a new SearchURLStringBuilder instance with the given URL as the initial URL.
     *
     * @param url this parameter can determine what will be the starting String of the request URL.
     * @return a new SearchURLStringBuilder instance with 'url' as starting url.
     */
    public static SearchURLStringBuilder createURL(String url) {
        return new SearchURLStringBuilder(url);
    }

    /**
     * This method is responsible for appending a String segment to the URL.
     * If the segment is null or empty the URL will not be changed.
     * If the URL's last character is a '/' or if the segment starts with a '/' character this method appends it to
     * the URL.
     * If the URL's last character is not a '/' or the segment does not start with a '/' character, this method appends
     * a '/' character between the url and the segment.
     *
     * @param segment String to append to the url.
     * @return SearchURLStringBuilder instance with the segment String appended to the url.
     */
    public SearchURLStringBuilder appendSegmentToURL(String segment) {
        if (isNullOrEmpty(segment)) {
            return this;
        }

        if (url.charAt(url.length() - 1) == '/' || segment.startsWith("/")) {
            return createURL(url + segment);
        }

        return createURL(url + "/" + segment);
    }

    /**
     * This method returns the built URL String.
     *
     * @return url as String.
     */
    public String build() {
        return this.url;
    }

}
