package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.SecurityStatsResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuditLogService;
import com.supportfaq.customersupportfaqaiagent.service.BlockedIpService;
import com.supportfaq.customersupportfaqaiagent.service.HoneypotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final AuditLogService auditLogService;
    private final HoneypotService honeypotService;
    private final BlockedIpService blockedIpService;

    public SecurityController(AuditLogService auditLogService, HoneypotService honeypotService,
                              BlockedIpService blockedIpService) {
        this.auditLogService = auditLogService;
        this.honeypotService = honeypotService;
        this.blockedIpService = blockedIpService;
    }

    // TODO: Protect Security Center APIs with ADMIN role when authentication is added.
    @GetMapping("/stats")
    public SecurityStatsResponse stats() {
        return new SecurityStatsResponse(
                auditLogService.count(),
                auditLogService.countSeverity("CRITICAL"),
                auditLogService.countSeverity("HIGH"),
                auditLogService.countEvent("HONEYPOT_TRIGGERED"),
                blockedIpService.count(),
                blockedIpService.countActive(),
                auditLogService.countEvent("RATE_LIMIT_EXCEEDED"),
                auditLogService.countEvent("PROMPT_INJECTION_ATTEMPT")
        );
    }

}
