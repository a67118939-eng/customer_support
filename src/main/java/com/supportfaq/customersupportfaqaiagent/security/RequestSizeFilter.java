package com.supportfaq.customersupportfaqaiagent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
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
@Order(-90)
public class RequestSizeFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Value("${app.security.max-api-request-bytes:65536}")
    private long maxApiRequestBytes;

    @Value("${app.security.voice.max-upload-bytes:5242880}")
    private long maxVoiceUploadBytes;

    public RequestSizeFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api/")) {
            long maxBytes = request.getRequestURI().startsWith("/api/voice/")
                    ? maxVoiceUploadBytes
                    : maxApiRequestBytes;
            long contentLength = request.getContentLengthLong();
            if (contentLength > maxBytes) {
                writeJson(response, HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,
                        "Request payload is too large.");
                return;
            }
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
