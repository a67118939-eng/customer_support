package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.SupportTicketRequest;
import com.supportfaq.customersupportfaqaiagent.entity.SupportTicket;
import com.supportfaq.customersupportfaqaiagent.exception.ResourceNotFoundException;
import com.supportfaq.customersupportfaqaiagent.repository.SupportTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final AuditLogService auditLogService;
    private final InputSanitizer inputSanitizer;

    private static final Set<String> ALLOWED_STATUSES = Set.of("OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED");
    private static final Set<String> ALLOWED_PRIORITIES = Set.of("LOW", "MEDIUM", "HIGH", "CRITICAL");

    public SupportTicketService(SupportTicketRepository supportTicketRepository, AuditLogService auditLogService,
                                InputSanitizer inputSanitizer) {
        this.supportTicketRepository = supportTicketRepository;
        this.auditLogService = auditLogService;
        this.inputSanitizer = inputSanitizer;
    }

    public SupportTicket create(SupportTicket ticket) {
        ticket.setCustomerName(inputSanitizer.text(ticket.getCustomerName(), 120));
        ticket.setCustomerEmail(inputSanitizer.plainText(ticket.getCustomerEmail(), 254));
        ticket.setSubject(defaultValue(inputSanitizer.text(ticket.getSubject(), 180), "Customer support request"));
        ticket.setMessage(inputSanitizer.requiredText(ticket.getMessage(), 3000, "Message"));
        ticket.setPriority(inputSanitizer.enumValue(ticket.getPriority(), "MEDIUM", ALLOWED_PRIORITIES, "priority"));
        ticket.setSourceQuestion(inputSanitizer.text(ticket.getSourceQuestion(), 1000));
        ticket.setAdminReply(inputSanitizer.text(ticket.getAdminReply(), 3000));
        SupportTicket saved = supportTicketRepository.save(ticket);
        auditLogService.log("TICKET_CREATED", "LOW", "Ticket created", null, "SupportTicket", saved.getId());
        return saved;
    }

    public SupportTicket create(SupportTicketRequest request) {
        SupportTicket ticket = new SupportTicket();
        ticket.setCustomerName(inputSanitizer.text(request.getCustomerName(), 120));
        ticket.setCustomerEmail(inputSanitizer.plainText(request.getCustomerEmail(), 254));
        ticket.setSubject(defaultValue(inputSanitizer.text(request.getSubject(), 180), "Customer support request"));
        ticket.setMessage(inputSanitizer.requiredText(request.getMessage(), 3000, "Message"));
        ticket.setPriority(inputSanitizer.enumValue(request.getPriority(), "MEDIUM", ALLOWED_PRIORITIES, "priority"));
        ticket.setSourceQuestion(inputSanitizer.text(request.getSourceQuestion(), 1000));
        return create(ticket);
    }

    public List<SupportTicket> getAll() {
        return supportTicketRepository.findAllByOrderByCreatedAtDesc();
    }

    public SupportTicket getById(Long id) {
        return supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Support ticket not found"));
    }

    public SupportTicket updateStatus(Long id, String status) {
        SupportTicket ticket = getById(id);
        ticket.setStatus(inputSanitizer.enumValue(status, "OPEN", ALLOWED_STATUSES, "status"));
        SupportTicket saved = supportTicketRepository.save(ticket);
        auditLogService.log("TICKET_UPDATED", "LOW", "Ticket status updated", null, "SupportTicket", saved.getId());
        return saved;
    }

    public SupportTicket addReply(Long id, String reply) {
        SupportTicket ticket = getById(id);
        ticket.setAdminReply(inputSanitizer.text(reply, 3000));
        SupportTicket saved = supportTicketRepository.save(ticket);
        auditLogService.log("TICKET_UPDATED", "LOW", "Ticket reply added", null, "SupportTicket", saved.getId());
        return saved;
    }

    public void delete(Long id) {
        if (!supportTicketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Support ticket not found");
        }
        supportTicketRepository.deleteById(id);
    }

    private String defaultValue(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }
}
