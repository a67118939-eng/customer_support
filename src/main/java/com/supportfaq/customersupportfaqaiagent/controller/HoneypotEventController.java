package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.entity.HoneypotEvent;
import com.supportfaq.customersupportfaqaiagent.service.HoneypotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security/honeypot-events")
public class HoneypotEventController {

    private final HoneypotService honeypotService;

    public HoneypotEventController(HoneypotService honeypotService) {
        this.honeypotService = honeypotService;
    }

    @GetMapping
    public List<HoneypotEvent> getAll() {
        return honeypotService.getAll();
    }
}
