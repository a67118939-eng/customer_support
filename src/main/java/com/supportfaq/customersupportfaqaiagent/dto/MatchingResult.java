package com.supportfaq.customersupportfaqaiagent.dto;

import com.supportfaq.customersupportfaqaiagent.entity.Faq;

public class MatchingResult {

    private final Faq faq;
    private final double confidenceScore;
    private final String categoryGuess;

    public MatchingResult(Faq faq, double confidenceScore) {
        this(faq, confidenceScore, faq == null ? null : faq.getCategory());
    }

    public MatchingResult(Faq faq, double confidenceScore, String categoryGuess) {
        this.faq = faq;
        this.confidenceScore = confidenceScore;
        this.categoryGuess = categoryGuess;
    }

    public Faq getFaq() {
        return faq;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public String getCategoryGuess() {
        return categoryGuess;
    }

    public boolean hasMatch(double threshold) {
        return faq != null && confidenceScore >= threshold;
    }
}
