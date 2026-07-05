package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.entity.HoneypotEvent;
import com.supportfaq.customersupportfaqaiagent.repository.HoneypotEventRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoneypotService {

    private final HoneypotEventRepository honeypotEventRepository;
    private final AuditLogService auditLogService;
    private final BlockedIpService blockedIpService;

    @Value("${app.security.honeypot.auto-block-enabled:false}")
    private boolean autoBlockEnabled;

    public HoneypotService(
            HoneypotEventRepository honeypotEventRepository,
            AuditLogService auditLogService,
            BlockedIpService blockedIpService
    ) {
        this.honeypotEventRepository = honeypotEventRepository;
        this.auditLogService = auditLogService;
        this.blockedIpService = blockedIpService;
    }

    public HoneypotEvent trigger(HttpServletRequest request, String reason) {
        String ipAddress = auditLogService.getClientIp(request);

        HoneypotEvent event = new HoneypotEvent();
        event.setIpAddress(ipAddress);
        event.setUserAgent(sanitize(request.getHeader("User-Agent")));
        event.setRequestPath(request.getRequestURI());
        event.setHttpMethod(request.getMethod());
        event.setQueryString(sanitize(request.getQueryString()));
        event.setReason(reason);
        event.setBlocked(autoBlockEnabled);

        HoneypotEvent saved = honeypotEventRepository.save(event);

        auditLogService.log(
                "HONEYPOT_TRIGGERED",
                "CRITICAL",
                "Honeypot triggered from IP: " + ipAddress + " Path: " + request.getRequestURI(),
                request,
                "HoneypotEvent",
                saved.getId()
        );

        if (autoBlockEnabled) {
            blockedIpService.blockIp(
                    ipAddress,
                    "Honeypot decoy resource accessed",
                    request
            );
        }

        return saved;
    }

    public List<HoneypotEvent> getAll() {
        return honeypotEventRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<HoneypotEvent> getEvents() {
        return getAll();
    }

    public long count() {
        return honeypotEventRepository.count();
    }

    private String sanitize(String value) {
        if (value == null) {
            return null;
        }

        return value.replaceAll(
                "(?i)(api[_-]?key|password|secret|token)\\s*[:=]\\s*\\S+",
                "$1=***"
        );
    }
}
