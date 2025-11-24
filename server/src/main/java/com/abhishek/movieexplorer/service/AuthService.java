package com.abhishek.movieexplorer.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abhishek.movieexplorer.dto.request.AuthRequest;
import com.abhishek.movieexplorer.dto.response.AuthResponse;
import com.abhishek.movieexplorer.exception.DuplicateResourceException;
import com.abhishek.movieexplorer.exception.UnauthorizedException;
import com.abhishek.movieexplorer.model.User;
import com.abhishek.movieexplorer.repository.UserRepository;
import com.abhishek.movieexplorer.security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final String COOKIE_NAME = "authToken";

    public AuthResponse signup(AuthRequest request, HttpServletResponse response) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("user already exist");
        }

        final User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        final User savedUser = userRepository.save(user);
        final String accessToken = jwtUtil.createToken(savedUser.getId().toString());

        setCookie(response, accessToken);

        return new AuthResponse("User created successfully");
    }

    public AuthResponse signin(AuthRequest request, HttpServletResponse response) {

        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if (user.isEmpty()) {
            throw new UnauthorizedException("username or password doesn't match");
        }

        String userPassword = user.get().getPassword();
        if (!passwordEncoder.matches(request.getPassword(), userPassword)) {
            throw new UnauthorizedException("username or password doesn't match");
        }

        String accessToken = jwtUtil.createToken(user.get().getId().toString());

        setCookie(response, accessToken);

        return new AuthResponse("Login successful");
    }

    public AuthResponse signout(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new AuthResponse("Logout successful");
    }

    private void setCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setMaxAge(jwtUtil.getExpiration().intValue() / 1000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

}
