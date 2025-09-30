package com.chat.storage.controller;

import com.chat.storage.dto.ChatSessionRequest;
import com.chat.storage.dto.ChatSessionResponse;
import com.chat.storage.dto.MessageRequest;
import com.chat.storage.dto.MessageResponse;
import com.chat.storage.service.ChatService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    @Value("${API_KEY}")
    private String apiKey;

    private final ChatService chatService;

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @PostMapping("/sessions")
    public ResponseEntity<ChatSessionResponse> createSession(@RequestHeader("X-API-KEY") String clientApiKey,
                                                             @Valid @RequestBody  ChatSessionRequest request) {
        log.info("Create Session: " + request);
        if (apiKey.equals(clientApiKey)) {
            return ResponseEntity.ok(chatService.createSession(request));
        } else {
            log.error("Create Session fails: UNAUTHORIZED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @GetMapping("/sessions")
    public ResponseEntity<List<ChatSessionResponse>> listSessions(@RequestHeader("X-API-KEY") String clientApiKey,
                                                                  @RequestParam String userId,
                                                  @RequestParam(defaultValue = "false") boolean favorites) {
        if (apiKey.equals(clientApiKey)) {
            return ResponseEntity.ok(chatService.getSessions(userId, favorites));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @PatchMapping("/sessions/{id}/rename")
    public ResponseEntity<ChatSessionResponse> renameSession(@RequestHeader("X-API-KEY") String clientApiKey,
                                             @PathVariable UUID id, @RequestParam String name) {
        if (apiKey.equals(clientApiKey)) {
            return ResponseEntity.ok(chatService.renameSession(id, name));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @PatchMapping("/sessions/{id}/favorite")
    public ResponseEntity<ChatSessionResponse> favoriteSession(@RequestHeader("X-API-KEY") String clientApiKey,
                                               @PathVariable UUID id) {
        if (apiKey.equals(clientApiKey)) {
            return ResponseEntity.ok(chatService.toggleFavorite(id));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<String> deleteSession(@RequestHeader("X-API-KEY") String clientApiKey,
                              @PathVariable UUID id) {
        if (apiKey.equals(clientApiKey)) {
            chatService.deleteSession(id);
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fail");
        }

    }

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @PostMapping("/sessions/{id}/messages")
    public ResponseEntity<MessageResponse> addMessage(@RequestHeader("X-API-KEY") String clientApiKey,
                                      @Valid @RequestBody MessageRequest request) {
        if (apiKey.equals(clientApiKey)) {
            return ResponseEntity.ok(chatService.addMessageToSession(request));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @RateLimiter(name = "apiRateLimiter", fallbackMethod = "rateLimitFallback")
    @GetMapping("/sessions/{id}/messages")
    public ResponseEntity<Page<MessageResponse>> getMessages(@RequestHeader("X-API-KEY") String clientApiKey,
                                                             @PathVariable UUID id,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        if (apiKey.equals(clientApiKey)) {
            return ResponseEntity.ok(chatService.getMessagesBySession(id, page, size));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public String rateLimitFallback() {
        return "Rate limit exceeded, please try again later.";
    }
}
