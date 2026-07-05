package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.entity.AuditLog;
import com.supportfaq.customersupportfaqaiagent.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Value("${app.security.trust-proxy-headers:false}")
    private boolean trustProxyHeaders;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLog log(String eventType, String severity, String details, HttpServletRequest request) {
        return log(eventType, severity, details, request, null, null);
    }

    public AuditLog log(String eventType, String severity, String details, HttpServletRequest request,
                        String entityType, Long entityId) {
        AuditLog log = new AuditLog();
        log.setEventType(eventType);
        log.setSeverity(severity);
        log.setDetails(sanitize(details));
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setActor("anonymous");
        if (request != null) {
            log.setIpAddress(getClientIp(request));
            log.setUserAgent(sanitize(request.getHeader("User-Agent")));
            log.setRequestPath(request.getRequestURI());
            log.setHttpMethod(request.getMethod());
        }
        return auditLogRepository.save(log);
    }

    public List<AuditLog> getAll() {
        return auditLogRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<AuditLog> getBySeverity(String severity) {
        return auditLogRepository.findBySeverityIgnoreCaseOrderByCreatedAtDesc(severity);
    }

    public List<AuditLog> getByEventType(String eventType) {
        return auditLogRepository.findByEventTypeIgnoreCaseOrderByCreatedAtDesc(eventType);
    }

    public long count() {
        return auditLogRepository.count();
    }

    public long countSeverity(String severity) {
        return auditLogRepository.countBySeverityIgnoreCase(severity);
    }

    public long countEvent(String eventType) {
        return auditLogRepository.countByEventTypeIgnoreCase(eventType);
    }

    public String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        if (trustProxyHeaders) {
            String forwardedFor = request.getHeader("X-Forwarded-For");
            if (forwardedFor != null && !forwardedFor.isBlank()) {
                return sanitizeIp(forwardedFor.split(",")[0].trim());
            }
            String realIp = request.getHeader("X-Real-IP");
            if (realIp != null && !realIp.isBlank()) {
                return sanitizeIp(realIp.trim());
            }
        }
        return sanitizeIp(request.getRemoteAddr());
    }

    private String sanitize(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("(?i)(api[_-]?key|password|secret|token)\\s*[:=]\\s*\\S+", "$1=***");
    }

    private String sanitizeIp(String value) {
        if (value == null || value.isBlank()) {
            return "unknown";
        }
        return value.replaceAll("[^A-Fa-f0-9:.]", "");
    }
}
