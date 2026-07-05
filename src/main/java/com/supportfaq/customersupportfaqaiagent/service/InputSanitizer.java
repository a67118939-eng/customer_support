package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class InputSanitizer {

    private static final Pattern CONTROL_CHARS = Pattern.compile("[\\p{Cntrl}&&[^\r\n\t]]");
    private static final Pattern REPEATED_WHITESPACE = Pattern.compile("[\\s&&[^\\r\\n]]+");

    public String text(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        String sanitized = CONTROL_CHARS.matcher(value).replaceAll("")
                .replace("\u0000", "")
                .trim();
        sanitized = REPEATED_WHITESPACE.matcher(sanitized).replaceAll(" ");
        if (sanitized.length() > maxLength) {
            throw new BadRequestException("Input is too long.");
        }
        return escapeHtml(sanitized);
    }

    public String plainText(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        String sanitized = CONTROL_CHARS.matcher(value).replaceAll("")
                .replace("\u0000", "")
                .trim();
        sanitized = REPEATED_WHITESPACE.matcher(sanitized).replaceAll(" ");
        if (sanitized.length() > maxLength) {
            throw new BadRequestException("Input is too long.");
        }
        return sanitized;
    }

    public String requiredText(String value, int maxLength, String fieldName) {
        String sanitized = text(value, maxLength);
        if (sanitized == null || sanitized.isBlank()) {
            throw new BadRequestException(fieldName + " is required.");
        }
        return sanitized;
    }

    public String enumValue(String value, String fallback, Set<String> allowed, String fieldName) {
        String candidate = plainText(value, 40);
        if (candidate == null || candidate.isBlank()) {
            candidate = fallback;
        }
        String normalized = candidate.toUpperCase(Locale.ROOT);
        if (!allowed.contains(normalized)) {
            throw new BadRequestException("Invalid " + fieldName + ".");
        }
        return normalized;
    }

    public String sessionId(String value) {
        String sanitized = plainText(value, 120);
        if (sanitized == null || sanitized.isBlank()) {
            return "anonymous";
        }
        return sanitized.replaceAll("[^A-Za-z0-9._:-]", "_");
    }

    public String ipAddress(String value) {
        String sanitized = plainText(value, 80);
        if (sanitized == null || sanitized.isBlank()) {
            throw new BadRequestException("IP address is required.");
        }
        if (!sanitized.matches("^[A-Fa-f0-9:.]{3,45}$")) {
            throw new BadRequestException("IP address is invalid.");
        }
        return sanitized;
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}
