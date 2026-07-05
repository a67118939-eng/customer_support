package com.supportfaq.customersupportfaqaiagent.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_ips")
public class BlockedIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    @Column(length = 1000)
    private String reason;

    private Boolean active = true;

    private LocalDateTime blockedAt = LocalDateTime.now();

    private LocalDateTime expiresAt;

    private Boolean permanent = false;

    private String blockedBy = "system";

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getReason() {
        return reason;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Boolean getPermanent() {
        return permanent;
    }

    public String getBlockedBy() {
        return blockedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setBlockedAt(LocalDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setPermanent(Boolean permanent) {
        this.permanent = permanent;
    }

    public void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }
}