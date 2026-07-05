package com.supportfaq.customersupportfaqaiagent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
@Order(-60)
public class CsrfProtectionFilter extends OncePerRequestFilter {

    private static final String CSRF_HEADER = "X-CSRF-Token";
    private static final Set<String> SAFE_METHODS = Set.of("GET", "HEAD", "OPTIONS");

    private final ObjectMapper objectMapper;

    public CsrfProtectionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!requiresCsrf(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        Object expected = session == null ? null : session.getAttribute(AuthService.SESSION_CSRF);
        String supplied = request.getHeader(CSRF_HEADER);
        if (!matches(expected, supplied)) {
            writeJson(response, HttpServletResponse.SC_FORBIDDEN, "Security token is missing or invalid.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean requiresCsrf(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI();
        if (!path.startsWith("/api/") || SAFE_METHODS.contains(method)) {
            return false;
        }
        if (hasAdminApiKey(request)) {
            return false;
        }
        return !path.equals("/api/auth/login") && !path.equals("/api/auth/register");
    }

    private boolean hasAdminApiKey(HttpServletRequest request) {
        String suppliedKey = request.getHeader("X-Admin-API-Key");
        return suppliedKey != null && !suppliedKey.isBlank();
    }

    private boolean matches(Object expected, String supplied) {
        if (expected == null || supplied == null || supplied.isBlank()) {
            return false;
        }
        byte[] expectedBytes = expected.toString().getBytes(StandardCharsets.UTF_8);
        byte[] suppliedBytes = supplied.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(expectedBytes, suppliedBytes);
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        objectMapper.writeValue(response.getWriter(), new ApiErrorResponse(message, status));
    }
}
