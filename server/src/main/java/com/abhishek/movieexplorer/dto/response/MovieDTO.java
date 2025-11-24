package com.abhishek.movieexplorer.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private Long id;

    private boolean adult;

    private String backdropPath;

    private String originalLanguage;

    private String originalTitle;

    private String overview;

    private Double popularity;

    private String posterPath;

    private String releaseDate;

    private String title;

    private Boolean video;

    private Double voteAverage;

    private Integer voteCount;
}
