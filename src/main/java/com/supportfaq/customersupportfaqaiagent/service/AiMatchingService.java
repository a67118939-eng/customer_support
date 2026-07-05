package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.MatchingResult;
import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AiMatchingService {

    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "the", "is", "are", "am", "to", "of", "for", "in", "on", "and", "or", "my", "your",
            "how", "what", "when", "where", "why", "do", "does", "can", "i", "you", "me", "we", "our",
            "\u0645\u0646", "\u0641\u064a", "\u0639\u0644\u0649", "\u0627\u0644\u0649", "\u0625\u0644\u0649",
            "\u0639\u0646", "\u0645\u0627", "\u0645\u0627\u0630\u0627", "\u0643\u064a\u0641", "\u0647\u0644",
            "\u0647\u0648", "\u0647\u064a", "\u0648", "\u0627\u0648", "\u0623\u0648", "\u0627\u0644"
    );

    public MatchingResult findBestMatch(String userQuestion, List<Faq> faqs) {
        return findBestMatch(userQuestion, faqs, null, null);
    }

    public MatchingResult findBestMatch(String userQuestion, List<Faq> faqs, String language, String category) {
        Faq bestFaq = null;
        double bestScore = 0.0;

        for (Faq faq : faqs) {
            if (!"ACTIVE".equalsIgnoreCase(faq.getStatus())) {
                continue;
            }
            double score = calculateFaqScore(userQuestion, faq, language, category);
            if (score > bestScore) {
                bestScore = score;
                bestFaq = faq;
            }
        }

        return new MatchingResult(bestFaq, Math.min(bestScore, 1.0));
    }

    public double calculateFaqScore(String userQuestion, Faq faq, String language, String category) {
        double score = 0.0;
        score += calculateSimilarity(userQuestion, faq.getQuestion()) * 0.55;
        score += calculateSimilarity(userQuestion, faq.getAnswer()) * 0.20;
        score += calculateSimilarity(userQuestion, faq.getKeywords()) * 0.20;
        score += calculateSimilarity(userQuestion, faq.getCategory()) * 0.05;

        String cleanQuestion = cleanText(userQuestion);
        if (!cleanQuestion.isBlank() && cleanText(faq.getQuestion()).contains(cleanQuestion)) {
            score += 0.18;
        }
        if (language != null && faq.getLanguage() != null && faq.getLanguage().equalsIgnoreCase(language)) {
            score += 0.08;
        }
        if (category != null && faq.getCategory() != null && faq.getCategory().equalsIgnoreCase(category)) {
            score += 0.08;
        }
        score += priorityBoost(faq.getPriority());

        return Math.min(score, 1.0);
    }

    public double calculateSimilarity(String text1, String text2) {
        Set<String> words1 = tokenize(text1);
        Set<String> words2 = tokenize(text2);

        if (words1.isEmpty() || words2.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        return (double) intersection.size() / union.size();
    }

    public Set<String> tokenize(String text) {
        String cleanedText = cleanText(text);
        if (cleanedText.isBlank()) {
            return Set.of();
        }

        return Arrays.stream(cleanedText.split("\\s+"))
                .filter(word -> !word.isBlank())
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toSet());
    }

    private double priorityBoost(String priority) {
        if ("CRITICAL".equalsIgnoreCase(priority)) {
            return 0.08;
        }
        if ("HIGH".equalsIgnoreCase(priority)) {
            return 0.05;
        }
        return 0.0;
    }

    private String cleanText(String text) {
        if (text == null) {
            return "";
        }

        String normalized = Normalizer.normalize(text, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "");

        return normalized
                .toLowerCase()
                .replace('\u0623', '\u0627')
                .replace('\u0625', '\u0627')
                .replace('\u0622', '\u0627')
                .replace('\u0649', '\u064a')
                .replace('\u0629', '\u0647')
                .replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
