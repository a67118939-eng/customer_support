package com.supportfaq.customersupportfaqaiagent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@Order(-70)
public class AuthSessionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    private final Set<String> publicStaticFiles = Set.of(
            "/",
            "/login.html",
            "/auth.css",
            "/auth.js",
            "/style.css",
            "/script.js",
            "/favicon.ico",
            "/rushd-ai-logo-login-glow.png"
    );

    public AuthSessionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!requiresAuthentication(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean adminEndpoint = requiresAdmin(path, request.getMethod());

        if (!AuthService.isAuthenticated(request)) {
            if (adminEndpoint && hasAdminApiKey(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (path.startsWith("/api/")) {
                writeJson(response, HttpServletResponse.SC_UNAUTHORIZED, "Please log in first.");
            } else {
                response.sendRedirect("/login.html");
            }

            return;
        }

        if (adminEndpoint && !AuthService.isAdmin(request)) {
            writeJson(response, HttpServletResponse.SC_FORBIDDEN, "Admin access is required.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/api/auth/")
                || publicStaticFiles.contains(path)
                || path.endsWith(".png")
                || path.endsWith(".jpg")
                || path.endsWith(".jpeg")
                || path.endsWith(".webp")
                || path.endsWith(".svg")
                || path.endsWith(".ico")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.startsWith("/private/")
                || path.startsWith("/internal/")
                || path.startsWith("/config/")
                || path.startsWith("/admin/secrets")
                || path.startsWith("/backup/")
                || path.startsWith("/system/");
    }

    private boolean requiresAuthentication(String path) {
        return path.equals("/index.html")
                || path.startsWith("/api/")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/api-docs");
    }

    private boolean requiresAdmin(String path, String method) {
        if (path.startsWith("/swagger-ui") || path.startsWith("/api-docs")) {
            return true;
        }

        if (path.startsWith("/api/security")
                || path.startsWith("/api/dashboard")
                || path.startsWith("/api/unanswered")) {
            return true;
        }

        if (path.equals("/api/chat/history")) {
            return true;
        }

        if (path.startsWith("/api/feedback") && !"POST".equalsIgnoreCase(method)) {
            return true;
        }

        if (path.startsWith("/api/tickets") && !"POST".equalsIgnoreCase(method)) {
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

        if (path.equals("/api/faqs")) {
            return !"GET".equalsIgnoreCase(method);
        }

        return path.matches("^/api/faqs/\\d+$") && !"GET".equalsIgnoreCase(method);
    }

    private boolean hasAdminApiKey(HttpServletRequest request) {
        String suppliedKey = request.getHeader("X-Admin-API-Key");
        return suppliedKey != null && !suppliedKey.isBlank();
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        objectMapper.writeValue(response.getWriter(), new ApiErrorResponse(message, status));
    }
}