package com.abhishek.movieexplorer.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_cache")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    private Long id;

    @Column
    private boolean adult;

    @Column
    private String backdropPath;

    @Column
    private String originalLanguage;

    @Column
    private String originalTitle;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column
    private Double popularity;

    @Column
    private String posterPath;

    @Column
    private LocalDate releaseDate;

    @Column
    private String title;

    @Column
    private Boolean video;

    @Column
    private Double voteAverage;

    @Column
    private Integer voteCount;
}
