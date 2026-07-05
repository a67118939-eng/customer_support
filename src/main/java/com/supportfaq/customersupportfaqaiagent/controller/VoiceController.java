package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.VoiceAskResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuthService;
import com.supportfaq.customersupportfaqaiagent.service.VoiceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {

    private final VoiceService voiceService;

    public VoiceController(VoiceService voiceService) {
        this.voiceService = voiceService;
    }

    @PostMapping("/ask")
    public VoiceAskResponse askWithVoice(@RequestParam("audio") MultipartFile audio,
                                         @RequestParam(required = false) String mode,
                                         @RequestParam(required = false) String language,
                                         @RequestParam(required = false) String sessionId,
                                         HttpServletRequest request) {
        return voiceService.askWithVoice(
                audio,
                mode,
                language,
                userScopedSessionId(sessionId, request),
                AuthService.isAdmin(request)
        );
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
