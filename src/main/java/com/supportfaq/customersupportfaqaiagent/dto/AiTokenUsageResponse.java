package com.supportfaq.customersupportfaqaiagent.dto;

public class AiTokenUsageResponse {

    private long tokensUsed;
    private long tokenLimit;
    private long tokensRemaining;
    private double percentUsed;

    public AiTokenUsageResponse() {
    }

    public AiTokenUsageResponse(long tokensUsed, long tokenLimit) {
        this.tokensUsed = Math.max(0, tokensUsed);
        this.tokenLimit = tokenLimit;
        if (tokenLimit <= 0) {
            this.tokensRemaining = Long.MAX_VALUE;
            this.percentUsed = 0;
        } else {
            this.tokensRemaining = Math.max(0, this.tokenLimit - this.tokensUsed);
            this.percentUsed = Math.min(100.0, (this.tokensUsed * 100.0) / this.tokenLimit);
        }
    }

    public long getTokensUsed() {
        return tokensUsed;
    }

    public long getTokenLimit() {
        return tokenLimit;
    }

    public long getTokensRemaining() {
        return tokensRemaining;
    }

    public double getPercentUsed() {
        return percentUsed;
    }
}
