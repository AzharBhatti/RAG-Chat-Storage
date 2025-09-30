package com.chat.storage.service;

import com.chat.storage.dto.ChatSessionRequest;
import com.chat.storage.dto.ChatSessionResponse;
import com.chat.storage.dto.MessageRequest;
import com.chat.storage.dto.MessageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    ChatSessionResponse createSession(ChatSessionRequest request);

    List<ChatSessionResponse> getSessions(String userId, boolean favoritesOnly);

    ChatSessionResponse renameSession(UUID id, String newName);
    ChatSessionResponse toggleFavorite(UUID messageId);
    MessageResponse addMessageToSession(MessageRequest messageRequest);
    Page<MessageResponse> getMessagesBySession(UUID sessionId, int page, int size);

    void deleteSession(UUID id);
}
