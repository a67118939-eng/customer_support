package com.supportfaq.customersupportfaqaiagent.dto;

import java.util.List;

public class AiAskResponse extends AskQuestionResponse {

    private long tokensUsed;
    private long tokenLimit;
    private long tokensRemaining;
    private double tokenPercentUsed;
    private long lastRequestTokens;
    private boolean openAiUsed;
    private String tokenUsageReason;

    public AiAskResponse() {
    }

    public AiAskResponse(String answer, Long matchedFaqId, double confidenceScore, boolean answered,
                         String language, String mode, String whatsappSupportUrl, List<String> suggestedQuestions) {
        super(answer, matchedFaqId, confidenceScore, answered, language, mode, whatsappSupportUrl, suggestedQuestions);
    }

    public AiAskResponse(String answer, Long matchedFaqId, double confidenceScore, boolean answered,
                         String language, String mode, String whatsappSupportUrl, List<String> suggestedQuestions,
                         String sessionId) {
        super(answer, matchedFaqId, confidenceScore, answered, language, mode, whatsappSupportUrl, suggestedQuestions, sessionId);
    }

    public long getTokensUsed() {
        return tokensUsed;
    }

    public void setTokensUsed(long tokensUsed) {
        this.tokensUsed = tokensUsed;
    }

    public long getTokenLimit() {
        return tokenLimit;
    }

    public void setTokenLimit(long tokenLimit) {
        this.tokenLimit = tokenLimit;
    }

    public long getTokensRemaining() {
        return tokensRemaining;
    }

    public void setTokensRemaining(long tokensRemaining) {
        this.tokensRemaining = tokensRemaining;
    }

    public double getTokenPercentUsed() {
        return tokenPercentUsed;
    }

    public void setTokenPercentUsed(double tokenPercentUsed) {
        this.tokenPercentUsed = tokenPercentUsed;
    }

    public long getLastRequestTokens() {
        return lastRequestTokens;
    }

    public void setLastRequestTokens(long lastRequestTokens) {
        this.lastRequestTokens = lastRequestTokens;
    }

    public boolean isOpenAiUsed() {
        return openAiUsed;
    }

    public void setOpenAiUsed(boolean openAiUsed) {
        this.openAiUsed = openAiUsed;
    }

    public String getTokenUsageReason() {
        return tokenUsageReason;
    }

    public void setTokenUsageReason(String tokenUsageReason) {
        this.tokenUsageReason = tokenUsageReason;
    }
}
