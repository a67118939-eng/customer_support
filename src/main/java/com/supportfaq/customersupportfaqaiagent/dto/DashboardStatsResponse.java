package com.supportfaq.customersupportfaqaiagent.dto;

public class DashboardStatsResponse {

    private long totalFaqs;
    private long activeFaqs;
    private long inactiveFaqs;
    private long draftFaqs;
    private long archivedFaqs;
    private long totalQuestions;
    private long answeredQuestions;
    private long unansweredQuestions;
    private double averageConfidenceScore;
    private long totalTickets;
    private long openTickets;
    private long resolvedTickets;
    private long totalFeedback;
    private long helpfulFeedback;
    private long unhelpfulFeedback;
    private long englishQuestions;
    private long arabicQuestions;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(long totalFaqs, long activeFaqs, long inactiveFaqs, long draftFaqs,
                                  long archivedFaqs, long totalQuestions, long answeredQuestions,
                                  long unansweredQuestions, double averageConfidenceScore, long totalTickets,
                                  long openTickets, long resolvedTickets, long totalFeedback,
                                  long helpfulFeedback, long unhelpfulFeedback, long englishQuestions,
                                  long arabicQuestions) {
        this.totalFaqs = totalFaqs;
        this.activeFaqs = activeFaqs;
        this.inactiveFaqs = inactiveFaqs;
        this.draftFaqs = draftFaqs;
        this.archivedFaqs = archivedFaqs;
        this.totalQuestions = totalQuestions;
        this.answeredQuestions = answeredQuestions;
        this.unansweredQuestions = unansweredQuestions;
        this.averageConfidenceScore = averageConfidenceScore;
        this.totalTickets = totalTickets;
        this.openTickets = openTickets;
        this.resolvedTickets = resolvedTickets;
        this.totalFeedback = totalFeedback;
        this.helpfulFeedback = helpfulFeedback;
        this.unhelpfulFeedback = unhelpfulFeedback;
        this.englishQuestions = englishQuestions;
        this.arabicQuestions = arabicQuestions;
    }

    public long getTotalFaqs() {
        return totalFaqs;
    }

    public long getActiveFaqs() {
        return activeFaqs;
    }

    public long getInactiveFaqs() {
        return inactiveFaqs;
    }

    public long getDraftFaqs() {
        return draftFaqs;
    }

    public long getArchivedFaqs() {
        return archivedFaqs;
    }

    public long getTotalQuestions() {
        return totalQuestions;
    }

    public long getAnsweredQuestions() {
        return answeredQuestions;
    }

    public long getUnansweredQuestions() {
        return unansweredQuestions;
    }

    public double getAverageConfidenceScore() {
        return averageConfidenceScore;
    }

    public long getTotalTickets() {
        return totalTickets;
    }

    public long getOpenTickets() {
        return openTickets;
    }

    public long getResolvedTickets() {
        return resolvedTickets;
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

    public long getEnglishQuestions() {
        return englishQuestions;
    }

    public long getArabicQuestions() {
        return arabicQuestions;
    }
}
