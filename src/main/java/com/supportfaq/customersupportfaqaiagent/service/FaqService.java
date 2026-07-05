package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.FaqRequest;
import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import com.supportfaq.customersupportfaqaiagent.exception.ResourceNotFoundException;
import com.supportfaq.customersupportfaqaiagent.repository.FaqRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class FaqService {

    private final FaqRepository faqRepository;
    private final AuditLogService auditLogService;
    private final InputSanitizer inputSanitizer;

    private static final Set<String> ALLOWED_STATUSES = Set.of("ACTIVE", "INACTIVE", "DRAFT", "ARCHIVED");
    private static final Set<String> ALLOWED_LANGUAGES = Set.of("EN", "AR");
    private static final Set<String> ALLOWED_PRIORITIES = Set.of("LOW", "NORMAL", "HIGH", "CRITICAL");

    public FaqService(FaqRepository faqRepository, AuditLogService auditLogService,
                      InputSanitizer inputSanitizer) {
        this.faqRepository = faqRepository;
        this.auditLogService = auditLogService;
        this.inputSanitizer = inputSanitizer;
    }

    public Faq addFaq(FaqRequest request) {
        Faq faq = new Faq();
        applyRequest(faq, request);
        Faq saved = faqRepository.save(faq);
        auditLogService.log("FAQ_CREATED", "LOW", "FAQ created", null, "Faq", saved.getId());
        return saved;
    }

    public List<Faq> getAllFaqs() {
        return faqRepository.findAllByOrderByUpdatedAtDesc();
    }

    public Faq getFaqById(Long id) {
        return faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ not found"));
    }

    public List<Faq> getActiveFaqs() {
        return faqRepository.findByStatusIgnoreCase("ACTIVE");
    }

    public List<Faq> getGeneratedFaqs() {
        return faqRepository.findBySourceIgnoreCaseOrderByUpdatedAtDesc("AI_GENERATED");
    }

    public List<Faq> getPendingReviewFaqs() {
        return faqRepository.findBySourceIgnoreCaseAndReviewedFalseOrderByUpdatedAtDesc("AI_GENERATED");
    }

    public List<Faq> getFaqsByCategory(String category) {
        return faqRepository.findByCategoryIgnoreCase(category);
    }

    public List<Faq> getFaqsByStatus(String status) {
        return faqRepository.findByStatusIgnoreCase(status);
    }

    public List<Faq> getFaqsByLanguage(String language) {
        return faqRepository.findByLanguageIgnoreCase(language);
    }

    public List<Faq> searchFaqs(String keyword) {
        String safeKeyword = inputSanitizer.plainText(keyword, 200);
        if (safeKeyword == null) {
            safeKeyword = "";
        }
        return faqRepository.findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCaseOrKeywordsContainingIgnoreCase(
                safeKeyword, safeKeyword, safeKeyword);
    }

    public Faq updateFaq(Long id, FaqRequest request) {
        Faq faq = getFaqById(id);
        applyRequest(faq, request);
        Faq saved = faqRepository.save(faq);
        auditLogService.log("FAQ_UPDATED", "LOW", "FAQ updated", null, "Faq", saved.getId());
        return saved;
    }

    public void deleteFaq(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new ResourceNotFoundException("FAQ not found");
        }
        faqRepository.deleteById(id);
        auditLogService.log("FAQ_DELETED", "MEDIUM", "FAQ deleted", null, "Faq", id);
    }

    public Faq incrementMatchCount(Faq faq) {
        faq.setMatchCount(faq.getMatchCount() + 1);
        return faqRepository.save(faq);
    }

    public Faq approveFaq(Long id) {
        Faq faq = getFaqById(id);
        faq.setStatus("ACTIVE");
        faq.setReviewed(true);
        faq.setReviewedAt(LocalDateTime.now());
        faq.setReviewedBy("admin");
        Faq saved = faqRepository.save(faq);
        auditLogService.log("FAQ_APPROVED", "LOW", "FAQ approved", null, "Faq", saved.getId());
        return saved;
    }

    public Faq rejectFaq(Long id) {
        Faq faq = getFaqById(id);
        faq.setStatus("ARCHIVED");
        faq.setReviewed(true);
        faq.setReviewedAt(LocalDateTime.now());
        faq.setReviewedBy("admin");
        Faq saved = faqRepository.save(faq);
        auditLogService.log("FAQ_REJECTED", "MEDIUM", "FAQ rejected", null, "Faq", saved.getId());
        return saved;
    }

    private void applyRequest(Faq faq, FaqRequest request) {
        String question = inputSanitizer.requiredText(request.getQuestion(), 500, "Question");
        faq.setQuestion(question);
        faq.setAnswer(inputSanitizer.requiredText(request.getAnswer(), 5000, "Answer"));
        faq.setCategory(inputSanitizer.text(request.getCategory(), 100));
        faq.setStatus(inputSanitizer.enumValue(request.getStatus(), "ACTIVE", ALLOWED_STATUSES, "status"));
        faq.setLanguage(inputSanitizer.enumValue(request.getLanguage(), detectLanguage(question), ALLOWED_LANGUAGES, "language"));
        faq.setKeywords(inputSanitizer.text(request.getKeywords(), 1000));
        faq.setPriority(inputSanitizer.enumValue(request.getPriority(), "NORMAL", ALLOWED_PRIORITIES, "priority"));
    }

    private String detectLanguage(String text) {
        if (text != null && text.matches(".*[\\u0600-\\u06FF].*")) {
            return "AR";
        }
        return "EN";
    }

    String buildKeywords(String question) {
        return question.toLowerCase()
                .replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", " ")
                .replaceAll("\\s+", ", ")
                .trim();
    }
}
