package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.AskQuestionResponse;
import com.supportfaq.customersupportfaqaiagent.dto.MatchingResult;
import com.supportfaq.customersupportfaqaiagent.entity.ChatHistory;
import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import com.supportfaq.customersupportfaqaiagent.entity.UnansweredQuestion;
import com.supportfaq.customersupportfaqaiagent.repository.ChatHistoryRepository;
import com.supportfaq.customersupportfaqaiagent.repository.UnansweredQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ChatService {

    public static final String DEFAULT_UNANSWERED_MESSAGE =
            "Sorry, I could not find an answer for your question. Please contact support.";

    public static final double MATCH_THRESHOLD = 0.40;

    private final FaqService faqService;
    private final AiMatchingService aiMatchingService;
    private final ChatHistoryRepository chatHistoryRepository;
    private final UnansweredQuestionRepository unansweredQuestionRepository;
    private final WhatsappSupportService whatsappSupportService;
    private final InputSanitizer inputSanitizer;

    public ChatService(FaqService faqService, AiMatchingService aiMatchingService,
                       ChatHistoryRepository chatHistoryRepository,
                       UnansweredQuestionRepository unansweredQuestionRepository,
                       WhatsappSupportService whatsappSupportService,
                       InputSanitizer inputSanitizer) {
        this.faqService = faqService;
        this.aiMatchingService = aiMatchingService;
        this.chatHistoryRepository = chatHistoryRepository;
        this.unansweredQuestionRepository = unansweredQuestionRepository;
        this.whatsappSupportService = whatsappSupportService;
        this.inputSanitizer = inputSanitizer;
    }

    public AskQuestionResponse askQuestion(String question) {
        return askQuestion(question, null, "TEXT", null, "FAQ_ONLY");
    }

    public AskQuestionResponse askQuestion(String question, String requestedLanguage, String inputType) {
        return askQuestion(question, requestedLanguage, inputType, null, "FAQ_ONLY");
    }

    public AskQuestionResponse askQuestion(String question, String requestedLanguage, String inputType,
                                           String sessionId, String mode) {
        String safeQuestion = inputSanitizer.requiredText(question, 1000, "Question");
        String safeSessionId = inputSanitizer.sessionId(sessionId);
        String language = defaultValue(requestedLanguage, detectLanguage(safeQuestion)).toUpperCase();
        String normalizedInputType = defaultValue(inputType, "TEXT").toUpperCase();
        String normalizedMode = defaultValue(mode, "FAQ_ONLY").toUpperCase();
        List<Faq> activeFaqs = faqService.getActiveFaqs();

        MatchingResult result = aiMatchingService.findBestMatch(safeQuestion, activeFaqs, language, null);
        if (!result.hasMatch(MATCH_THRESHOLD)) {
            return saveUnanswered(safeQuestion, language, normalizedInputType, safeSessionId, normalizedMode,
                    result.getConfidenceScore(), result.getCategoryGuess());
        }

        Faq matchedFaq = faqService.incrementMatchCount(result.getFaq());
        List<String> suggestions = getSuggestedQuestions(matchedFaq, language, activeFaqs);

        ChatHistory history = new ChatHistory(
                safeSessionId,
                safeQuestion,
                matchedFaq.getAnswer(),
                matchedFaq.getId(),
                result.getConfidenceScore(),
                true,
                language,
                normalizedInputType,
                normalizedMode,
                null
        );
        chatHistoryRepository.save(history);

        return new AskQuestionResponse(
                matchedFaq.getAnswer(),
                matchedFaq.getId(),
                result.getConfidenceScore(),
                true,
                language,
                normalizedMode,
                null,
                suggestions,
                safeSessionId
        );
    }

    public AskQuestionResponse saveRealAiAnswer(String question, String answer, String language, String sessionId,
                                                MatchingResult result, boolean answered) {
        return saveRealAiAnswer(question, answer, language, sessionId, result, answered, "TEXT");
    }

    public AskQuestionResponse saveRealAiAnswer(String question, String answer, String language, String sessionId,
                                                MatchingResult result, boolean answered, String inputType) {
        String safeQuestion = inputSanitizer.requiredText(question, 1000, "Question");
        String safeAnswer = inputSanitizer.requiredText(answer, 5000, "Answer");
        String safeSessionId = inputSanitizer.sessionId(sessionId);
        String normalizedInputType = defaultValue(inputType, "TEXT").toUpperCase();
        String whatsappUrl = answered ? null : whatsappSupportService.buildSupportUrl();
        String finalAnswer = answered ? safeAnswer : defaultValue(safeAnswer, DEFAULT_UNANSWERED_MESSAGE);
        Long matchedFaqId = result.getFaq() == null ? null : result.getFaq().getId();
        double confidenceScore = answered ? Math.max(result.getConfidenceScore(), 0.85) : result.getConfidenceScore();
        List<Faq> activeFaqs = faqService.getActiveFaqs();
        List<String> suggestions = result.getFaq() == null ? List.of() : getSuggestedQuestions(result.getFaq(), language, activeFaqs);

        ChatHistory history = new ChatHistory(
                safeSessionId,
                safeQuestion,
                finalAnswer,
                matchedFaqId,
                confidenceScore,
                answered,
                language,
                normalizedInputType,
                "REAL_AI",
                whatsappUrl
        );
        chatHistoryRepository.save(history);

        if (!answered) {
            unansweredQuestionRepository.save(new UnansweredQuestion(
                    safeQuestion,
                    language,
                    inputSanitizer.text(result.getCategoryGuess(), 100)
            ));
        }

        return new AskQuestionResponse(finalAnswer, matchedFaqId, confidenceScore, answered,
                language, "REAL_AI", whatsappUrl, suggestions, safeSessionId);
    }

    public List<ChatHistory> getRecentHistory() {
        return chatHistoryRepository.findTop50ByOrderByCreatedAtDesc();
    }

    public void deleteHistory() {
        chatHistoryRepository.deleteAll();
    }

    private AskQuestionResponse saveUnanswered(String question, String language, String inputType,
                                               String sessionId, String mode, double confidenceScore,
                                               String categoryGuess) {
        String whatsappUrl = whatsappSupportService.buildSupportUrl();
        ChatHistory history = new ChatHistory(
                sessionId,
                question,
                DEFAULT_UNANSWERED_MESSAGE,
                null,
                confidenceScore,
                false,
                language,
                inputType,
                mode,
                whatsappUrl
        );
        chatHistoryRepository.save(history);
        unansweredQuestionRepository.save(new UnansweredQuestion(
                question,
                language,
                inputSanitizer.text(categoryGuess, 100)
        ));

        return new AskQuestionResponse(DEFAULT_UNANSWERED_MESSAGE, null, confidenceScore, false,
                language, mode, whatsappUrl, List.of(), sessionId);
    }

    private List<String> getSuggestedQuestions(Faq matchedFaq, String language, List<Faq> activeFaqs) {
        return activeFaqs.stream()
                .filter(faq -> faq.getId() != null && !faq.getId().equals(matchedFaq.getId()))
                .filter(faq -> matchedFaq.getCategory() != null && faq.getCategory() != null
                        && faq.getCategory().equalsIgnoreCase(matchedFaq.getCategory()))
                .filter(faq -> language == null || faq.getLanguage() == null || faq.getLanguage().equalsIgnoreCase(language))
                .sorted(Comparator.comparingInt(this::priorityRank).reversed())
                .map(Faq::getQuestion)
                .limit(3)
                .toList();
    }

    private int priorityRank(Faq faq) {
        if ("CRITICAL".equalsIgnoreCase(faq.getPriority())) {
            return 4;
        }
        if ("HIGH".equalsIgnoreCase(faq.getPriority())) {
            return 3;
        }
        if ("NORMAL".equalsIgnoreCase(faq.getPriority())) {
            return 2;
        }
        return 1;
    }

    private String defaultValue(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }

    private String detectLanguage(String text) {
        if (text != null && text.matches(".*[\\u0600-\\u06FF].*")) {
            return "AR";
        }
        return "EN";
    }
}
