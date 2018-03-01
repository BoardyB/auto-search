package com.github.boardyb.jofogas.search.request;

import org.apache.http.client.methods.HttpGet;

public class SearchRequestBase {

    public static void initSearchRequestHeaders(HttpGet request) {
        request.setHeader("Accept",
                          "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Accept-Language", "hu-HU,hu;q=0.9,en-US;q=0.8,en;q=0.7");
        request.setHeader("Host", "www.jofogas.hu");
        request.setHeader("Upgrade-Insecure-Requests", "1");
        request.setHeader("Referer", "http://www.jofogas.hu/");
        request.setHeader("User-Agent",
                          "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    }

}
