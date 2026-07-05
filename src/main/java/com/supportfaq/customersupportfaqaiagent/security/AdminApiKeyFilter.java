package com.supportfaq.customersupportfaqaiagent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
@Order(0)
public class AdminApiKeyFilter extends OncePerRequestFilter {

    private static final String ADMIN_API_KEY_HEADER = "X-Admin-API-Key";

    private final ObjectMapper objectMapper;

    @Value("${app.security.admin-api-key:}")
    private String adminApiKey;

    @Value("${app.security.admin-api-key-required:true}")
    private boolean adminApiKeyRequired;

    public AdminApiKeyFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!requiresAdmin(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (AuthService.isAdmin(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (adminApiKey == null || adminApiKey.isBlank()) {
            if (adminApiKeyRequired) {
                writeJson(response, HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                        "Admin API key is not configured.");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        String suppliedKey = request.getHeader(ADMIN_API_KEY_HEADER);
        if (!matchesConfiguredKey(suppliedKey)) {
            writeJson(response, HttpServletResponse.SC_UNAUTHORIZED, "Admin API key is required.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean requiresAdmin(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return false;
        }
        if (path.startsWith("/api/security")
                || path.startsWith("/api/dashboard")
                || path.startsWith("/api/unanswered")) {
            return true;
        }
        if (path.startsWith("/api/feedback") && "GET".equalsIgnoreCase(method)) {
            return true;
        }
        if (path.startsWith("/api/tickets")
                && !"GET".equalsIgnoreCase(method)
                && !"POST".equalsIgnoreCase(method)) {
            return true;
        }
        if (path.startsWith("/api/categories") && !"GET".equalsIgnoreCase(method)) {
            return true;
        }
        if (path.startsWith("/api/faqs/generated")
                || path.startsWith("/api/faqs/pending-review")
                || path.matches("^/api/faqs/\\d+/(approve|reject)$")) {
            return true;
        }
        return path.equals("/api/faqs")
                ? !"GET".equalsIgnoreCase(method)
                : path.matches("^/api/faqs/\\d+$") && !"GET".equalsIgnoreCase(method);
    }

    private boolean matchesConfiguredKey(String suppliedKey) {
        if (suppliedKey == null || suppliedKey.isBlank()) {
            return false;
        }
        byte[] expected = adminApiKey.getBytes(StandardCharsets.UTF_8);
        byte[] actual = suppliedKey.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(expected, actual);
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        objectMapper.writeValue(response.getWriter(), new ApiErrorResponse(message, status));
    }
}
