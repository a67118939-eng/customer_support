package com.supportfaq.customersupportfaqaiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ManualBlockIpRequest {

    @NotBlank(message = "IP address is required")
    @Size(max = 80, message = "IP address must be at most 80 characters")
    private String ipAddress;

    @Size(max = 1000, message = "Reason must be at most 1000 characters")
    private String reason;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
