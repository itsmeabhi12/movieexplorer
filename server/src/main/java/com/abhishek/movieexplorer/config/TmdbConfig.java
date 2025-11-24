package com.abhishek.movieexplorer.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class TmdbConfig {
    @Value("${tmdb.api.key}")
    private String apiKey;

    @Bean
    public RestTemplate tmdbRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
            URI uri = UriComponentsBuilder.fromUri(request.getURI())
                    .queryParam("api_key", apiKey)
                    .build()
                    .toUri();

            HttpRequestWrapper wrapped = new HttpRequestWrapper(request) {
                @Override
                public URI getURI() {
                    return uri;
                }
            };

            return execution.execute(wrapped, body);
        });

        return restTemplate;
    }

}
