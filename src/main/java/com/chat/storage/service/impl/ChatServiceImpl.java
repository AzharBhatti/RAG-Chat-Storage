
package com.chat.storage.service.impl;

import com.chat.storage.dto.ChatSessionRequest;
import com.chat.storage.dto.ChatSessionResponse;
import com.chat.storage.dto.MessageRequest;
import com.chat.storage.dto.MessageResponse;
import com.chat.storage.entity.ChatSessionEntity;
import com.chat.storage.entity.MessageEntity;
import com.chat.storage.repository.ChatSessionRepository;
import com.chat.storage.repository.MessageRepository;
import com.chat.storage.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatSessionRepository chatSessionRepository;
    private final MessageRepository chatMessageRepository;

    @Override
    public ChatSessionResponse createSession(ChatSessionRequest request) {
        ChatSessionEntity session = new ChatSessionEntity();
        session.setUserId(request.getUserId());
        session.setName(request.getName());
        session.setFavorite(request.isFavorite());
        ChatSessionEntity savedSession = chatSessionRepository.save(session);
        return new ChatSessionResponse(savedSession);
    }

    @Override
    public List<ChatSessionResponse> getSessions(String userId, boolean favoritesOnly) {
        List<ChatSessionEntity> sessions;
        if (favoritesOnly) {
            sessions = chatSessionRepository.findByUserIdAndFavorite(userId, true);
        } else {
            sessions = chatSessionRepository.findByUserId(userId);
        }
        return sessions.stream()
                .map(ChatSessionResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public ChatSessionResponse renameSession(UUID id, String newName) {
        ChatSessionEntity session = chatSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setName(newName);
        ChatSessionEntity updatedSession = chatSessionRepository.save(session);
        return new ChatSessionResponse(updatedSession);
    }

    @Override
    public void deleteSession(UUID id) {
        chatSessionRepository.deleteById(id);
    }

    @Override
    public ChatSessionResponse toggleFavorite(UUID id) {
        ChatSessionEntity session = chatSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setFavorite(!session.isFavorite());
        ChatSessionEntity updatedSession = chatSessionRepository.save(session);
        return new ChatSessionResponse(updatedSession);
    }

    @Override
    public MessageResponse addMessageToSession(MessageRequest request) {
        ChatSessionEntity session = chatSessionRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Session not found"));
        MessageEntity message = new MessageEntity();
        message.setSender(request.getSender());
        message.setContent(request.getContent());
        message.setSession(session);
        message.setContext(request.getContext());
        MessageEntity savedMessage = chatMessageRepository.save(message);
        return new MessageResponse(savedMessage);
    }

    @Override
    public List<MessageResponse> getMessagesBySession(UUID sessionId) {
        List<MessageEntity> messages = chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);
        return messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }
}
