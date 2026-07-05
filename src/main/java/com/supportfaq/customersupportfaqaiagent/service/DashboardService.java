package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.DashboardStatsResponse;
import com.supportfaq.customersupportfaqaiagent.repository.ChatHistoryRepository;
import com.supportfaq.customersupportfaqaiagent.repository.FaqRepository;
import com.supportfaq.customersupportfaqaiagent.repository.FeedbackRepository;
import com.supportfaq.customersupportfaqaiagent.repository.SupportTicketRepository;
import com.supportfaq.customersupportfaqaiagent.repository.UnansweredQuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final FaqRepository faqRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    private final UnansweredQuestionRepository unansweredQuestionRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final FeedbackRepository feedbackRepository;

    public DashboardService(FaqRepository faqRepository, ChatHistoryRepository chatHistoryRepository,
                            UnansweredQuestionRepository unansweredQuestionRepository,
                            SupportTicketRepository supportTicketRepository,
                            FeedbackRepository feedbackRepository) {
        this.faqRepository = faqRepository;
        this.chatHistoryRepository = chatHistoryRepository;
        this.unansweredQuestionRepository = unansweredQuestionRepository;
        this.supportTicketRepository = supportTicketRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public DashboardStatsResponse getStats() {
        return new DashboardStatsResponse(
                faqRepository.count(),
                faqRepository.countByStatusIgnoreCase("ACTIVE"),
                faqRepository.countByStatusIgnoreCase("INACTIVE"),
                faqRepository.countByStatusIgnoreCase("DRAFT"),
                faqRepository.countByStatusIgnoreCase("ARCHIVED"),
                chatHistoryRepository.count(),
                chatHistoryRepository.countByAnsweredTrue(),
                unansweredQuestionRepository.count(),
                chatHistoryRepository.averageConfidenceScore(),
                supportTicketRepository.count(),
                supportTicketRepository.countByStatusIgnoreCase("OPEN"),
                supportTicketRepository.countByStatusIgnoreCase("RESOLVED"),
                feedbackRepository.count(),
                feedbackRepository.countByHelpfulTrue(),
                feedbackRepository.countByHelpfulFalse(),
                chatHistoryRepository.countByLanguageIgnoreCase("EN"),
                chatHistoryRepository.countByLanguageIgnoreCase("AR")
        );
    }
}
