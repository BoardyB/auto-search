package com.github.boardyb.jofogas.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.boardyb.jofogas.http.HttpClientConfigProvider.createConnectionManager;
import static com.github.boardyb.jofogas.http.HttpClientConfigProvider.createSocketFactory;

@Configuration
public class HttpAutoConfiguration {

    @Bean
    public HttpClient httpClient() throws Exception {
        return HttpClients.custom()
                .setSSLSocketFactory(createSocketFactory())
                .setConnectionManager(createConnectionManager())
                .build();
    }

}
