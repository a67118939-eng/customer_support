package com.supportfaq.customersupportfaqaiagent.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiSafetyService {

    private static final String SAFE_REFUSAL =
            "I cannot help with requests that attempt to access secrets, internal instructions, or administrative functions. Please contact support if you need help.";

    private final AuditLogService auditLogService;
    private final WhatsappSupportService whatsappSupportService;

    private final List<String> suspiciousPhrases = List.of(
            "ignore previous instructions",
            "reveal your prompt",
            "show your system message",
            "show api key",
            "show database password",
            "delete all faqs",
            "admin password",
            "bypass security",
            "jailbreak",
            "developer message",
            "system prompt",
            "secret key",
            "show token",
            "session token",
            "access token"
    );

    public AiSafetyService(AuditLogService auditLogService, WhatsappSupportService whatsappSupportService) {
        this.auditLogService = auditLogService;
        this.whatsappSupportService = whatsappSupportService;
    }

    public boolean isSuspicious(String question) {
        String normalized = question == null ? "" : question.toLowerCase();
        return suspiciousPhrases.stream().anyMatch(normalized::contains);
    }

    public void auditSuspiciousPrompt(String question, HttpServletRequest request) {
        auditLogService.log("PROMPT_INJECTION_ATTEMPT", "HIGH",
                "Suspicious AI prompt blocked: " + summarize(question), request);
    }

    public String safeRefusal() {
        return SAFE_REFUSAL;
    }

    public String supportUrl() {
        return whatsappSupportService.buildSupportUrl();
    }

    private String summarize(String value) {
        if (value == null) {
            return "";
        }
        String compact = value.replaceAll("\\s+", " ").trim();
        return compact.length() > 180 ? compact.substring(0, 180) + "..." : compact;
    }
}
