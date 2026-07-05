package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.FaqRequest;
import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import com.supportfaq.customersupportfaqaiagent.entity.SupportTicket;
import com.supportfaq.customersupportfaqaiagent.entity.UnansweredQuestion;
import com.supportfaq.customersupportfaqaiagent.exception.ResourceNotFoundException;
import com.supportfaq.customersupportfaqaiagent.repository.UnansweredQuestionRepository;
import com.supportfaq.customersupportfaqaiagent.service.FaqService;
import com.supportfaq.customersupportfaqaiagent.service.SupportTicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/unanswered")
public class UnansweredQuestionController {

    private final UnansweredQuestionRepository unansweredQuestionRepository;
    private final FaqService faqService;
    private final SupportTicketService supportTicketService;

    public UnansweredQuestionController(UnansweredQuestionRepository unansweredQuestionRepository,
                                        FaqService faqService,
                                        SupportTicketService supportTicketService) {
        this.unansweredQuestionRepository = unansweredQuestionRepository;
        this.faqService = faqService;
        this.supportTicketService = supportTicketService;
    }

    @GetMapping
    public List<UnansweredQuestion> getAll() {
        return unansweredQuestionRepository.findAllByOrderByCreatedAtDesc();
    }

    @PutMapping("/{id}/review")
    public UnansweredQuestion markReviewed(@PathVariable Long id) {
        UnansweredQuestion unansweredQuestion = getUnanswered(id);
        unansweredQuestion.setStatus("REVIEWED");
        unansweredQuestion.setReviewedAt(LocalDateTime.now());
        return unansweredQuestionRepository.save(unansweredQuestion);
    }

    @PutMapping("/{id}/resolve")
    public UnansweredQuestion resolve(@PathVariable Long id) {
        UnansweredQuestion unansweredQuestion = getUnanswered(id);
        unansweredQuestion.setStatus("RESOLVED");
        unansweredQuestion.setResolvedAt(LocalDateTime.now());
        return unansweredQuestionRepository.save(unansweredQuestion);
    }

    @PostMapping("/{id}/create-faq")
    public Faq createFaqFromUnanswered(@PathVariable Long id, @RequestBody FaqRequest request) {
        UnansweredQuestion unansweredQuestion = getUnanswered(id);
        if (request.getQuestion() == null || request.getQuestion().isBlank()) {
            request.setQuestion(unansweredQuestion.getQuestion());
        }
        if (request.getLanguage() == null || request.getLanguage().isBlank()) {
            request.setLanguage(unansweredQuestion.getLanguage());
        }
        Faq faq = faqService.addFaq(request);
        unansweredQuestion.setConvertedToFaq(true);
        unansweredQuestion.setStatus("CONVERTED_TO_FAQ");
        unansweredQuestion.setResolvedAt(LocalDateTime.now());
        unansweredQuestionRepository.save(unansweredQuestion);
        return faq;
    }

    @PostMapping("/{id}/ticket")
    public SupportTicket escalateToTicket(@PathVariable Long id) {
        UnansweredQuestion unansweredQuestion = getUnanswered(id);
        SupportTicket ticket = new SupportTicket();
        ticket.setSubject("Escalated unanswered question");
        ticket.setMessage(unansweredQuestion.getQuestion());
        ticket.setSourceQuestion(unansweredQuestion.getQuestion());
        ticket.setPriority("MEDIUM");
        SupportTicket savedTicket = supportTicketService.create(ticket);
        unansweredQuestion.setStatus("ESCALATED");
        unansweredQuestionRepository.save(unansweredQuestion);
        return savedTicket;
    }

    private UnansweredQuestion getUnanswered(Long id) {
        return unansweredQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unanswered question not found"));
    }
}
