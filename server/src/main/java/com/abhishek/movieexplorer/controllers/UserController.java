package com.abhishek.movieexplorer.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abhishek.movieexplorer.dto.response.UserResponse;
import com.abhishek.movieexplorer.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(userService.getMe(userId));
    }
}