package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.AiTokenUsageResponse;
import com.supportfaq.customersupportfaqaiagent.entity.AiTokenUsage;
import com.supportfaq.customersupportfaqaiagent.repository.AiTokenUsageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AiTokenUsageService {

    private final AiTokenUsageRepository aiTokenUsageRepository;
    private final InputSanitizer inputSanitizer;

    @Value("${app.ai.token-limit-per-session:20000}")
    private long tokenLimitPerSession;

    public AiTokenUsageService(AiTokenUsageRepository aiTokenUsageRepository, InputSanitizer inputSanitizer) {
        this.aiTokenUsageRepository = aiTokenUsageRepository;
        this.inputSanitizer = inputSanitizer;
    }

    public AiTokenUsageResponse getUsage(String sessionId) {
        return getUsage(sessionId, false);
    }

    public AiTokenUsageResponse getUsage(String sessionId, boolean unlimitedTokens) {
        String normalizedSessionId = normalizeSessionId(sessionId);
        long tokensUsed = aiTokenUsageRepository.findBySessionId(normalizedSessionId)
                .map(AiTokenUsage::getTokensUsed)
                .orElse(0L);
        return new AiTokenUsageResponse(tokensUsed, effectiveTokenLimit(unlimitedTokens));
    }

    public boolean hasRemainingTokens(String sessionId) {
        return hasRemainingTokens(sessionId, false);
    }

    public boolean hasRemainingTokens(String sessionId, boolean unlimitedTokens) {
        return unlimitedTokens || getUsage(sessionId, false).getTokensRemaining() > 0;
    }

    @Transactional
    public AiTokenUsageResponse addTokens(String sessionId, long tokensToAdd) {
        return addTokens(sessionId, tokensToAdd, false);
    }

    @Transactional
    public AiTokenUsageResponse addTokens(String sessionId, long tokensToAdd, boolean unlimitedTokens) {
        String normalizedSessionId = normalizeSessionId(sessionId);
        AiTokenUsage usage = aiTokenUsageRepository.findBySessionId(normalizedSessionId)
                .orElseGet(() -> new AiTokenUsage(normalizedSessionId));
        usage.setTokensUsed(Math.max(0, usage.getTokensUsed() + Math.max(0, tokensToAdd)));
        AiTokenUsage saved = aiTokenUsageRepository.save(usage);
        return new AiTokenUsageResponse(saved.getTokensUsed(), effectiveTokenLimit(unlimitedTokens));
    }

    private long effectiveTokenLimit(boolean unlimitedTokens) {
        return unlimitedTokens ? 0 : tokenLimitPerSession;
    }

    private String normalizeSessionId(String sessionId) {
        return inputSanitizer.sessionId(sessionId);
    }
}
