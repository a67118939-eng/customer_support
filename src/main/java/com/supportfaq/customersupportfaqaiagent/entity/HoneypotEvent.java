package com.supportfaq.customersupportfaqaiagent.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "honeypot_events")
public class HoneypotEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    @Column(length = 1000)
    private String userAgent;

    private String requestPath;

    private String httpMethod;

    @Column(length = 1000)
    private String queryString;

    @Column(length = 1000)
    private String reason;

    private Boolean blocked = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getReason() {
        return reason;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
