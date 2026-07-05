package com.supportfaq.customersupportfaqaiagent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuditLogService;
import com.supportfaq.customersupportfaqaiagent.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(2)
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper;

    public RateLimitFilter(RateLimitService rateLimitService, AuditLogService auditLogService,
                           ObjectMapper objectMapper) {
        this.rateLimitService = rateLimitService;
        this.auditLogService = auditLogService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String ipAddress = auditLogService.getClientIp(request);
        if (!rateLimitService.isAllowed(ipAddress, request.getRequestURI(), request.getMethod())) {
            auditLogService.log("RATE_LIMIT_EXCEEDED", "MEDIUM",
                    "Rate limit exceeded for " + request.getMethod() + " " + request.getRequestURI(), request);
            writeJson(response, 429, "Too many requests. Please wait before trying again.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        objectMapper.writeValue(response.getWriter(), new ApiErrorResponse(message, status));
    }
}
