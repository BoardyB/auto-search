package com.github.boardyb.jofogas.search.request;

import ie.corballis.fixtures.annotation.Fixture;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Test;

import static ie.corballis.fixtures.assertion.FixtureAssert.assertThat;

public class SearchRequestBaseTest {

    @Fixture("defaultHeaders")
    private BasicHeader[] defaultHeaders;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldAddRequestHeaders() throws Exception {
        HttpGet httpGet = new HttpGet();
        SearchRequestBase.initSearchRequestHeaders(httpGet);
        assertThat(httpGet.getAllHeaders()).matches("defaultHeaders");
    }
}