package com.github.boardyb.jofogas.search;

import com.github.boardyb.jofogas.search.criteria.SearchCriteria;
import com.github.boardyb.jofogas.search.extract.SearchListElementExtractor;
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

import static com.github.boardyb.jofogas.search.request.SearchRequestBase.initSearchRequestHeaders;
import static com.github.boardyb.jofogas.search.request.SearchURLStringBuilder.SITE_BASE_URL;
import static com.google.common.collect.Lists.newArrayList;

/**
 * A basic implementation of SearchClient interfaces.
 * It executes HttpGet request to acquire the search result html and parse data from it using Jsoup.
 * The data from the parsed document is being extracted to SearchListElement objects.
 */
@Component
public class BasicSearchClient implements SearchClient {

    private Logger logger = LoggerFactory.getLogger(BasicSearchClient.class);

    @Autowired
    private SearchListElementExtractor extractor;

    @Autowired
    private HttpClient httpClient;

    public List<SearchListElement> search(String term) throws Exception {
        return this.search(new SearchCriteria(term));
    }
    
    @Override
    public List<SearchListElement> search(SearchCriteria searchCriteria) throws Exception {
        List<SearchListElement> searchResults = newArrayList();

        HttpGet request = createSearchRequest(searchCriteria);

        logger.info(request.getURI().toString());

        HttpResponse response = httpClient.execute(request);

        Document resultPage = createSearchResultPage(response);

        return extractSearchResults(searchResults, resultPage);
    }

    /**
     * This method creates a valid HttpGet request and populate it with the headers necessary
     * for searching on jofogas.hu site.
     *
     * @param searchCriteria criteria provides the necessary URI which instantiates the HttpGet request.
     * @return this method returns a HttpGet request which contains the URI to which it will be sent and the
     * request headers which are necessary for the successful execution of the request.
     * @throws Exception if an error happens while generating the URI for the request.
     */
    private HttpGet createSearchRequest(SearchCriteria searchCriteria) throws Exception {
        HttpGet request = new HttpGet(searchCriteria.generateURI());
        initSearchRequestHeaders(request);
        return request;
    }

    /**
     * This method parses a Jsoup Document from the content input stream of HttpResponse.
     *
     * @param response HttpResponse which contains the content of the html which if going to be parsed by Jsoup.
     * @return a Jsoup document which is parsed from the HttpResponse.
     * @throws IOException if Document cannot be parsed from InputStream
     */
    private Document createSearchResultPage(HttpResponse response) throws IOException {
        InputStream contentStream = response.getEntity().getContent();
        Document resultPage = Jsoup.parse(contentStream, "ISO-8859-1", SITE_BASE_URL);
        contentStream.close();
        return resultPage;
    }

    /**
     * This method extracts the search results as SearchListElement objects from the document.
     *
     * @param searchResults search results extracted from the document.
     * @param resultPage    a Jsoup document from which the search results will be extracted.
     * @return returns the results parsed from the document in SearchListElement format.
     */
    private List<SearchListElement> extractSearchResults(List<SearchListElement> searchResults, Document resultPage) {
        Elements resultListFull = resultPage.select(".col-xs-12.box.listing.list-item.reListElement");
        resultListFull.forEach((Element element) -> searchResults.add(extractor.extractFromElement(element)));

        return searchResults;
    }
}
