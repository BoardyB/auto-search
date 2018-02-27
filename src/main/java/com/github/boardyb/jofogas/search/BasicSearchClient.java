package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.github.boardyb.jofogas.search.SearchRequestBase.initSearchRequestHeaders;
import static com.github.boardyb.jofogas.search.SearchURLStringBuilder.SITE_BASE_URL;
import static com.google.common.collect.Lists.newArrayList;

@Component
public class BasicSearchClient implements SearchClient {

    private Logger logger = LoggerFactory.getLogger(BasicSearchClient.class);

    @Autowired
    private SearchListElementExtractor extractor;

    @Autowired
    private HttpClient httpClient;

    @Override
    public List<SearchListElement> search(SearchCriteria searchCriteria) throws Exception {
        List<SearchListElement> searchResults = newArrayList();

        HttpGet request = createSearchRequest(searchCriteria);

        logger.info(request.getURI().toString());

        HttpResponse response = httpClient.execute(request);

        Document resultPage = createSearchResultPage(response);

        return extractSearchResults(searchResults, resultPage);
    }

    private HttpGet createSearchRequest(SearchCriteria searchCriteria) throws Exception {
        HttpGet request = new HttpGet(searchCriteria.generateURI());
        initSearchRequestHeaders(request);
        return request;
    }

    private Document createSearchResultPage(HttpResponse response) throws IOException {
        InputStream contentStream = response.getEntity().getContent();
        Document resultPage = Jsoup.parse(contentStream, "ISO-8859-1", SITE_BASE_URL);
        contentStream.close();
        return resultPage;
    }

    private List<SearchListElement> extractSearchResults(List<SearchListElement> searchResults, Document resultPage) {
        Elements resultListFull = resultPage.select(".col-xs-12.box.listing.list-item.reListElement");
        resultListFull.forEach((Element element) -> searchResults.add(extractor.extractFromElement(element)));

        return searchResults;
    }
}
