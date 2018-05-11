package com.github.boardyb.jofogas.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.isNull;

@Configuration
public class SearchConfiguration {

    @Bean
    public SearchProperties searchProperties() {
        return new SearchProperties();
    }

    @Bean
    public SearchClient searchClient() {
        SearchProperties searchProperties = searchProperties();
        String client = searchProperties.getClient();
        if (isNull(client)) {
            return new DefaulSearchClient();
        }

        boolean jofogasClient = client.equals("jofogas");
        if (jofogasClient) {
            return new JofogasSearchClient();
        } else {
            return new DefaulSearchClient();
        }
    }

}
