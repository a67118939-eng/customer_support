package com.supportfaq.customersupportfaqaiagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final String[] DEFAULT_ALLOWED_ORIGIN_PATTERNS = {
            "http://localhost:8081",
            "http://127.0.0.1:8081",
            "http://localhost:8080",
            "http://127.0.0.1:8080",
            "https://customersupport-production-4e02.up.railway.app"
    };

    @Value("${app.security.cors.allowed-origin-patterns:${app.security.cors.allowed-origins:http://localhost:8081,http://127.0.0.1:8081,http://localhost:8080,http://127.0.0.1:8080,https://customersupport-production-4e02.up.railway.app}}")
    private String[] allowedOriginPatterns;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsConfiguration configuration = apiCorsConfiguration();
        registry.addMapping("/api/**")
                .allowedOriginPatterns(configuration.getAllowedOriginPatterns().toArray(String[]::new))
                .allowedMethods(configuration.getAllowedMethods().toArray(String[]::new))
                .allowedHeaders(configuration.getAllowedHeaders().toArray(String[]::new))
                .allowCredentials(Boolean.TRUE.equals(configuration.getAllowCredentials()))
                .maxAge(configuration.getMaxAge());
    }

    CorsConfiguration apiCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(cleanPatterns()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Content-Type",
                "Authorization",
                "X-CSRF-Token",
                "X-Admin-API-Key",
                "X-Admin-Api-Key"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        return configuration;
    }

    private String[] cleanPatterns() {
        String[] cleaned = allowedOriginPatterns == null ? new String[0] : Arrays.stream(allowedOriginPatterns)
                .map(pattern -> pattern == null ? "" : pattern.trim())
                .filter(pattern -> !pattern.isBlank())
                .toArray(String[]::new);
        return cleaned.length == 0 ? DEFAULT_ALLOWED_ORIGIN_PATTERNS : cleaned;
    }
}
