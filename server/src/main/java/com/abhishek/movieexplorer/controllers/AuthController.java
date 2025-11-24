package com.abhishek.movieexplorer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abhishek.movieexplorer.dto.request.AuthRequest;
import com.abhishek.movieexplorer.dto.response.AuthResponse;
import com.abhishek.movieexplorer.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    final private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody AuthRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(authService.signup(request, response));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@Valid @RequestBody AuthRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok(authService.signin(request, response));
    }

    @PostMapping("/signout")
    public ResponseEntity<AuthResponse> signout(HttpServletResponse response) {
        return ResponseEntity.ok(authService.signout(response));
    }
}
