package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.entity.BlockedIp;
import com.supportfaq.customersupportfaqaiagent.service.BlockedIpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security/blocked-ips")
public class BlockedIpController {

    private final BlockedIpService blockedIpService;

    public BlockedIpController(BlockedIpService blockedIpService) {
        this.blockedIpService = blockedIpService;
    }

    @GetMapping
    public List<BlockedIp> getBlockedIps() {
        return blockedIpService.getAll();
    }

    @PostMapping
    public BlockedIp blockIp(@Valid @RequestBody BlockIpRequest requestBody,
                             HttpServletRequest request) {
        return blockedIpService.manualBlock(
                requestBody.getIpAddress(),
                requestBody.getReason(),
                request
        );
    }

    @PutMapping("/{id}/unblock")
    public BlockedIp unblock(@PathVariable Long id,
                             HttpServletRequest request) {
        return blockedIpService.unblock(id, request);
    }

    public static class BlockIpRequest {

        @NotBlank(message = "IP address is required")
        @Size(max = 80, message = "IP address must be at most 80 characters")
        private String ipAddress;

        @Size(max = 1000, message = "Reason must be at most 1000 characters")
        private String reason = "Manual block from admin";

        public String getIpAddress() {
            return ipAddress;
        }

        public String getReason() {
            return reason;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
