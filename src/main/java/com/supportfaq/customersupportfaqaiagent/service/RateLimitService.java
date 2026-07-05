package com.supportfaq.customersupportfaqaiagent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Value("${app.security.rate-limit.chat-per-minute:20}")
    private int chatPerMinute;

    @Value("${app.security.rate-limit.ai-per-minute:5}")
    private int aiPerMinute;

    @Value("${app.security.rate-limit.tickets-per-hour:3}")
    private int ticketsPerHour;

    @Value("${app.security.rate-limit.feedback-per-hour:10}")
    private int feedbackPerHour;

    @Value("${app.security.rate-limit.voice-per-minute:3}")
    private int voicePerMinute;

    @Value("${app.security.rate-limit.admin-per-minute:60}")
    private int adminPerMinute;

    @Value("${app.security.rate-limit.api-per-minute:120}")
    private int apiPerMinute;

    @Value("${app.security.rate-limit.auth-per-minute:10}")
    private int authPerMinute;

    public boolean isAllowed(String ipAddress, String path, String method) {
        Rule rule = ruleFor(path, method);
        if (rule == null) {
            return true;
        }
        long now = Instant.now().toEpochMilli();
        String key = ipAddress + "|" + rule.name + "|" + method;
        Bucket bucket = buckets.compute(key, (ignored, existing) -> {
            if (existing == null || now >= existing.resetAtMillis) {
                return new Bucket(1, now + rule.windowMillis);
            }
            existing.count++;
            return existing;
        });
        return bucket.count <= rule.maxRequests;
    }

    private Rule ruleFor(String path, String method) {
        if (path == null || !path.startsWith("/api/")) {
            return null;
        }
        if (path.startsWith("/api/security") || path.startsWith("/api/dashboard")
                || path.equals("/api/chat/history") || path.startsWith("/api/unanswered")) {
            return new Rule("admin", adminPerMinute, 60_000);
        }
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            return new Rule("auth", authPerMinute, 60_000);
        }
        if ("POST".equalsIgnoreCase(method)) {
            if ("/api/chat/ask".equals(path)) {
                return new Rule("chat", chatPerMinute, 60_000);
            }
            if ("/api/ai/ask".equals(path)) {
                return new Rule("ai", aiPerMinute, 60_000);
            }
            if ("/api/voice/ask".equals(path)) {
                return new Rule("voice", voicePerMinute, 60_000);
            }
            if ("/api/tickets".equals(path)) {
                return new Rule("tickets", ticketsPerHour, 3_600_000);
            }
            if ("/api/feedback".equals(path)) {
                return new Rule("feedback", feedbackPerHour, 3_600_000);
            }
        }
        return new Rule("api", apiPerMinute, 60_000);
    }

    private record Rule(String name, int maxRequests, long windowMillis) {
    }

    private static final class Bucket {
        private int count;
        private final long resetAtMillis;

        private Bucket(int count, long resetAtMillis) {
            this.count = count;
            this.resetAtMillis = resetAtMillis;
        }
    }
}
