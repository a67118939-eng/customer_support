package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.FeedbackRequest;
import com.supportfaq.customersupportfaqaiagent.dto.FeedbackSummaryResponse;
import com.supportfaq.customersupportfaqaiagent.entity.Feedback;
import com.supportfaq.customersupportfaqaiagent.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public Feedback create(@Valid @RequestBody FeedbackRequest request) {
        return feedbackService.create(request);
    }

    @GetMapping
    public List<Feedback> getAll() {
        return feedbackService.getAll();
    }

    @GetMapping("/summary")
    public FeedbackSummaryResponse summary() {
        return feedbackService.summary();
    }
}
