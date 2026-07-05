package com.supportfaq.customersupportfaqaiagent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "faqs")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;

    private String category;
    private String status = "ACTIVE";
    private String language = "EN";

    @Column(columnDefinition = "TEXT")
    private String keywords;

    private String priority = "NORMAL";
    private String source = "MANUAL";
    private String generatedBy;

    @Column(columnDefinition = "TEXT")
    private String generatedFromQuestion;

    private boolean reviewed = true;
    private LocalDateTime reviewedAt;
    private String reviewedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long matchCount;
    private long helpfulCount;
    private long unhelpfulCount;

    public Faq() {
    }

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        applyDefaults();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        applyDefaults();
    }

    private void applyDefaults() {
        if (status == null || status.isBlank()) {
            status = "ACTIVE";
        }
        if (language == null || language.isBlank()) {
            language = "EN";
        }
        if (priority == null || priority.isBlank()) {
            priority = "NORMAL";
        }
        if (source == null || source.isBlank()) {
            source = "MANUAL";
        }
        status = status.toUpperCase();
        language = language.toUpperCase();
        priority = priority.toUpperCase();
        source = source.toUpperCase();
        if ("AI_GENERATED".equals(source) && "DRAFT".equals(status)) {
            reviewed = false;
        }
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getGeneratedFromQuestion() {
        return generatedFromQuestion;
    }

    public void setGeneratedFromQuestion(String generatedFromQuestion) {
        this.generatedFromQuestion = generatedFromQuestion;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public long getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(long matchCount) {
        this.matchCount = matchCount;
    }

    public long getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(long helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public long getUnhelpfulCount() {
        return unhelpfulCount;
    }

    public void setUnhelpfulCount(long unhelpfulCount) {
        this.unhelpfulCount = unhelpfulCount;
    }
}
