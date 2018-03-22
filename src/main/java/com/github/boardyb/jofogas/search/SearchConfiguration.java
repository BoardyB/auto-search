package com.github.boardyb.jofogas.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchConfiguration {

    @Bean
    public SearchProperties searchProperties() {
        return new SearchProperties();
    }
    
}
