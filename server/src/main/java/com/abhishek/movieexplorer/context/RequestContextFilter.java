package com.abhishek.movieexplorer.context;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("requestMetadataFilter")
@Order(Ordered.LOWEST_PRECEDENCE - 10) // ensure runs late but before
public class RequestContextFilter extends OncePerRequestFilter {

    private static final String REQ_ID_HEADER = "X-Request-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String requestId = request.getHeader(REQ_ID_HEADER);
        if (requestId == null || requestId.isBlank()) {
            requestId = "req-" +
                    Long.toHexString(ThreadLocalRandom.current().nextLong());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = null;

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UUID uuid) {
                userId = uuid;
            }
        }

        if (userId == null) {
            userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        }

        RequestContext.set(requestId, userId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            RequestContext.clear();
        }
    }
}