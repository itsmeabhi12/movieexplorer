package com.abhishek.movieexplorer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.abhishek.movieexplorer.dto.response.MovieDTO;
import com.abhishek.movieexplorer.dto.response.MovieSearchResponse;
import com.abhishek.movieexplorer.exception.TmdbApiException;

@Service
public class TmdbService {

    final private String baseUrl;

    final RestTemplate restTemplate;

    TmdbService(RestTemplate restTemplate, @Value("${tmdb.api.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    private static final String DISCOVER_ENDPOINT = "/discover/movie";
    private static final String SEARCH_ENDPOINT = "/search/movie";

    public MovieSearchResponse getMovies(Integer page) {

        try {
            String url = UriComponentsBuilder
                    .fromUriString(baseUrl + DISCOVER_ENDPOINT)
                    .queryParam("page", page)
                    .build()
                    .toUriString();
            return restTemplate.getForObject(url, MovieSearchResponse.class);
        } catch (Exception e) {
            throw new TmdbApiException(e.getMessage());
        }

    }

    public MovieSearchResponse searchMovies(String query, Integer page) {
        try {
            String url = UriComponentsBuilder
                    .fromUriString(baseUrl + SEARCH_ENDPOINT)
                    .queryParam("page", page)
                    .queryParam("query", query)
                    .build()
                    .toUriString();
            return restTemplate.getForObject(url, MovieSearchResponse.class);
        } catch (Exception e) {
            throw new TmdbApiException(e.getMessage());
        }
    }

    public MovieDTO getMovie(Long id) {
        try {
            String url = UriComponentsBuilder
                    .fromUriString(baseUrl + "/movie/" + id)
                    .build()
                    .toUriString();
            return restTemplate.getForObject(url, MovieDTO.class);
        } catch (Exception e) {
            throw new TmdbApiException(e.getMessage());
        }
    }
}