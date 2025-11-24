package com.abhishek.movieexplorer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abhishek.movieexplorer.dto.response.MovieSearchResponse;
import com.abhishek.movieexplorer.service.TmdbService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {

    private final TmdbService tmdbService;

    @GetMapping
    public ResponseEntity<MovieSearchResponse> getMovies(
            @RequestParam(name = "page", defaultValue = "1") Integer page) {
        return ResponseEntity.ok(tmdbService.getMovies(page));
    }

    @GetMapping("/search")
    public ResponseEntity<MovieSearchResponse> searchMovies(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "page", defaultValue = "1") Integer page) {
        return ResponseEntity.ok(tmdbService.searchMovies(query, page));
    }
}
