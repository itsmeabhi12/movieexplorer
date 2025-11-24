package com.abhishek.movieexplorer.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AddToWatchListRequest {

    @NotNull(message = "movie id is required")
    private Long tmdbMovieId;

    private String notes;

}
