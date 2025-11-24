package com.abhishek.movieexplorer.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class UserResponse {

    private UUID id;

    private String username;

    private Instant createdAt;

    private Instant updatedAt;

}
