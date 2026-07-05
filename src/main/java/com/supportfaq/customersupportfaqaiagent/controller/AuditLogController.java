package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.entity.AuditLog;
import com.supportfaq.customersupportfaqaiagent.service.AuditLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLog> getAll() {
        return auditLogService.getAll();
    }

    @GetMapping("/severity/{severity}")
    public List<AuditLog> getBySeverity(@PathVariable String severity) {
        return auditLogService.getBySeverity(severity);
    }

    @GetMapping("/event/{eventType}")
    public List<AuditLog> getByEventType(@PathVariable String eventType) {
        return auditLogService.getByEventType(eventType);
    }
}
