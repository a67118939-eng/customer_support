package com.supportfaq.customersupportfaqaiagent.dto;

import java.util.List;

public class AskQuestionResponse {

    private String answer;
    private Long matchedFaqId;
    private double confidenceScore;
    private boolean answered;
    private String language;
    private String mode;
    private String whatsappSupportUrl;
    private List<String> suggestedQuestions;
    private String sessionId;
    private Long generatedFaqId;
    private boolean generatedFaqSaved;
    private String generatedFaqStatus;
    private String learningMessage;

    public AskQuestionResponse() {
    }

    public AskQuestionResponse(String answer, Long matchedFaqId, double confidenceScore, boolean answered) {
        this(answer, matchedFaqId, confidenceScore, answered, "EN", "FAQ_ONLY", null, List.of());
    }

    public AskQuestionResponse(String answer, Long matchedFaqId, double confidenceScore, boolean answered,
                               String language, List<String> suggestedQuestions) {
        this(answer, matchedFaqId, confidenceScore, answered, language, "FAQ_ONLY", null, suggestedQuestions);
    }

    public AskQuestionResponse(String answer, Long matchedFaqId, double confidenceScore, boolean answered,
                               String language, String mode, String whatsappSupportUrl, List<String> suggestedQuestions) {
        this(answer, matchedFaqId, confidenceScore, answered, language, mode, whatsappSupportUrl, suggestedQuestions, null);
    }

    public AskQuestionResponse(String answer, Long matchedFaqId, double confidenceScore, boolean answered,
                               String language, String mode, String whatsappSupportUrl, List<String> suggestedQuestions,
                               String sessionId) {
        this.answer = answer;
        this.matchedFaqId = matchedFaqId;
        this.confidenceScore = confidenceScore;
        this.answered = answered;
        this.language = language;
        this.mode = mode;
        this.whatsappSupportUrl = whatsappSupportUrl;
        this.suggestedQuestions = suggestedQuestions;
        this.sessionId = sessionId;
    }

    public String getAnswer() {
        return answer;
    }

    public Long getMatchedFaqId() {
        return matchedFaqId;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public boolean isAnswered() {
        return answered;
    }

    public String getLanguage() {
        return language;
    }

    public String getMode() {
        return mode;
    }

    public String getWhatsappSupportUrl() {
        return whatsappSupportUrl;
    }

    public List<String> getSuggestedQuestions() {
        return suggestedQuestions;
    }

    public void setSuggestedQuestions(List<String> suggestedQuestions) {
        this.suggestedQuestions = suggestedQuestions;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getGeneratedFaqId() {
        return generatedFaqId;
    }

    public void setGeneratedFaqId(Long generatedFaqId) {
        this.generatedFaqId = generatedFaqId;
    }

    public boolean isGeneratedFaqSaved() {
        return generatedFaqSaved;
    }

    public void setGeneratedFaqSaved(boolean generatedFaqSaved) {
        this.generatedFaqSaved = generatedFaqSaved;
    }

    public String getGeneratedFaqStatus() {
        return generatedFaqStatus;
    }

    public void setGeneratedFaqStatus(String generatedFaqStatus) {
        this.generatedFaqStatus = generatedFaqStatus;
    }

    public String getLearningMessage() {
        return learningMessage;
    }

    public void setLearningMessage(String learningMessage) {
        this.learningMessage = learningMessage;
    }
}
