package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import com.supportfaq.customersupportfaqaiagent.entity.UnansweredQuestion;
import com.supportfaq.customersupportfaqaiagent.repository.FaqRepository;
import com.supportfaq.customersupportfaqaiagent.repository.UnansweredQuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AiKnowledgeLearningService {

    private final FaqRepository faqRepository;
    private final UnansweredQuestionRepository unansweredQuestionRepository;
    private final FaqService faqService;
    private final AuditLogService auditLogService;
    private final InputSanitizer inputSanitizer;

    @Value("${app.ai.auto-save-generated-faq:true}")
    private boolean autoSaveGeneratedFaq;

    @Value("${app.ai.auto-approve-generated-faq:false}")
    private boolean autoApproveGeneratedFaq;

    @Value("${app.ai.generated-faq-default-category:AI Generated}")
    private String generatedFaqDefaultCategory;

    @Value("${app.ai.minimum-confidence-to-auto-save:0.50}")
    private double minimumConfidenceToAutoSave;

    public AiKnowledgeLearningService(FaqRepository faqRepository,
                                      UnansweredQuestionRepository unansweredQuestionRepository,
                                      FaqService faqService,
                                      AuditLogService auditLogService,
                                      InputSanitizer inputSanitizer) {
        this.faqRepository = faqRepository;
        this.unansweredQuestionRepository = unansweredQuestionRepository;
        this.faqService = faqService;
        this.auditLogService = auditLogService;
        this.inputSanitizer = inputSanitizer;
    }

    public Faq learnFromAnswer(String question, String aiAnswer, String language,
                               double confidenceScore, String categoryGuess) {
        String safeQuestion = inputSanitizer.plainText(question, 1000);
        String safeAnswer = inputSanitizer.text(aiAnswer, 5000);
        String safeCategory = inputSanitizer.text(categoryGuess, 100);
        saveUnansweredQuestion(safeQuestion, language, safeCategory);

        if (!autoSaveGeneratedFaq || confidenceScore < minimumConfidenceToAutoSave
                || safeQuestion == null || safeQuestion.isBlank()
                || safeAnswer == null || safeAnswer.isBlank()
                || faqRepository.existsByQuestionIgnoreCase(safeQuestion.trim())) {
            return null;
        }

        Faq faq = new Faq();
        faq.setQuestion(inputSanitizer.text(safeQuestion, 1000));
        faq.setAnswer(safeAnswer);
        faq.setCategory(defaultValue(safeCategory, generatedFaqDefaultCategory));
        faq.setLanguage(defaultValue(language, detectLanguage(safeQuestion)).toUpperCase());
        faq.setStatus(autoApproveGeneratedFaq ? "ACTIVE" : "DRAFT");
        faq.setPriority("NORMAL");
        faq.setKeywords(inputSanitizer.text(faqService.buildKeywords(safeQuestion), 1000));
        faq.setSource("AI_GENERATED");
        faq.setGeneratedBy("REAL_AI");
        faq.setGeneratedFromQuestion(inputSanitizer.text(safeQuestion, 1000));
        faq.setReviewed(autoApproveGeneratedFaq);
        if (autoApproveGeneratedFaq) {
            faq.setReviewedAt(LocalDateTime.now());
            faq.setReviewedBy("system");
        }
        Faq saved = faqRepository.save(faq);
        auditLogService.log("AI_GENERATED_FAQ_CREATED", "LOW",
                "AI-generated FAQ created as " + saved.getStatus(), null, "Faq", saved.getId());
        return saved;
    }

    private void saveUnansweredQuestion(String question, String language, String categoryGuess) {
        if (question == null || question.isBlank()) {
            return;
        }
        unansweredQuestionRepository.save(new UnansweredQuestion(
                question.trim(),
                defaultValue(language, detectLanguage(question)).toUpperCase(),
                categoryGuess
        ));
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
