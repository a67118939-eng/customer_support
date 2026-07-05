package com.supportfaq.customersupportfaqaiagent.dto;

public class SecurityStatsResponse {

    private long totalAuditLogs;
    private long criticalEvents;
    private long highEvents;
    private long honeypotTriggers;
    private long blockedIps;
    private long activeBlockedIps;
    private long rateLimitEvents;
    private long promptInjectionAttempts;

    public SecurityStatsResponse(long totalAuditLogs, long criticalEvents, long highEvents,
                                 long honeypotTriggers, long blockedIps, long activeBlockedIps,
                                 long rateLimitEvents, long promptInjectionAttempts) {
        this.totalAuditLogs = totalAuditLogs;
        this.criticalEvents = criticalEvents;
        this.highEvents = highEvents;
        this.honeypotTriggers = honeypotTriggers;
        this.blockedIps = blockedIps;
        this.activeBlockedIps = activeBlockedIps;
        this.rateLimitEvents = rateLimitEvents;
        this.promptInjectionAttempts = promptInjectionAttempts;
    }

    public long getTotalAuditLogs() {
        return totalAuditLogs;
    }

    public long getCriticalEvents() {
        return criticalEvents;
    }

    public long getHighEvents() {
        return highEvents;
    }

    public long getHoneypotTriggers() {
        return honeypotTriggers;
    }

    public long getBlockedIps() {
        return blockedIps;
    }

    public long getActiveBlockedIps() {
        return activeBlockedIps;
    }

    public long getRateLimitEvents() {
        return rateLimitEvents;
    }

    public long getPromptInjectionAttempts() {
        return promptInjectionAttempts;
    }
}
