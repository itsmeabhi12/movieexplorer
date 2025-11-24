package com.abhishek.movieexplorer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// This is basic app so we we same DTO for both signup/login 
@Data
public class AuthRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username should not be less than 3 characters and more than 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;
}
