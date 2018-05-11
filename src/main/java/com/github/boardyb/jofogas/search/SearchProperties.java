package com.github.boardyb.jofogas.search;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jofogas.search")
public class SearchProperties {

    private String term;
    private String client;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchProperties{");
        sb.append("term='").append(term).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
