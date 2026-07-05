package com.supportfaq.customersupportfaqaiagent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuditLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
@Order(-80)
public class SuspiciousRequestFilter extends OncePerRequestFilter {

    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper;

    private final List<Pattern> suspiciousPatterns = List.of(
            Pattern.compile("(\\.\\./|\\.\\.\\\\|%2e%2e|%252e%252e)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(<script|javascript:|onerror\\s*=|onload\\s*=)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\bunion\\b\\s+\\bselect\\b|\\bdrop\\b\\s+\\btable\\b|\\binformation_schema\\b)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\$\\{|\\{\\{|<\\?php|/etc/passwd|cmd\\.exe|powershell)", Pattern.CASE_INSENSITIVE)
    );

    public SuspiciousRequestFilter(AuditLogService auditLogService, ObjectMapper objectMapper) {
        this.auditLogService = auditLogService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String target = normalize(request.getRequestURI() + "?" + (request.getQueryString() == null ? "" : request.getQueryString()));
        if (isSuspicious(target)) {
            auditLogService.log("SUSPICIOUS_REQUEST_BLOCKED", "HIGH",
                    "Blocked suspicious request for " + request.getMethod() + " " + request.getRequestURI(),
                    request);
            writeJson(response, HttpServletResponse.SC_BAD_REQUEST, "Suspicious request blocked.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isSuspicious(String target) {
        return suspiciousPatterns.stream().anyMatch(pattern -> pattern.matcher(target).find());
    }

    private String normalize(String value) {
        String decoded = value;
        for (int i = 0; i < 2; i++) {
            try {
                decoded = URLDecoder.decode(decoded, StandardCharsets.UTF_8);
            } catch (IllegalArgumentException ignored) {
                break;
            }
        }
        return decoded.toLowerCase(Locale.ROOT);
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        objectMapper.writeValue(response.getWriter(), new ApiErrorResponse(message, status));
    }
}
