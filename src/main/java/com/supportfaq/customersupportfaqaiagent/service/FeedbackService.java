package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.FeedbackRequest;
import com.supportfaq.customersupportfaqaiagent.dto.FeedbackSummaryResponse;
import com.supportfaq.customersupportfaqaiagent.entity.ChatHistory;
import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import com.supportfaq.customersupportfaqaiagent.entity.Feedback;
import com.supportfaq.customersupportfaqaiagent.repository.ChatHistoryRepository;
import com.supportfaq.customersupportfaqaiagent.repository.FaqRepository;
import com.supportfaq.customersupportfaqaiagent.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    private final FaqRepository faqRepository;
    private final AuditLogService auditLogService;
    private final InputSanitizer inputSanitizer;

    public FeedbackService(FeedbackRepository feedbackRepository, ChatHistoryRepository chatHistoryRepository,
                           FaqRepository faqRepository, AuditLogService auditLogService,
                           InputSanitizer inputSanitizer) {
        this.feedbackRepository = feedbackRepository;
        this.chatHistoryRepository = chatHistoryRepository;
        this.faqRepository = faqRepository;
        this.auditLogService = auditLogService;
        this.inputSanitizer = inputSanitizer;
    }

    public Feedback create(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setChatHistoryId(request.getChatHistoryId());
        feedback.setHelpful(request.isHelpful());
        feedback.setComment(inputSanitizer.text(request.getComment(), 1000));
        Feedback saved = feedbackRepository.save(feedback);
        updateFaqFeedbackCounters(request);
        auditLogService.log("FEEDBACK_CREATED", "LOW", "Feedback created", null, "Feedback", saved.getId());
        return saved;
    }

    public List<Feedback> getAll() {
        return feedbackRepository.findAllByOrderByCreatedAtDesc();
    }

    public FeedbackSummaryResponse summary() {
        return new FeedbackSummaryResponse(
                feedbackRepository.count(),
                feedbackRepository.countByHelpfulTrue(),
                feedbackRepository.countByHelpfulFalse()
        );
    }

    private void updateFaqFeedbackCounters(FeedbackRequest request) {
        if (request.getChatHistoryId() == null) {
            return;
        }
        chatHistoryRepository.findById(request.getChatHistoryId())
                .map(ChatHistory::getMatchedFaqId)
                .flatMap(faqRepository::findById)
                .ifPresent(faq -> incrementFaqCounter(faq, request.isHelpful()));
    }

    private void incrementFaqCounter(Faq faq, boolean helpful) {
        if (helpful) {
            faq.setHelpfulCount(faq.getHelpfulCount() + 1);
        } else {
            faq.setUnhelpfulCount(faq.getUnhelpfulCount() + 1);
        }
        faqRepository.save(faq);
    }
}
