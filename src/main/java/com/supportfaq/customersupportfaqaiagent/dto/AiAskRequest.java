package com.supportfaq.customersupportfaqaiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AiAskRequest {

    @NotBlank(message = "Question is required")
    @Size(max = 1000, message = "Question must be at most 1000 characters")
    private String question;

    @Size(max = 10, message = "Language must be at most 10 characters")
    private String language;

    @Size(max = 40, message = "Mode must be at most 40 characters")
    private String mode;

    @Size(max = 40, message = "Input type must be at most 40 characters")
    private String inputType;

    @Size(max = 120, message = "Session id must be at most 120 characters")
    private String sessionId;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
