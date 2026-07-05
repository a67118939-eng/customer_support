package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.AiAskRequest;
import com.supportfaq.customersupportfaqaiagent.dto.AiAskResponse;
import com.supportfaq.customersupportfaqaiagent.dto.AiModeResponse;
import com.supportfaq.customersupportfaqaiagent.dto.AiTokenUsageResponse;
import com.supportfaq.customersupportfaqaiagent.service.AiSafetyService;
import com.supportfaq.customersupportfaqaiagent.service.AuthService;
import com.supportfaq.customersupportfaqaiagent.service.RealAiAgentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiAgentController {

    private final RealAiAgentService realAiAgentService;
    private final AiSafetyService aiSafetyService;

    public AiAgentController(RealAiAgentService realAiAgentService, AiSafetyService aiSafetyService) {
        this.realAiAgentService = realAiAgentService;
        this.aiSafetyService = aiSafetyService;
    }

    @PostMapping("/ask")
    public AiAskResponse ask(@Valid @RequestBody AiAskRequest request, HttpServletRequest servletRequest) {
        boolean admin = AuthService.isAdmin(servletRequest);
        request.setSessionId(userScopedSessionId(request.getSessionId(), servletRequest));
        if (aiSafetyService.isSuspicious(request.getQuestion())) {
            aiSafetyService.auditSuspiciousPrompt(request.getQuestion(), servletRequest);
            AiAskResponse response = new AiAskResponse(
                    aiSafetyService.safeRefusal(),
                    null,
                    0.0,
                    false,
                    request.getLanguage() == null ? "EN" : request.getLanguage(),
                    "REAL_AI",
                    aiSafetyService.supportUrl(),
                    List.of(),
                    request.getSessionId()
            );
            applyTokenUsage(response, realAiAgentService.getTokenUsage(request.getSessionId(), admin));
            response.setLastRequestTokens(0);
            response.setOpenAiUsed(false);
            response.setTokenUsageReason("Suspicious prompt was blocked before the external AI provider, so no external AI tokens were used.");
            return response;
        }
        return realAiAgentService.ask(request, admin);
    }

    @GetMapping("/token-usage")
    public AiTokenUsageResponse getTokenUsage(@RequestParam(required = false) String sessionId,
                                              HttpServletRequest servletRequest) {
        return realAiAgentService.getTokenUsage(
                userScopedSessionId(sessionId, servletRequest),
                AuthService.isAdmin(servletRequest)
        );
    }

    @GetMapping("/modes")
    public List<AiModeResponse> getModes() {
        return List.of(
                new AiModeResponse("FAQ_ONLY", "FAQ Database Agent",
                        "Answers only from saved FAQ records. If no answer is found, it redirects to WhatsApp support."),
                new AiModeResponse("REAL_AI", "Real AI API Agent",
                        "Uses the backend AI API key, FAQ context, and safe general knowledge. New safe answers can be saved as FAQ records.")
        );
    }

    private void applyTokenUsage(AiAskResponse response, AiTokenUsageResponse tokenUsage) {
        response.setTokensUsed(tokenUsage.getTokensUsed());
        response.setTokenLimit(tokenUsage.getTokenLimit());
        response.setTokensRemaining(tokenUsage.getTokensRemaining());
        response.setTokenPercentUsed(tokenUsage.getPercentUsed());
    }

    private String userScopedSessionId(String sessionId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object userId = session == null ? null : session.getAttribute(AuthService.SESSION_USER_ID);
        String userPart = userId == null ? "anonymous" : userId.toString();
        String clientPart = sessionId == null || sessionId.isBlank() ? "default" : sessionId;
        String scoped = "user-" + userPart + ":" + clientPart;
        return scoped.length() <= 120 ? scoped : scoped.substring(0, 120);
    }
}
