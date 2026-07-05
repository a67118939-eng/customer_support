package com.supportfaq.customersupportfaqaiagent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuditLogService;
import com.supportfaq.customersupportfaqaiagent.service.BlockedIpService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class IpBlockFilter extends OncePerRequestFilter {

    private final BlockedIpService blockedIpService;
    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper;

    public IpBlockFilter(BlockedIpService blockedIpService, AuditLogService auditLogService,
                         ObjectMapper objectMapper) {
        this.blockedIpService = blockedIpService;
        this.auditLogService = auditLogService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String ipAddress = auditLogService.getClientIp(request);
        if (blockedIpService.getActiveBlock(ipAddress).isPresent()) {
            writeJson(response, HttpServletResponse.SC_FORBIDDEN, "Access denied.");
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
