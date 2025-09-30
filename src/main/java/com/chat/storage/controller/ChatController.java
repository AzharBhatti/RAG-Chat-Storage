package com.chat.storage.controller;

import com.chat.storage.dto.ChatSessionRequest;
import com.chat.storage.dto.ChatSessionResponse;
import com.chat.storage.dto.MessageRequest;
import com.chat.storage.dto.MessageResponse;
import com.chat.storage.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/sessions")
    public ChatSessionResponse createSession(@Valid @RequestBody  ChatSessionRequest request) {
        return chatService.createSession(request);
    }

    @GetMapping("/sessions")
    public List<ChatSessionResponse> listSessions(@RequestParam String userId,
                                                  @RequestParam(defaultValue = "false") boolean favorites) {
        return chatService.getSessions(userId, favorites);
    }

    @PatchMapping("/sessions/{id}/rename")
    public ChatSessionResponse renameSession(@PathVariable UUID id, @RequestParam String name) {
        return chatService.renameSession(id, name);
    }

    @PatchMapping("/sessions/{id}/favorite")
    public ChatSessionResponse favoriteSession(@PathVariable UUID id) {
        return chatService.toggleFavorite(id);
    }

    @DeleteMapping("/sessions/{id}")
    public void deleteSession(@PathVariable UUID id) {
        chatService.deleteSession(id);
    }

    @PostMapping("/sessions/{id}/messages")
    public MessageResponse addMessage(@Valid @RequestBody MessageRequest request) {
        return chatService.addMessageToSession(request);
    }

    @GetMapping("/sessions/{id}/messages")
    public List<MessageResponse> getMessages(@PathVariable UUID id) {
        return chatService.getMessagesBySession(id);
    }
}
