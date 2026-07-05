package com.supportfaq.customersupportfaqaiagent.dto;

import jakarta.validation.constraints.Size;

public class FeedbackRequest {

    private Long chatHistoryId;
    private boolean helpful;

    @Size(max = 1000, message = "Comment must be at most 1000 characters")
    private String comment;

    public Long getChatHistoryId() {
        return chatHistoryId;
    }

    public void setChatHistoryId(Long chatHistoryId) {
        this.chatHistoryId = chatHistoryId;
    }

    public boolean isHelpful() {
        return helpful;
    }

    public void setHelpful(boolean helpful) {
        this.helpful = helpful;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
