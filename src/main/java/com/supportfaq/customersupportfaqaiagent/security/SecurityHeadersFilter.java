package com.supportfaq.customersupportfaqaiagent.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(-100)
public class SecurityHeadersFilter extends OncePerRequestFilter {

    @Value("${app.security.headers.enabled:true}")
    private boolean enabled;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (enabled) {
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("X-Frame-Options", "DENY");
            response.setHeader("Referrer-Policy", "no-referrer");

            response.setHeader(
                    "Permissions-Policy",
                    "microphone=(self), camera=(), geolocation=()"
            );

            response.setHeader(
                    "Content-Security-Policy",
                    "default-src 'self'; " +
                            "script-src 'self' 'unsafe-inline'; " +
                            "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; " +
                            "font-src 'self' https://fonts.gstatic.com data:; " +
                            "img-src 'self' data:; " +
                            "connect-src 'self'; " +
                            "base-uri 'self'; " +
                            "object-src 'none'; " +
                            "form-action 'self'; " +
                            "frame-ancestors 'none';"
            );

            String path = request.getRequestURI();
            if (path.startsWith("/api/")
                    || path.equals("/login.html")
                    || path.equals("/auth.js")
                    || path.equals("/auth.css")) {
                response.setHeader("Cache-Control", "no-store");
            }
        }

        filterChain.doFilter(request, response);
    }
}
