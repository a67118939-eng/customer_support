package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.exception.ApiErrorResponse;
import com.supportfaq.customersupportfaqaiagent.service.HoneypotService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HoneypotController {

    private final HoneypotService honeypotService;

    public HoneypotController(HoneypotService honeypotService) {
        this.honeypotService = honeypotService;
    }

    @GetMapping({
            "/private/admin-backup.zip",
            "/internal/database-dump.sql",
            "/config/.env",
            "/admin/secrets.txt",
            "/backup/customer-data.xlsx",
            "/system/api-keys.json"
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse decoy(HttpServletRequest request) {
        honeypotService.trigger(request, "Decoy resource accessed");
        return new ApiErrorResponse("Resource not found", HttpStatus.NOT_FOUND.value());
    }
}