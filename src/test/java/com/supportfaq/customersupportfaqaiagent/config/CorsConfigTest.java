package com.supportfaq.customersupportfaqaiagent.config;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.DefaultCorsProcessor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CorsConfigTest {

    @Test
    void apiCorsAllowsRailwayOriginForAuthPosts() throws ServletException, IOException {
        CorsConfiguration configuration = configurationWithPatterns(
                "http://localhost:8081",
                "https://customersupport-production-4e02.up.railway.app"
        );
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/auth/register");
        request.addHeader(HttpHeaders.ORIGIN, "https://customersupport-production-4e02.up.railway.app");
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean allowed = new DefaultCorsProcessor().processRequest(configuration, request, response);

        assertTrue(allowed);
        assertEquals("https://customersupport-production-4e02.up.railway.app",
                response.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
        assertEquals("true", response.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS));
    }

    @Test
    void apiCorsAllowsCsrfHeaderInPreflightRequests() throws ServletException, IOException {
        CorsConfiguration configuration = configurationWithPatterns("https://customersupport-production-4e02.up.railway.app");
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/faqs");
        request.addHeader(HttpHeaders.ORIGIN, "https://customersupport-production-4e02.up.railway.app");
        request.addHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST");
        request.addHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "content-type,x-csrf-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean allowed = new DefaultCorsProcessor().processRequest(configuration, request, response);

        assertTrue(allowed);
        assertEquals("https://customersupport-production-4e02.up.railway.app",
                response.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }

    @Test
    void apiCorsRejectsUnlistedOrigins() throws ServletException, IOException {
        CorsConfiguration configuration = configurationWithPatterns("https://customersupport-production-4e02.up.railway.app");
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/auth/register");
        request.addHeader(HttpHeaders.ORIGIN, "https://attacker.example");
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean allowed = new DefaultCorsProcessor().processRequest(configuration, request, response);

        assertFalse(allowed);
    }

    private CorsConfiguration configurationWithPatterns(String... patterns) {
        CorsConfig config = new CorsConfig();
        ReflectionTestUtils.setField(config, "allowedOriginPatterns", patterns);
        return config.apiCorsConfiguration();
    }
}
