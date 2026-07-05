package com.supportfaq.customersupportfaqaiagent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String userQuestion;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String aiResponse;

    private Long matchedFaqId;
    private double confidenceScore;
    private boolean answered;
    private String language = "EN";
    private String inputType = "TEXT";
    private String aiMode = "FAQ_ONLY";

    @Column(columnDefinition = "TEXT")
    private String whatsappSupportUrl;

    private LocalDateTime createdAt;

    public ChatHistory() {
    }

    public ChatHistory(String sessionId, String userQuestion, String aiResponse, Long matchedFaqId,
                       double confidenceScore, boolean answered, String language, String inputType,
                       String aiMode, String whatsappSupportUrl) {
        this.sessionId = sessionId;
        this.userQuestion = userQuestion;
        this.aiResponse = aiResponse;
        this.matchedFaqId = matchedFaqId;
        this.confidenceScore = confidenceScore;
        this.answered = answered;
        this.language = language;
        this.inputType = inputType;
        this.aiMode = aiMode;
        this.whatsappSupportUrl = whatsappSupportUrl;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (language == null || language.isBlank()) {
            language = "EN";
        }
        if (inputType == null || inputType.isBlank()) {
            inputType = "TEXT";
        }
        if (aiMode == null || aiMode.isBlank()) {
            aiMode = "FAQ_ONLY";
        }
        language = language.toUpperCase();
        inputType = inputType.toUpperCase();
        aiMode = aiMode.toUpperCase();
    }

    public Long getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(String userQuestion) {
        this.userQuestion = userQuestion;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public Long getMatchedFaqId() {
        return matchedFaqId;
    }

    public void setMatchedFaqId(Long matchedFaqId) {
        this.matchedFaqId = matchedFaqId;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getAiMode() {
        return aiMode;
    }

    public void setAiMode(String aiMode) {
        this.aiMode = aiMode;
    }

    public String getWhatsappSupportUrl() {
        return whatsappSupportUrl;
    }

    public void setWhatsappSupportUrl(String whatsappSupportUrl) {
        this.whatsappSupportUrl = whatsappSupportUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
