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
@Table(name = "unanswered_questions")
public class UnansweredQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    private String status = "NEW";
    private String language = "EN";
    private String categoryGuess;

    @Column(columnDefinition = "TEXT")
    private String adminNote;

    private boolean convertedToFaq;
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;
    private LocalDateTime resolvedAt;

    public UnansweredQuestion() {
    }

    public UnansweredQuestion(String question, String language, String categoryGuess) {
        this.question = question;
        this.language = language;
        this.categoryGuess = categoryGuess;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (status == null || status.isBlank()) {
            status = "NEW";
        }
        if (language == null || language.isBlank()) {
            language = "EN";
        }
        status = status.toUpperCase();
        language = language.toUpperCase();
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

    public String getCategoryGuess() {
        return categoryGuess;
    }

    public void setCategoryGuess(String categoryGuess) {
        this.categoryGuess = categoryGuess;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public boolean isConvertedToFaq() {
        return convertedToFaq;
    }

    public void setConvertedToFaq(boolean convertedToFaq) {
        this.convertedToFaq = convertedToFaq;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}
