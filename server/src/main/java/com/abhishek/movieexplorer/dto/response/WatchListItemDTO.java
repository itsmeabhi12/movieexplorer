package com.abhishek.movieexplorer.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class WatchListItemDTO {

    private UUID id;

    private MovieDTO movie;

    private String notes;

    private Instant createdAt;

    private Instant updatedAt;

}
