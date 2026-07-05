package com.supportfaq.customersupportfaqaiagent.dto;

public class FeedbackSummaryResponse {

    private long totalFeedback;
    private long helpfulFeedback;
    private long unhelpfulFeedback;

    public FeedbackSummaryResponse(long totalFeedback, long helpfulFeedback, long unhelpfulFeedback) {
        this.totalFeedback = totalFeedback;
        this.helpfulFeedback = helpfulFeedback;
        this.unhelpfulFeedback = unhelpfulFeedback;
    }

    public long getTotalFeedback() {
        return totalFeedback;
    }

    public long getHelpfulFeedback() {
        return helpfulFeedback;
    }

    public long getUnhelpfulFeedback() {
        return unhelpfulFeedback;
    }
}
