package com.github.boardyb.jofogas.search;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jofogas.search")
public class SearchProperties {

    private String term;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchProperties{");
        sb.append("term='").append(term).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
