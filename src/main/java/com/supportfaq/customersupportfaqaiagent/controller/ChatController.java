package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.ApiMessageResponse;
import com.supportfaq.customersupportfaqaiagent.dto.AskQuestionRequest;
import com.supportfaq.customersupportfaqaiagent.dto.AskQuestionResponse;
import com.supportfaq.customersupportfaqaiagent.entity.ChatHistory;
import com.supportfaq.customersupportfaqaiagent.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ask")
    public AskQuestionResponse askQuestion(@Valid @RequestBody AskQuestionRequest request) {
        return chatService.askQuestion(
                request.getQuestion(),
                request.getLanguage(),
                request.getInputType(),
                request.getSessionId(),
                "FAQ_ONLY"
        );
    }

    @GetMapping("/history")
    public List<ChatHistory> getHistory() {
        return chatService.getRecentHistory();
    }

    @DeleteMapping("/history")
    public ApiMessageResponse deleteHistory() {
        chatService.deleteHistory();
        return new ApiMessageResponse("Chat history cleared successfully");
    }
}
