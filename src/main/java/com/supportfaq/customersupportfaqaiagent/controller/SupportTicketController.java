package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.ApiMessageResponse;
import com.supportfaq.customersupportfaqaiagent.dto.SupportTicketRequest;
import com.supportfaq.customersupportfaqaiagent.entity.SupportTicket;
import com.supportfaq.customersupportfaqaiagent.service.SupportTicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @PostMapping
    public SupportTicket create(@Valid @RequestBody SupportTicketRequest request) {
        return supportTicketService.create(request);
    }

    @GetMapping
    public List<SupportTicket> getAll() {
        return supportTicketService.getAll();
    }

    @GetMapping("/{id}")
    public SupportTicket getById(@PathVariable Long id) {
        return supportTicketService.getById(id);
    }

    @PutMapping("/{id}/status")
    public SupportTicket updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return supportTicketService.updateStatus(id, request.getOrDefault("status", "OPEN"));
    }

    @PutMapping("/{id}/reply")
    public SupportTicket reply(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return supportTicketService.addReply(id, request.getOrDefault("adminReply", ""));
    }

    @DeleteMapping("/{id}")
    public ApiMessageResponse delete(@PathVariable Long id) {
        supportTicketService.delete(id);
        return new ApiMessageResponse("Ticket deleted successfully");
    }
}
